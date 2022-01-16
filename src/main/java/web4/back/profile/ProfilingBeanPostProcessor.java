package web4.back.profile;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

@Component
public class ProfilingBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().getDeclaredAnnotation(Profile.class) != null){
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Date date = new Date();
                    Object result = method.invoke(bean, args);
                    Long executeTime = new Date().getTime() - date.getTime();
                    System.out.println(method.getName() + " execute for " + executeTime);
                    return result;
                }
            });
        }else {
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
    }
}
