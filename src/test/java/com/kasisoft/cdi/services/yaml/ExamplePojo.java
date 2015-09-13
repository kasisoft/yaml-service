package com.kasisoft.cdi.services.yaml;

import com.kasisoft.cdi.services.yaml.annotation.*;

import lombok.*;

import java.util.*;

@Data
public class ExamplePojo {

  String                       key;
  
  @Type(ExampleMember.class)
  List<ExampleMember>          list;
  
  @EntryType(key = Integer.class, value = ExampleMember.class)
  Map<Integer,ExampleMember>   map;
  
} /* ENDCLASS */
