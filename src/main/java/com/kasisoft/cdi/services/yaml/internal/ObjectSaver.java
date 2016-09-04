package com.kasisoft.cdi.services.yaml.internal;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.introspector.*;

import com.kasisoft.cdi.services.yaml.*;

import lombok.*;

import java.io.*;

/**
 * This loader allows to read some content while splitting it into content and frontmatter data.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class ObjectSaver<T> implements YamlSaver<T> {

  YamlService   service;
  
  /**
   * Instatiates this loader.
   * 
   * @param serviceInstance   The service used to provide a Yaml instance. Not <code>null</code>.
   */
  public ObjectSaver( YamlService serviceInstance ) {
    service = serviceInstance;
  }
  
  @Override
  public void save( @NonNull Writer writer, @NonNull Object data ) {
    Yaml yaml = service.yaml( data.getClass() );
    yaml.setBeanAccess( BeanAccess.FIELD );
    StringWriter w = new StringWriter();
    yaml.dump( data, w );
    System.err.println( w.toString() );
    try {
      writer.write( w.toString() );
    } catch( IOException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
} /* ENDCLASS */
