package dk.mmba.util.builder.vaadin

import com.vaadin.terminal.Sizeable
import groovy.swing.factory.BeanFactory
import java.awt.GridLayout
import org.codehaus.groovy.runtime.InvokerHelper
import com.vaadin.ui.*
import dk.mmba.util.builder.vaadin.factory.*

/**
 *
 * @author Artem Nikolaienko
 */
class VaadinBuilder extends FactoryBuilderSupport {

    VaadinBuilder() {
        registerFactories()
    }

    protected void registerFactories() {
        registerFactory("panel", new PanelFactory())

        registerFactory("gridLayout", new AlignmentLayoutFactory(GridLayout))
        addAttributeDelegate(AlignmentLayoutFactory.&processAlignmentLayoutAttributes)

        registerFactory("verticalLayout", new OrderedLayoutFactory(VerticalLayout))
        registerFactory("horizontalLayout", new OrderedLayoutFactory(HorizontalLayout))
        registerFactory("formLayout", new OrderedLayoutFactory(FormLayout))
        addAttributeDelegate(OrderedLayoutFactory.&processOrderedLayoutAttributes)

        registerFactory("cssLayout", new ComponentContainerFactory(CssLayout))
        registerFactory("tabSheet", new ComponentContainerFactory(TabSheet))
        registerFactory("accordion", new ComponentContainerFactory(Accordion))

        registerFactory("label", new BeanFactory(Label))
        registerFactory("button", new BeanFactory(Button))
        registerFactory("comboBox", new BeanFactory(ComboBox))
        registerFactory("checkBox", new BeanFactory(CheckBox))
        registerFactory("widget", new WidgetFactory(Component))

    }

    protected void setNodeAttributes(Object node, Map attributes) {
        for (Map.Entry entry: (Set<Map.Entry>) attributes.entrySet()) {
            String property = entry.getKey().toString();
            Object value = entry.getValue();

            //TODO:Think how to find a better workaround
            // b/c Vaadin don't follow JavaBeans conventions for some properties
            // and Groovy don't support assignment with overloaded setters, see details http://jira.codehaus.org/browse/GROOVY-2500
            // there is a workaround
            if (property == "width" && node instanceof Sizeable) {
                node.class.getMethod("setWidth", String.class).invoke(node, value);
            } else if (property == "height" && node instanceof Sizeable) {
                node.class.getMethod("setHeight", String.class).invoke(node, value);
            } else if (property == "margin" && node instanceof AbstractLayout && value instanceof Boolean) {
                node.class.getMethod("setMargin", boolean.class).invoke(node, value);
            } else {
                InvokerHelper.setProperty(node, property, value);
            }
        }
    }


}


