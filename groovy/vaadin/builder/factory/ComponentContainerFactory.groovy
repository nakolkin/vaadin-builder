package dk.mmba.util.builder.vaadin.factory

import com.vaadin.ui.Component
import com.vaadin.ui.ComponentContainer
import com.vaadin.ui.Window

class ComponentContainerFactory extends BeanFactory {

    ComponentContainerFactory(Class beanClass) {
        super(beanClass)
    }

    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(child instanceof Component) || (child instanceof Window)) {
            return;
        }
        ((ComponentContainer) parent).addComponent((Component) child);
    }
}
