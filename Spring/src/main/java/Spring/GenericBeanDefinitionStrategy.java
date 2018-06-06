package Spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;

public class GenericBeanDefinitionStrategy implements  AnnotationStrategy{
    @Override
    public boolean isAnnotatedWithExport(BeanDefinition beanDefinition) {
        return ((AnnotationMetadataReadingVisitor)((ScannedGenericBeanDefinition) beanDefinition)
                .getMetadata())
                .getAnnotationTypes()
                .contains("com.eq.demo.springboot.Spring.Exported");
    }
}
