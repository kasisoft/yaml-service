package com.kasisoft.cdi.services.yaml.internal;

import com.kasisoft.libs.common.io.*;

import com.kasisoft.cdi.services.yaml.*;
import com.kasisoft.libs.common.text.*;

import lombok.*;

import java.util.function.*;

import java.io.*;

/**
 * This loader allows to read some content while splitting it into content and frontmatter data.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public class SourceLoader<T extends SourceWithFrontMatter> implements YamlLoader<T> {

  YamlService            service;
  Supplier<T>            supplier;
  BiConsumer<T,Object>   configurator;
  String                 fmMarker;
  int                    fmLength;
  
  /**
   * Instatiates this loader.
   * 
   * @param marker            The marker for front matter data. Neither <code>null</code> nor empty.
   * @param serviceInstance   The service used to provide a Yaml instance. Not <code>null</code>.
   * @param factory           The supplier used to create a {@link SourceWithFrontMatter} instance. Not <code>null</code>.
   * @param applicator        A {@link BiConsumer} used to configure the {@link SourceWithFrontMatter} instance. Not <code>null</code>.
   */
  public SourceLoader( String marker, YamlService serviceInstance, Supplier<T> factory, BiConsumer<T,Object> applicator ) {
    service       = serviceInstance;
    supplier      = factory;
    configurator  = applicator;
    fmMarker      = marker;
    fmLength      = fmMarker.length();
  }
  
  @Override
  public T load( @NonNull Reader reader ) {
    T      result = supplier.get();
    String text   = IoFunctions.readTextFully( reader );
    result.setContent( text );
    int    open   = text.indexOf( fmMarker );
    if( open != -1 ) {
      int close = text.indexOf( fmMarker, open + fmLength );
      if( close != -1 ) {
        String frontMatterText    = text.substring( open + fmLength, close ).trim();
        text                      = StringFunctions.trim( text.substring( close + fmLength ), Boolean.TRUE );
        result.setContent( text );
        Iterable<Object> data     = service.yaml().loadAll( frontMatterText );
        data.forEach( $ -> configurator.accept( result, $ ) );
      }
    }
    return result;
  }
  
} /* ENDCLASS */
