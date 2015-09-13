package com.kasisoft.cdi.services.yaml.internal;

import com.kasisoft.libs.common.i18n.*;

import lombok.*;
import lombok.experimental.*;

/**
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Messages {

  
  static {
    I18NSupport.initialize( Messages.class );
  }

} /* ENDCLASS */
