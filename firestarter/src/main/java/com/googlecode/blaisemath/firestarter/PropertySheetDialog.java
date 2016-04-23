/*
 * PropertySheetDialog.java
 * Created on Aug 4, 2009, 12:47:50 PM
 */

package com.googlecode.blaisemath.firestarter;

/*
 * #%L
 * Firestarter
 * --
 * Copyright (C) 2009 - 2016 Elisha Peterson
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

import com.google.common.base.Predicate;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IndexedPropertyDescriptor;
import javax.swing.JButton;
import javax.swing.JScrollPane;

/**
 * <p>
 *   Shows a property sheet for a specified bean within a dialog box.
 * </p>
 * @author ae3263
 */
public class PropertySheetDialog extends javax.swing.JDialog {
    
    private PropertySheetDialog(Window win, ModalityType modal, Object bean, Predicate<String> filter) {
        super(win, bean.toString(), modal);
        PropertySheet propertySheet = PropertySheet.forBean(bean);
        if (filter != null) {
            PropertyModel pm = propertySheet.getPropertyModel();
            ((BeanPropertyModel)pm).setFilter(BeanPropertyFilter.byName(filter));
        }
        initComponents(propertySheet);
    }
    
    private PropertySheetDialog(Window win, ModalityType modal, Object bean, IndexedPropertyDescriptor ipd) {
        super(win, "Indexed property [" + ipd.getDisplayName() + "] of " + bean.toString(), modal);
        initComponents(IndexedPropertySheet.forIndexedProperty(bean, ipd));
    }
    
    private PropertySheetDialog(Window win, ModalityType modal, Object bean, PropertyModel model) {
        super(win, "Editing "+bean.toString(), modal);
        initComponents(new PropertySheet(model));
    }
    
    //<editor-fold defaultstate="collapsed" desc="FACTORY METHODS">
    
    private static ModalityType modality(boolean modal) {
        return modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS;
    }

    /**
     * Creates new form PropertySheetDialog
     * @param parent the parent frame
     * @param modal whether the dialog box is modal
     * @param bean object to populate the box
     */
    public static void show(Window parent, boolean modal, Object bean) {
        if (parent instanceof Frame) {
            new PropertySheetDialog((Frame) parent, modality(modal), bean, (Predicate) null)
                    .setVisible(true);
        } else {
            new PropertySheetDialog((Window) parent, modality(modal), bean, (Predicate) null)
                    .setVisible(true);
        }
    }
    
    /**
     * Creates new form PropertySheetDialog
     * @param parent the parent frame
     * @param modal whether the dialog box is modal
     * @param bean object to populate the box
     * @param propertyFilter filters properties by name
     */
    public static void show(Window parent, boolean modal, Object bean, Predicate<String> propertyFilter) {
        new PropertySheetDialog((Window) parent, modality(modal), bean, propertyFilter)
                .setVisible(true);
    }

    /**
     * Creates new form PropertySheetDialog with an indexed property.
     * @param parent the parent frame
     * @param modal whether the dialog box is modal
     * @param bean object to populate the box
     * @param ipd an indexed property descriptor
     */
    public static void show(Window parent, boolean modal, Object bean, IndexedPropertyDescriptor ipd) {
        new PropertySheetDialog((Window) parent, modality(modal), bean, ipd)
                .setVisible(true);
    }

    /**
     * Creates new form PropertySheetDialog with an indexed property.
     * @param parent the parent frame
     * @param modal whether the dialog box is modal
     * @param bean object to populate the box
     * @param model model for the property sheet
     */
    public static void show(Window parent, boolean modal, Object bean, PropertyModel model) {
        new PropertySheetDialog((Window) parent, modality(modal), bean, model)
                .setVisible(true);
    }
    
    //</editor-fold>
    
    private void initComponents(PropertySheet propertySheet) {
        add(new JScrollPane(propertySheet,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                ), java.awt.BorderLayout.CENTER);

        JButton okButton = new JButton("Close");
        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        add(okButton, java.awt.BorderLayout.SOUTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }

}