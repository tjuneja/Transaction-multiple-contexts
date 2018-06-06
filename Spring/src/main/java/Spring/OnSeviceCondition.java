package Spring;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class OnSeviceCondition implements ConfigurationCondition {


    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        OnSeviceCondition.InspectMetaData inspect;
        boolean allBeansMatched = false;
        if (annotatedTypeMetadata.isAnnotated(ConditionalOnService.class.getName())) {
            inspect = new OnSeviceCondition.InspectMetaData(conditionContext, annotatedTypeMetadata, ConditionalOnService.class);
            allBeansMatched = this.getMatchingBeans(conditionContext,inspect);
        }
        return allBeansMatched;
    }

    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }

    private boolean getMatchingBeans(ConditionContext context, OnSeviceCondition.InspectMetaData beans) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        if (beans.getStrategy() == SearchStrategy.ANCESTORS) {
            BeanFactory parent = beanFactory.getParentBeanFactory();
            Assert.isInstanceOf(ConfigurableListableBeanFactory.class, parent, "Unable to use SearchStrategy.ANCESTORS");
            beanFactory = (ConfigurableListableBeanFactory)parent;
        }

        if (beanFactory == null) {
            return false;
        } else {
            List<String> beanNamesFound = new ArrayList<String>();
            List<String> beanNamesListed = new ArrayList<String>();

            Iterator iterator = beans.getNames().iterator();
            String beanName;
            while(iterator.hasNext()) {
                beanName = (String)iterator.next();
                beanNamesListed.add(beanName);
                if (this.containsBean(beanFactory, beanName)) {
                    beanNamesFound.add(beanName);
                }
            }

            return beanNamesFound.containsAll(beanNamesListed);
        }
    }

    private boolean containsBean(ConfigurableListableBeanFactory beanFactory, String beanName) {
        return  beanFactory.containsSingleton(beanName);

    }

    private static class InspectMetaData{

        private final Class<?> annotationType;
        private final List<String> names = new ArrayList();
        private final SearchStrategy strategy;

        InspectMetaData(ConditionContext context, AnnotatedTypeMetadata metadata, Class<?> annotationType) {
            this.annotationType = annotationType;
            this.strategy = (SearchStrategy)metadata.getAnnotationAttributes(annotationType.getName()).get("search");
            MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(annotationType.getName(), true);
            this.collect(attributes, "name", this.names);
        }

        public Class<?> getAnnotationType() {
            return annotationType;
        }

        public List<String> getNames() {
            return names;
        }

        public SearchStrategy getStrategy() {
            return this.strategy!=null? this.strategy: SearchStrategy.ALL;
        }

        protected void collect(MultiValueMap<String, Object> attributes, String key, List<String> destination) {
            List<?> values = (List)attributes.get(key);
            if (values != null) {
                Iterator iterator = values.iterator();

                while(iterator.hasNext()) {
                    Object value = iterator.next();
                    if (value instanceof String[]) {
                        Collections.addAll(destination, (String[])((String[])value));
                    } else {
                        destination.add((String)value);
                    }
                }
            }

        }
    }


}
