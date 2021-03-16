package base.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestClassAnnotation {
    String name();
    String type();
    int length();

    String value() default "this is test annotation";
}
