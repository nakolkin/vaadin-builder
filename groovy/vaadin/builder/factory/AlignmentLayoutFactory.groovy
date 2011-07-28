package dk.mmba.util.builder.vaadin.factory

import com.vaadin.ui.Alignment
import com.vaadin.ui.Component
import com.vaadin.ui.Layout.AlignmentHandler

class AlignmentLayoutFactory extends ComponentContainerFactory {

    AlignmentLayoutFactory(Class beanClass) {
        super(beanClass)
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        super.setChild(builder, parent, child)
        if (builder.context.alignment != null) {
            ((AlignmentHandler) parent).setComponentAlignment((Component) child, (Alignment) builder.context.alignment)
        }
    }

    public static void processAlignmentLayoutAttributes(FactoryBuilderSupport builder, Object node, Map attributes) {
        if (!(node instanceof Component)) {
            return
        }
        if (attributes.containsKey('alignment')) {
            builder.context.alignment = attributes.remove('alignment')
        }
    }
}
