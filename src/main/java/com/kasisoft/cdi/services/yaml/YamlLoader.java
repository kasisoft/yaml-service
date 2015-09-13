package com.kasisoft.cdi.services.yaml;

import java.io.*;

/**
 * This loader allows to read some content while splitting it into content and frontmatter data. Each loader instance
 * is thread safe.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public interface YamlLoader<T> {

  /**
   * Returns the source instance.
   * 
   * @param reader   The reader used to provide the content. Not <code>null</code>.
   * 
   * @return   The source instance. Not <code>null</code>.
   */
  T load( Reader reader );
  
} /* ENDINTERFACE */
