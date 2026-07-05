package com.ariel.disha.mall.init;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author ariel
 * @apiNote AbstractInjectBean
 * @serial
 */
@Component
public class InjectBeans implements BeanFactoryAware {

    private static BeanFactory BEAN_FACTORY;

    @Override
    public void setBeanFactory(@NotNull BeanFactory beanFactory) throws BeansException {
        BEAN_FACTORY = beanFactory;
    }

    public static <T> Supplier<T> inject(Class<T> bClass) {
        return new BeanWrapper<>(bClass);
    }

    public static class BeanWrapper<T> implements Supplier<T> {

        private final Class<T> tClass;

        private T bean;

        public BeanWrapper(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public T get() {
            if (bean == null) {
                bean = BEAN_FACTORY.getBean(tClass);
            }
            return bean;
        }

    }
}
