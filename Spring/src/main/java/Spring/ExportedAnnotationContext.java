package Spring;

import org.springframework.beans.factory.config.BeanDefinition;

public class ExportedAnnotationContext {
    AnnotationStrategy strategy;

    public void setStrategy(AnnotationStrategy annotationStrategy){
      this.strategy = annotationStrategy;
    }

    public boolean isAnnotatedWithExport(BeanDefinition beanDefinition){
        return strategy.isAnnotatedWithExport(beanDefinition);
    }


}
