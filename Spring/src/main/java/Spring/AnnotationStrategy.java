package Spring;

import org.springframework.beans.factory.config.BeanDefinition;

public interface AnnotationStrategy {

    public boolean isAnnotatedWithExport(BeanDefinition beanDefinition);
}
