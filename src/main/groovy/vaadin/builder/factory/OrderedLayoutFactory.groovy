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

import com.vaadin.ui.AbstractOrderedLayout
import com.vaadin.ui.Component

/**
 * @author <a href="mailto:art@inet.ua">Artem Nikolaienko</a>
 */

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
        if (attributes.containsKey('expandRatio')) {
            builder.context.expandRatio = attributes.remove('expandRatio')
        }
    }
}
