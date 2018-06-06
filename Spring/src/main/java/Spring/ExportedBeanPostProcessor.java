package Spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.StandardMethodMetadata;

/*
* BeanPostProcessor Implementation to check for the exported annotation, for the beans that are to be exposed to other
* modules. This approach sits well with the idea of modularity.
* We are only exposing the beans that are to be shared with other modules,other beans would be private to the
* module itself. The parent-child context hierarchy is maintained and the shared beans are accessed via the bean factory
* of the parent context
*
* */

@ConditionalOnBean
public class ExportedBeanPostProcessor implements BeanPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    public ExportedBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {


            // System.out.println("Bean Name = " + beanName);
        return bean;
    }



    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (hasExportedAnnotation(beanName)) {
            ConfigurableListableBeanFactory parent = getParentBeanFactory();

            if (parent != null) {

                // registering the bean with the bean factory of the parent context
                if(parent.getSingleton(beanName) == null)
                   parent.registerSingleton(beanName, bean);
            }
        }
        return bean;
    }

    private boolean hasExportedAnnotation(String beanName) {
        BeanDefinition bd;
        try {
            bd = beanFactory.getBeanDefinition(beanName);

        } catch (NoSuchBeanDefinitionException exception) {
            return false;
        }
        ExportedAnnotationContext context = new ExportedAnnotationContext();

        if ((bd.getSource() instanceof StandardMethodMetadata) ) {
              // This strategy is used to read the metadata from the beans exposed via a configuration class
              context.setStrategy(new ConfigurationBeanDefinitionStrategy());
        }
        else if(bd instanceof ScannedGenericBeanDefinition){
            // This strategy is used to handle the Generic Beans which are defined via xml,or a service that is annotated
            // with the "Exported" annotation.
            context.setStrategy(new GenericBeanDefinitionStrategy());
        }
        else{
            return false;
        }

        return context.isAnnotatedWithExport(bd);
    }

    private ConfigurableListableBeanFactory getParentBeanFactory() {
        // The configurable bean factory is aware of the hierarchy and we can access the parent factory from the instance
        BeanFactory parent = beanFactory.getParentBeanFactory();
        return (parent instanceof ConfigurableListableBeanFactory)
                ? (ConfigurableListableBeanFactory) parent : null;
    }
}