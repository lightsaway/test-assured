package com.lightsaway.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Factory to create subapis
 */
public class ApiFactory {

    private static void initSubApis(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields){
            Annotation annotation = field.getAnnotation(SubApi.class);
            if( annotation != null){

                SubApi subApi = (SubApi) annotation;
                field.setAccessible(true);
                Class clazz = field.getType();

                try {
                    Object instance = clazz.newInstance();
                    Api subApiInstance = (Api) instance;

                    for(Class c : subApi.filtersBlackList()){
                        subApiInstance.blackListFilter(c);
                    }
                    subApiInstance.setStore(((Api)obj).getStore());

                    field.set(obj, instance);

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Not able to instantiate field " + field.getName(), e.getCause());
                } catch (InstantiationException e) {
                    throw new RuntimeException("Not able to instantiate field " + field.getName(), e.getCause());
                }

            }
        }

    }

    private static void initPath(Object obj){
        Annotation annotation = obj.getClass().getAnnotation(Resource.class);
        if( annotation != null && obj instanceof Api) {
            Resource resource = (Resource) annotation;
            Api api = (Api) obj;
            api.setPath(resource.path());
        }
    }

    public static void init(Object obj){
        initSubApis(obj);
        initPath(obj);
    }

}
