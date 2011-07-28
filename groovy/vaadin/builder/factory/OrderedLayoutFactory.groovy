package dk.mmba.util.builder.vaadin.factory

import com.vaadin.ui.AbstractOrderedLayout
import com.vaadin.ui.Component

class OrderedLayoutFactory extends AlignmentLayoutFactory {
    OrderedLayoutFactory(Class beanClass) {
        super(beanClass)
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        super.setChild(builder, parent, child)
        if (builder.context.expandRatio != null) {
            ((AbstractOrderedLayout) parent).setExpandRatio((Component) child, (float) builder.context.expandRatio)
        }
    }


    public static void processOrderedLayoutAttributes(FactoryBuilderSupport builder, Object node, Map attributes) {
        if (!(node instanceof Component)) {
            return
        }
        if(attributes.containsKey('expandRatio')) {
            builder.context.expandRatio = attributes.remove('expandRatio')
        }
    }
}
