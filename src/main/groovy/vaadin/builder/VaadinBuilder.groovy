/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vaadin.builder

import com.vaadin.terminal.Sizeable
import groovy.swing.factory.BeanFactory
import org.codehaus.groovy.runtime.InvokerHelper

import java.awt.GridLayout

import com.vaadin.ui.*
import vaadin.builder.factory.*

/**
 * @author <a href="mailto:art@inet.ua">Artem Nikolaienko</a>
 */
class VaadinBuilder extends FactoryBuilderSupport {

    VaadinBuilder() {
        registerFactories()
    }

    protected void registerFactories() {
        registerFactory("window", new WindowFactory())
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
        registerFactory("loginForm", new BeanFactory(LoginForm))
        registerFactory("textField", new BeanFactory(TextField))

        registerFactory("widget", new WidgetFactory(Component))
    }

    protected void setNodeAttributes(Object node, Map attributes) {
        for (Map.Entry entry : (Set<Map.Entry>) attributes.entrySet()) {
            String property = entry.key.toString();
            Object value = entry.value;

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


