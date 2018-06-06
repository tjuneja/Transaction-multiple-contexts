package Spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.StandardMethodMetadata;

public class ConfigurationBeanDefinitionStrategy implements AnnotationStrategy {

    @Override
    public boolean isAnnotatedWithExport(BeanDefinition beanDefinition) {
      return  ((StandardMethodMetadata) beanDefinition
                .getSource())
                .isAnnotated(Exported.class.getName());
    }
}
