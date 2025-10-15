package com.plgdhd;

import com.plgdhd.interfaces.Autowired;
import com.plgdhd.interfaces.Component;
import com.plgdhd.interfaces.InitializingBean;
import com.plgdhd.interfaces.Scope;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private Map<Class<?>, Object> beans = new HashMap<>();
    private Set<Class<?>> components = new HashSet<>();
    private Reflections reflections;

    public ApplicationContext(String packageToScan) {
        reflections = new Reflections(packageToScan);
        components = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> clazz : components) {
            Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
            String scope = (scopeAnnotation != null) ? scopeAnnotation.value() : "singleton";
            if ("singleton".equals(scope)) {
                try{
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    beans.put(clazz, instance);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        for (Object bean : beans.values()) {
            injectDependencies(bean);
        }

        for (Object bean : beans.values()) {
            if (bean instanceof InitializingBean) {
                try {
                    ((InitializingBean) bean).afterPropertiesSet();
                } catch (Exception e) {
                    throw new RuntimeException("Initialize failed " + bean.getClass(), e);
                }
            }
        }
    }

    private void injectDependencies(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = getBean(fieldType);
                if (dependency != null) {
                    field.setAccessible(true);
                    try {
                        field.set(bean, dependency);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed " + field, e);
                    }
                } else {
                    throw new RuntimeException("Not found  " + fieldType);
                }
            }
        }
    }

    public <T> T getBean(Class<T> type) {
        Object singleton = findSingletonBean(type);
        if (singleton != null) {
            return type.cast(singleton);
        }

        Class<?> protoClass = findPrototypeClass(type);
        if (protoClass != null) {
            try {
                @SuppressWarnings("unchecked")
                T instance = (T) protoClass.getDeclaredConstructor().newInstance();
                injectDependencies(instance);
                if (instance instanceof InitializingBean) {
                    ((InitializingBean) instance).afterPropertiesSet();
                }
                return instance;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create prototype " + type, e);
            }
        }

        throw new RuntimeException("Bean not found for " + type);
    }

    private Object findSingletonBean(Class<?> type) {
        Object candidate = null;
        for (Object bean : beans.values()) {
            if (type.isInstance(bean)) {
                if (candidate != null) {
                    throw new RuntimeException("Multiple singleton beans found for type " + type);
                }
                candidate = bean;
            }
        }
        return candidate;
    }

    private Class<?> findPrototypeClass(Class<?> type) {
        Class<?> classToInject = null;
        for (Class<?> clazz : components) {
            Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
            String scope = (scopeAnnotation != null) ? scopeAnnotation.value() : "singleton";
            if ("prototype".equals(scope) && type.isAssignableFrom(clazz)) {
                if (classToInject != null) {
                    throw new RuntimeException("Multiple prototype classes found for type " + type);
                }
                classToInject = clazz;
            }
        }
        return classToInject;
    }
}