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

package vaadin.builder.factory

/**
 * @author <a href="mailto:art@inet.ua">Artem Nikolaienko</a>
 */

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
