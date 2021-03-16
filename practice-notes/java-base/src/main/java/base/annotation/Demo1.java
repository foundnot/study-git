package base.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Demo1 {

    @Test
    public void testAnnotation() {
        TestClassAnnotation annotation = CarInfo.class.getAnnotation(TestClassAnnotation.class);
        log.info("value:{}", annotation.value());
        log.info("name:{}", annotation.name());
    }

    @Test
    public void testReflection(){
        Class<CarInfo> carInfoClass = CarInfo.class;
        log.info("annotation name:{}", carInfoClass.getCanonicalName());
        // 获取Class字节码上某个注解
        TestClassAnnotation testClassAnnotation = carInfoClass.getDeclaredAnnotation(TestClassAnnotation.class);
        log.info("value:{}", testClassAnnotation.value());
        log.info("name:{}", testClassAnnotation.name());
        log.info("length:{}", testClassAnnotation.length());
        log.info("value:{}", testClassAnnotation.value());

//        Method[] methods = carInfoClass.getMethods();
//        for(Method method : methods){
//            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
//            if(declaredAnnotations.length > 0){
//                for(Annotation annotation : declaredAnnotations){
//                    TestAnnotation testAnnotation = (TestAnnotation) annotation;
//                    Class<? extends Annotation> aClass = annotation.annotationType();
//                    log.info("getCanonicalName:{}", aClass.getCanonicalName());
//
//                    log.info("value:{}", testAnnotation.value());
//                    log.info("name:{}", testAnnotation.name());
//                    log.info("length:{}", testAnnotation.length());
//                    log.info("value:{}", testAnnotation.value());
//                }
//            }
//        }
//
//        Annotation[] declaredAnnotations = carInfoClass.getDeclaredAnnotations();
//        for(Annotation annotation : declaredAnnotations){
//            Class<? extends Annotation> aClass = annotation.annotationType();
//            log.info("getCanonicalName:{}", aClass.getCanonicalName());
//            TestClassAnnotation testAnnotation = (TestClassAnnotation) annotation;
//            log.info("value:{}", testAnnotation.value());
//            log.info("name:{}", testAnnotation.name());
//            log.info("length:{}", testAnnotation.length());
//            log.info("value:{}", testAnnotation.value());
//        }
    }

}
