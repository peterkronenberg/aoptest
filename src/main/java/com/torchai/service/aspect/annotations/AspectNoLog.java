package com.torchai.service.aspect.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class, method and parameter level annotation to turn off automatic logging.
 * Adding it to class, method or parameter disables logging for it.
 * Annotation on method takes precedence over that on class.
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectNoLog {
//    JsonIgnore jsonIgnore();

}
