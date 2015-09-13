package com.kasisoft.cdi.services.yaml;

import java.util.function.*;

import java.util.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class ExampleSourceConfigurator implements BiConsumer<ExampleSource,Object> {

  @Override
  public void accept( ExampleSource t, Object u ) {
    if( u instanceof Map ) {
      Map<String,Object> map = (Map<String,Object>) u;
      for( Map.Entry<String,Object> entry : map.entrySet() ) {
        if( "list".equals( entry.getKey() ) ) {
          t.setItems( (List<String>) entry.getValue() );
        } else {
          t.getData().put( entry.getKey(), entry.getValue() );
        }
      }
    }
  }

}
