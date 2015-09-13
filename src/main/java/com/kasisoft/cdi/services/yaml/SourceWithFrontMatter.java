package com.kasisoft.cdi.services.yaml;

import lombok.experimental.*;

import lombok.*;

import java.util.*;

/**
 * Representation of content providing some frontmatter content.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class SourceWithFrontMatter {

  String                content = "";
  
  @Setter(AccessLevel.PRIVATE)
  Map<String,Object>    data    = new HashMap<>();
  
} /* ENDCLASS */
