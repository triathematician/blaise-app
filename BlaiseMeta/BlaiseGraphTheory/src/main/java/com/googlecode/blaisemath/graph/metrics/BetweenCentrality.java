/*
 * BetweenCentrality.java
 * Created Jul 3, 2010
 */
package com.googlecode.blaisemath.graph.metrics;

/*
 * #%L
 * BlaiseGraphTheory
 * --
 * Copyright (C) 2009 - 2014 Elisha Peterson
 * --
 * Licensed under the Apache License, Version 2.0.
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import com.googlecode.blaisemath.graph.GAInstrument;
import com.googlecode.blaisemath.graph.Graph;
import com.googlecode.blaisemath.graph.GraphUtils;

/**
 * <p> Provides a metric describing the betweenness centrality of a vertex in a
 * CONNECTED graph. Returns infinity if the graph is not connected. May take a
 * long time for large graphs. </p> <p> Computationally, the centrality measures
 * the probability that a given node lies on a randomly chosen geodesic. </p>
 *
 * @author Elisha Peterson
 */
public class BetweenCentrality implements GraphNodeMetric<Double> {

    public <V> Double value(Graph<V> graph, V node) {
        return allValues(graph).get(node);
    }

    public <V> Map<V,Double> allValues(Graph<V> graph) {
        int id = GAInstrument.start("BetweenCentrality.allValues", graph.nodeCount()+" nodes", graph.edgeCount()+" edges");
        HashMap<V, Double> between = new HashMap<V, Double>();
        for (V v : graph.nodes()) {
            between.put(v, 0.0);
        }
        for (V start : graph.nodes()) {
            brandes(graph, start, between, graph.isDirected() ? 1.0 : 0.5);
        }
        GAInstrument.end(id);
        return between;
    }

    /**
     * Breadth-first search algorithm for an unweighted graph to generate
     * betweenness scores, with specified starting vertex. From <i>Brandes</i>,
     * "A Faster Algorithm for Betweenness Centrality"
     *
     * @param graph the graph
     * @param start the start vertex
     * @param between data structure storing existing betweenness centrality values
     * @param multiplier applied to all elements of resulting map
     * @return data structure encoding the result
     */
    static <V> HashMap<V, Double> brandes(Graph<V> graph, V start, HashMap<V, Double> between, double multiplier) {
        Set<V> nodes = graph.nodes();
        if (!nodes.contains(start)) {
            return new HashMap<V, Double>();
        }

        HashMap<V, Integer> numShortest = new HashMap<V, Integer>(); // number of shortest paths to each vertex
        HashMap<V, Integer> lengths = new HashMap<V, Integer>(); // length of shortest paths to each vertex
        Stack<V> stack = new Stack<V>(); // tracks elements in non-increasing order for later use
        HashMap<V, Set<V>> pred = new HashMap<V, Set<V>>(); // tracks vertex predecessors in resulting tree

        GraphUtils.breadthFirstSearch(graph, start, numShortest, lengths, stack, pred);

        // compute betweenness
        HashMap<V, Double> dependencies = new HashMap<V, Double>();
        for (V v : nodes) {
            dependencies.put(v, 0.0);
        }
        while (!stack.isEmpty()) {
            V w = stack.pop();
            for (V v : pred.get(w)) {
                dependencies.put(v, dependencies.get(v)
                        + (double) numShortest.get(v) / numShortest.get(w) * (1 + dependencies.get(w)));
            }
            if (w != start) {
                between.put(w, between.get(w)+multiplier*dependencies.get(w));
            }
        }

        return between;

    } // METHOD brandes
//
// OLD CODE BASED ON ADJACENCY MATRIX
//
//    /**
//     * Computes matrix of distances and #s of shortest paths between any 2 vertices
//     * @param n size of matrix/graph
//     * @param adj adjacency matrix of graph
//     * @param dists n x n matrix to set up with distances (return value)
//     * @param nPaths n x n matrix to set up with shortest paths (return value)
//     */
//    private static void computeShortestPaths(int n, int[][] adj, int[][] dists, int[][] nPaths) {
//        int[][] curAdj = new int[n][n];
//        int power = 1;
//        for (int i = 0; i < n; i++) {
//            dists[i][i] = 0;
//            nPaths[i][i] = 1;
//            for (int j = 0; j < n; j++) {
//                if (i == j) continue;
//                curAdj[i][j] = adj[i][j];
//                if (adj[i][j] == 0) {
//                    dists[i][j] = nPaths[i][j] = -1;
//                } else {
//                    dists[i][j] = nPaths[i][j] = 1;
//                }
//            }
//        }
//        int nFound = -1;
//        while (nFound != 0) {
//            nFound = 0;
//            curAdj = Matrices.matrixProduct(curAdj, adj);
//            power++;
//            for (int i = 0; i < n; i++)
//                for (int j = 0; j < n; j++) {
//                    if (i == j) continue;
//                    if (dists[i][j] == -1 && curAdj[i][j] != 0) {
//                        dists[i][j] = power;
//                        nPaths[i][j] = curAdj[i][j];
//                        nFound++;
//                    }
//                }
//        }
//    } // computeShortestPaths
//
//    /**
//     * Computes the betweenness of a specified vertex
//     * @param n size of matrix/graph
//     * @param dists the matrix of distances between vertices
//     * @param nPaths the matrix of # of shortest paths between any two vertices
//     * @param v0 the index of the node whose betweenness is to be computed
//     * @param directed whether the graph for the computation is directed or not
//     * @return the betweenness of the vertex
//     */
//    private static double computeBetweenness(int n, int[][] dists, int[][] nPaths, int v0) {
//        double result = 0.0;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (i == j)
//                    continue;
//                else if ((i == v0 || j == v0) && dists[i][j] != -1)
//                    result++;
//                else if (dists[i][v0] != -1 && dists[v0][j] != -1 && dists[i][j] != -1 && dists[i][v0] + dists[v0][j] == dists[i][j])
//                    result += nPaths[i][v0] * nPaths[v0][j] / (double) nPaths[i][j];
//            }
//        }
//        return result;
//    }
}
