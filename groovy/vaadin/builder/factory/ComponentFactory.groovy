package dk.mmba.util.builder.vaadin.factory

import com.vaadin.ui.Alignment
import com.vaadin.ui.Component
import com.vaadin.ui.Layout.AlignmentHandler

class ComponentFactory extends BeanFactory {

    ComponentFactory(Class beanClass) {
        super(beanClass)
    }

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) {
//        builder.context.alignment = attributes.remove('alignment')
        return super.newInstance(builder, name, value, attributes)
    }

//    @Override
//    void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
//        super.onNodeCompleted(builder, parent, node)
//        TODO: this should be handled for all components globally in AlignmentFactory or in VaadinBuilder directly
//        if (builder.context.alignment != null) {
//            ((AlignmentHandler) parent).setComponentAlignment((Component) node, (Alignment) builder.context.alignment)
//        }
//    }

}
