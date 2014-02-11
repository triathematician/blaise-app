/*
 * PointStyleBasic.java
 * Created Jan 22, 2011
 */

package org.blaise.style;

import static com.google.common.base.Preconditions.*;
import java.awt.Color;
import javax.annotation.Nullable;

/**
 * Draws a point on the graphics canvas, using a {@link Marker} object and a {@link ShapeStyle}.
 * See also the <a href="http://www.w3.org/TR/SVG/painting.html#Markers">related SVG documentation</a> on markers.
 * 
 * @author Elisha
 */
public class PointStyleBasic extends PointStyleSupport {

    /** Style of the point */
    protected Marker marker = new MarkerLibrary.CircleShape();
    /** Radius of the displayed point */
    protected float markerRadius = 4;
    /** Delegate style used to draw the point */
    protected ShapeStyleBasic shapeStyle = new ShapeStyleBasic().fill(Color.lightGray).stroke(Color.darkGray);


    /** Construct instance with default settings */
    public PointStyleBasic() { }

    @Override
    public String toString() {
        return String.format("BasicPointStyle[marker=%s, markerRadius=%.1f, fill=%s, stroke=%s, strokeWidth=%.1f]", 
                marker, markerRadius, shapeStyle.fill, shapeStyle.stroke, shapeStyle.strokeWidth);
    }
    
    //<editor-fold defaultstate="collapsed" desc="BUILDER PATTERNS">

    /** Sets shape & returns pointer to object */
    public PointStyleBasic marker(Marker s) {
        setMarker(s);
        return this;
    }

    /** Sets radius & returns pointer to object */
    public PointStyleBasic markerRadius(float radius) {
        setMarkerRadius(radius);
        return this;
    }

    /** Sets fill color & returns pointer to object */
    public PointStyleBasic fill(Color c) {
        setFill(c);
        return this;
    }

    /** Sets stroke color & returns pointer to object */
    public PointStyleBasic stroke(Color c) {
        setStroke(c);
        return this;
    }

    /** Sets strokeWidth & returns pointer to object */
    public PointStyleBasic strokeWidth(float thick) {
        setStrokeWidth(thick);
        return this;
    }

    // </editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="PROPERTY PATTERNS">
    //
    // PROPERTY PATTERNS
    //

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = checkNotNull(marker);
    }

    public float getMarkerRadius() {
        return markerRadius;
    }

    public final void setMarkerRadius(float r) {
        markerRadius = Math.max(r, 1);
    }

    public @Nullable Color getFill() {
        return shapeStyle.getFill();
    }
    
    public void setFill(@Nullable Color fill) {
        shapeStyle.setFill(fill);
    }

    public @Nullable Color getStroke() {
        return shapeStyle.getStroke();
    }

    public void setStroke(@Nullable Color stroke) {
        shapeStyle.setStroke(stroke);
    }

    public float getStrokeWidth() {
        return shapeStyle.getStrokeWidth();
    }

    public void setStrokeWidth(float thickness) {
        shapeStyle.setStrokeWidth(thickness);
    }
    
    protected ShapeStyleBasic getShapeStyle() {
        return shapeStyle;
    }
    
    protected void setShapeStyle(ShapeStyleBasic r) {
        this.shapeStyle = checkNotNull(r);
    }
    
    // </editor-fold>

}
