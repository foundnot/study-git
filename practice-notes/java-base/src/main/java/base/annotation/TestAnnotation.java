package base.annotation;

import java.lang.annotation.*;

@Documented
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    String name();
    String type();
    int length();

    String value() default "this is field annotation";
}
