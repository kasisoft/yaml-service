package com.kasisoft.cdi.services.yaml.annotation;

import java.lang.annotation.*;

/**
 * Only applicable to map types.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntryType {

  Class<?> key() default String.class;
  
  Class<?> value();
  
} /* ENDANNOTATION */
