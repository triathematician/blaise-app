package com.googlecode.blaisemath.app;

/*
 * #%L
 * BlaiseGraphics
 * --
 * Copyright (C) 2014 - 2025 Elisha Peterson
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

import java.awt.geom.Point2D;
import java.util.Set;
import javax.swing.JPopupMenu;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Provides a method that can be used to initialize (add actions to) a {@link JPopupMenu}.
 * The initializer will be provided as argument the <i>source</i> object
 * that is creating the menu, the <i>location</i> where it is being shown,
 * an optional <i>focus</i> object describing a more specific target for the menu,
 * and an optional <i>selection</i> of objects.
 *
 * @param <S> focus object type for menu
 * 
 * @author Elisha
 */
public interface ContextMenuInitializer<S> {

    /**
     * Initialize the context menu by adding any actions appropriate for the given parameters.
     * @param popup context menu
     * @param src source for context menu
     * @param point mouse location
     * @param focus object of focus
     * @param selection current selection
     */
    void initContextMenu(JPopupMenu popup, S src, Point2D point, @Nullable Object focus,
            @Nullable Set selection);

}
