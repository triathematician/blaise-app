/**
 * JGraphicCreatorGesture.java
 * Created Oct 10, 2014
 */
package com.googlecode.blaisemath.gesture.swing;

import static com.google.common.base.Preconditions.checkArgument;
import com.googlecode.blaisemath.gesture.GestureOrchestrator;
import com.googlecode.blaisemath.gesture.MouseGestureSupport;
import com.googlecode.blaisemath.graphics.core.Graphic;
import com.googlecode.blaisemath.graphics.swing.JGraphicComponent;
import com.googlecode.blaisemath.util.Configurer;
import com.googlecode.blaisemath.util.TransformedCanvasPainter;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Collections;
import javax.annotation.Nullable;

/*
 * #%L
 * BlaiseSketch
 * --
 * Copyright (C) 2015 Elisha Peterson
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

/**
 * Gesture for adding a graphic to the canvas. A preview of the graphic is rendered
 * on the canvas.
 * 
 * @author Elisha
 */
public abstract class JGraphicCreatorGesture extends MouseGestureSupport<JGraphicComponent> {
    
    public JGraphicCreatorGesture(GestureOrchestrator<JGraphicComponent> orchestrator, String name, String description) {
        super(orchestrator, name, description);
    }
    
    /**
     * Create the graphic based on the current state of the gesture
     * @return the graphic, or null if there is none.
     */
    @Nullable
    protected abstract Graphic<Graphics2D> createGraphic();

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);  
        orchestrator.setComponentCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public void complete() {
        Graphic<Graphics2D> gfc = createGraphic();
        if (gfc != null) {
            view.addGraphic(gfc);
            Configurer<Graphic<Graphics2D>> configurer = orchestrator.getConfigurer(Graphic.class);
            if (configurer != null) {
                configurer.configure(gfc);
            }
            view.getSelectionModel().setSelection(Collections.singleton(gfc));
        }
    }

    @Override
    public void cancel() {
        orchestrator.setComponentCursor(Cursor.DEFAULT_CURSOR);
    }

    @Override
    public void paint(Graphics2D g) {
        new Painter().paint(view, g);
    }

    /**
     * Paint the current state of the gesture on the given canvas. The canvas
     * has been pre-transformed to the proper graphic coordinate space.
     * @param gr graphics canvas
     */
    protected void paintTransformed(Graphics2D gr) {
        Graphic<Graphics2D> gfc = createGraphic();
        if (gfc != null) {
            gfc.renderTo(gr);
        }
    }
    
    /** Paints preview graphic on canvas */
    private final class Painter extends TransformedCanvasPainter {
        @Override
        public void paintTransformed(Component comp, Graphics2D gd) {
            JGraphicCreatorGesture.this.paintTransformed(gd);
        }
    }
    
}
