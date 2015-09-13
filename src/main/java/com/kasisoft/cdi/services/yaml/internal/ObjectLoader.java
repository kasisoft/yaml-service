package com.kasisoft.cdi.services.yaml.internal;

import org.yaml.snakeyaml.*;

import com.kasisoft.cdi.services.yaml.*;

import lombok.*;

import java.io.*;

/**
 * This loader allows to read some content while splitting it into content and frontmatter data.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class ObjectLoader<T> implements YamlLoader<T> {

  YamlService   service;
  Class<T>      targetClass;
  
  /**
   * Instatiates this loader.
   * 
   * @param target            The target class that is supposed to be loaded. Not <code>null</code>.
   * @param serviceInstance   The service used to provide a Yaml instance. Not <code>null</code>.
   */
  public ObjectLoader( Class<T> target, YamlService serviceInstance ) {
    service       = serviceInstance;
    targetClass   = target;
  }
  
  @Override
  public T load( @NonNull Reader reader ) {
    Yaml yaml = service.yaml( targetClass );
    return (T) yaml.load( reader );
  }
  
} /* ENDCLASS */
