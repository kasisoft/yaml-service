package com.kasisoft.cdi.services.yaml;

import java.util.function.*;

import java.util.*;

/**
 * Default implementation of a configurator for {@link SourceWithFrontMatter}.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class DefaultFrontMatterConfigurator<T extends SourceWithFrontMatter> implements BiConsumer<T,Object> {

  @Override
  public void accept( T t, Object u ) {
    if( u instanceof Map ) {
      Map<String,Object> map = (Map<String,Object>) u;
      map.keySet().forEach( k -> t.getData().put( k, map.get(k) ) );
    }
  }

} /* ENDCLASS */
