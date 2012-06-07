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

import com.vaadin.ui.ComponentContainer
import com.vaadin.ui.Panel

/**
 * @author <a href="mailto:art@inet.ua">Artem Nikolaienko</a>
 */

class PanelFactory extends ComponentContainerFactory {

    PanelFactory() {
        super(Panel)
    }

    PanelFactory(Class beanClass) {
        super(beanClass)
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        //TODO: Implement factory which will setContent if there is only one child of ComponentContainer
        //otherwise it should add children via addComponent
        if (child instanceof ComponentContainer) {
            ((Panel) parent).setContent(child)
        } else {
            super.setChild(builder, parent, child)
        }

    }


}
