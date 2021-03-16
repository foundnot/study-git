package base.reflex;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BeanToMap {

    public static Map<String, String> toMap(Object bean){
        Map<String, String> map = new HashMap<>();
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        for(Field field : fields){
            String name = field.getName();
            try {
                field.setAccessible(true);
                Object obj = field.get(bean);
                map.put(name, String.valueOf(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        log.info("map:{}", map);
        return map;
    }

    public static Map<String, String> toMap2(Object bean) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Map<String, String> map = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            if (!propertyName.equals("class")) {
                Method method = propertyDescriptor.getReadMethod();
                Object obj = method.invoke(bean);
                if (obj != null) {   // 过滤掉为空的字段
                    map.put(propertyName, String.valueOf(obj));
                }
            }
        }
        return map;
    }
}
