package Spring;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class ApplicationContextTracker implements ApplicationContextAware {

    private Map<String, Integer> beanReferenceCounter = new HashMap<String, Integer>();
    private StringBuilder outputMessage;
    private Map<String,ConfigurableListableBeanFactory> initializedServices = new
                                                                              HashMap<String, ConfigurableListableBeanFactory>();


    private Map<String,ConfigurableListableBeanFactory> unInitializedServices = new
            HashMap<String, ConfigurableListableBeanFactory>();


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        outputMessage = new StringBuilder();
        beanReferenceCounter.clear();
        outputMessage.append("--- ApplicationContext begin ---\n");
        readApplicationContext(context);
        getBeansWithoutReference();
        outputMessage.append("--- ApplicationContext end ---\n");
        System.out.print(outputMessage);
    }

    private void getBeansWithoutReference() {
        System.out.println("Beans with dependency not satisfied:\n");
        for (String bean : beanReferenceCounter.keySet()) {
            if (beanReferenceCounter.get(bean) == 0) {

            }
        }
    }

    private void initBeanReferenceIfNotExist(String beanName) {
        Integer count = beanReferenceCounter.get(beanName);
        if (count == null) {

            beanReferenceCounter.put(beanName, 0);
        }
    }

    private void increaseBeanReference(String beanName) {
        Integer count = beanReferenceCounter.get(beanName);
        if (count == null) {
            count = new Integer(0);
        }
        beanReferenceCounter.put(beanName, ++count);
    }

    private void readApplicationContext(ApplicationContext context) {
        String appContextInfo = String.format("ApplicationContext %s : %s", context.getId(), context.getClass()
                .getName());
        ApplicationContext parent = context.getParent();
        if (parent != null) {
            System.out.println( appContextInfo += String.format(" -> %s", parent.getId()));
        }
        outputMessage.append(appContextInfo).append('\n');

        AbstractRefreshableApplicationContext applicationContext = (AbstractRefreshableApplicationContext) context;
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();

        for (String beanName : factory.getBeanDefinitionNames()) {
            if (factory.getBeanDefinition(beanName).isAbstract()) {
                continue;
            }
            initBeanReferenceIfNotExist(beanName);
            Object bean = factory.getBean(beanName);
            for (String dependency : factory.getDependenciesForBean(beanName)) {
                increaseBeanReference(dependency);
            }
        }

        if (parent != null) {
            readApplicationContext(parent);
        }
    }

}