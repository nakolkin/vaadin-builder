package dk.mmba.util.builder.vaadin.factory

/**
 * 05.07.11 12:40 arni
 * Copyright (c) 2011 2M business application a|s.
 * All rights reserved.
 */
class WidgetFactory extends AbstractFactory {
    final Class restrictedType;

    public WidgetFactory(Class restrictedType) {
        this.restrictedType = restrictedType
    }

    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        if ((value != null) && FactoryBuilderSupport.checkValueIsType(value, name, restrictedType)) {
            return value;
        } else {
            throw new RuntimeException("$name must have either a value argument or an attribute named $name that must be of type $restrictedType.name");
        }
    }

}
