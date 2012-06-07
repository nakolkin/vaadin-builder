/*   
 * Copyright 2009 Rob Schoening
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.vaadin.ui;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VaadinBeanInfoHelper extends SimpleBeanInfo {
    Logger log = Logger.getLogger(getClass().getName());
    Class targetClass;

    public VaadinBeanInfoHelper(Class targetClass) {
        super();

        this.targetClass = targetClass;

    }

    private String propertyCaseConvert(String input) {
        if (input.length() > 1 && Character.isUpperCase(input.charAt(0))
                && Character.isUpperCase(input.charAt(1))) {

            return input;
        } else {
            return Character.toLowerCase(input.charAt(0)) + input.substring(1);
        }
    }

    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEventSetDescriptors(targetClass);
    }

    protected EventSetDescriptor[] getEventSetDescriptors(Class targetClass) {
        Set<EventSetDescriptor> descriptorList = new HashSet<EventSetDescriptor>();
        try {

            // Get the default BeanInfo
            BeanInfo defaultBeanInfo = Introspector.getBeanInfo(targetClass,
                    Introspector.IGNORE_IMMEDIATE_BEANINFO);

            // Extract the default EventSetDescriptors
//            EventSetDescriptor[] parentDescriptors = defaultBeanInfo.getEventSetDescriptors();
//            if (parentDescriptors != null) {
            //    descriptorList.addAll(Arrays.asList(parentDescriptors));
//            }

            // Now add all the Listener event sets
            descriptorList.addAll(process(defaultBeanInfo, "Listener"));

            // Now add all the Handler event sets
            descriptorList.addAll(process(defaultBeanInfo, "Handler"));

            return descriptorList.toArray(new EventSetDescriptor[descriptorList.size()]);
        } catch (IntrospectionException e) {
            log.log(Level.SEVERE, "", e);
            return null;
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            return null;
        }
    }

    /**
     * Extracts Handler/Listener EventSetDescriptors that aren't processed by
     * the Java Introspector. Vaadin uses inner classes for many of the
     * Listener/Handler interfaces, which are missed by the default
     * Introspection mechanism.
     *
     * @param defaultBeanInfo the Introspector's default BeanInfo for the bean being
     *                        introspected
     * @param listenerType    "Handler" or "Listener"
     * @return A list of EventSetDescriptor instances that have been derived
     */
    private List<EventSetDescriptor> process(BeanInfo defaultBeanInfo,
                                             String listenerType) {
        Class targetClass = defaultBeanInfo.getBeanDescriptor().getBeanClass();
        if (log.isLoggable(Level.FINER)) {

            log.finer("Extracting " + listenerType + " event sets from "
                    + defaultBeanInfo.getBeanDescriptor().getBeanClass());
        }
        List<EventSetDescriptor> descriptorList = new ArrayList<EventSetDescriptor>();

        // Loop through all the methods, looking for names of the form
        // addXXX[listenerType]
        // e.g. addXXXHandler, addXXXListener
        Method[] methods = targetClass.getMethods();
        for (int i = 0; methods != null && i < methods.length; i++) {
            Method m = methods[i];
            String methodName = methods[i].getName();
            if (methodName.startsWith("add")
                    && methodName.endsWith(listenerType)) {

                String methodRoot = methodName.substring("add".length());

                // look only for signatures with a single argument
                Class parameterTypes[] = m.getParameterTypes();
                if (parameterTypes != null && parameterTypes.length == 1) {

                    // Extract the "eventSetName"
                    // For a given class:
                    // com.vaadin.event.ItemClickEvent$ItemClickListener
                    // The eventSetName will be 'itemClick'
                    String eventSetName = extractEventSetName(
                            parameterTypes[0], listenerType);

                    // Now we look at the methods declared on the Listener
                    // interface
                    Method listenerMethods[] = parameterTypes[0].getMethods();
                    Method removeListenerMethod = findRemoveMethod(targetClass,
                            m);

                    List<Method> tmp = new LinkedList<Method>();
                    // For now, just log the listener methods. We won't filter
                    // them. Perhaps we should
                    for (int k = 0; listenerMethods != null
                            && k < listenerMethods.length; k++) {


                        tmp.add(listenerMethods[k]);


                        log.finer("eventSetName=" + eventSetName
                                + " class="
                                + targetClass.getName() + " action="
                                + listenerMethods[k].getName());


                    }
                    listenerMethods = tmp.toArray(new Method[tmp.size()]);

                    try {

                        EventSetDescriptor esd = new EventSetDescriptor(
                                eventSetName, parameterTypes[0],
                                listenerMethods, m, removeListenerMethod);
                        descriptorList.add(esd);

                    } catch (IntrospectionException e) {
                        log.log(Level.WARNING,
                                "problem extracting descriptors", e);
                    }
                }

            }
        }
        return descriptorList;
    }

    /**
     * Find the removeXXXListener() method for a corresponding addXXXListener()
     * method.
     */
    private Method findRemoveMethod(Class c, Method addMethod) {
        try {
            String removeMethodName = "remove" + addMethod.getName().substring("add".length());
            return c.getMethod(removeMethodName, addMethod.getParameterTypes()[0]);
        } catch (NoSuchMethodException e) {
            // If there isn't a removeXXX() method, that's just the way it is
            return null;
        }
    }

    private String extractEventSetName(Class paramTypeClass, String listenerType) {
        // paramTypeClassName might look like
        // com.vaadin.event.ItemClickEvent$ItemClickListener
        String paramTypeClassName = paramTypeClass.getName();
        String listenerCommonName;
        String eventSetName;
        // Vaadin uses inner classes for the listeners. We only want
        // the value on the right of the $.
        // For instance, given
        // com.vaadin.event.ItemClickEvent$ItemClickListener,
        // the common name for the listener is ItemClickListener

        int idx = paramTypeClassName.lastIndexOf("$");
        if (idx > 0) {
            listenerCommonName = paramTypeClassName.substring(idx + 1);
        } else {
            listenerCommonName = paramTypeClassName
                    .substring(paramTypeClassName.lastIndexOf(".") + 1);
        }

        // JavaBeans expect the case to be converted for the "eventSetName"
        // i.e. ItemClickListener ==> itemClickListener
        eventSetName = propertyCaseConvert(listenerCommonName);

        // the eventSetName needs to have Listener/Handler stripped off
        // i.e. itemClickListener ==> itemClick
        if (eventSetName.endsWith(listenerType)) {
            eventSetName = eventSetName.substring(0, eventSetName.length()
                    - listenerType.length());
        }

        return eventSetName;
    }
}
