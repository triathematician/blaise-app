/*
 * IndexedVisibilityGraphic.java
 * Created on Jul 18, 2012
 */
package org.blaise.graphics;

import org.blaise.style.VisibilityHint;
import org.blaise.util.IndexedPointBean;

/**
 * Provides methods for selecting
 * @param <C> the type of point
 * @author petereb1
 */
public interface IndexedVisibilityGraphic<C> extends IndexedPointBean<C> {

    /**
     * Updates visibility of a single item in the graphic
     * @param i index of item, or -1 for all
     * @param hint new visibility key
     * @param val new value
     */
    public void setVisibilityHint(int i, VisibilityHint hint, boolean val);

}
