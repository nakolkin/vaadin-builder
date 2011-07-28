package dk.mmba.util.builder.vaadin.factory

import com.vaadin.ui.Panel
import com.vaadin.ui.ComponentContainer

/**
 * 06.07.11 14:48 arni
 * Copyright (c) 2011 2M business application a|s.
 * All rights reserved.
 */
class PanelFactory extends ComponentContainerFactory{

    PanelFactory() {
        super(Panel)
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        //TODO: Implement factory which will setContent if there is only one child of ComponentContainer
        //otherwise it should add children via addComponent
        if(child instanceof ComponentContainer) {
            ((Panel)parent).setContent(child)
        } else {
            super.setChild(builder, parent, child)
        }

    }


}
