package dk.mmba.util.builder.vaadin.factory

class BeanFactory extends AbstractFactory {
    final Class beanClass

    public BeanFactory(Class beanClass) {
        this.beanClass = beanClass
    }

    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return beanClass.newInstance()
    }
}
