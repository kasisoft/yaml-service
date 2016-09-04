package com.kasisoft.cdi.services.yaml;

import lombok.experimental.*;

import lombok.*;

import java.util.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data @EqualsAndHashCode(callSuper = true)
public class ExampleSource extends SourceWithFrontMatter {

  List<String>    items = new ArrayList<>();
  
} /* ENDLASS */
