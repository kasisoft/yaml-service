package com.kasisoft.cdi.services.yaml.annotation;

import java.lang.annotation.*;

/**
 * Only applicable to list/set types.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Type {
  
  Class<?> value() default String.class;
  
} /* ENDANNOTATION */
