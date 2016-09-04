package com.kasisoft.cdi.services.yaml;

import java.io.*;

/**
 * This loader allows to read some content while splitting it into content and frontmatter data. Each loader instance
 * is thread safe.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
public interface YamlSaver<T> {

  void save( Writer writer, Object data );
  
} /* ENDINTERFACE */
