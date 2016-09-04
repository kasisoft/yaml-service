package com.kasisoft.cdi.services.yaml;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.DumperOptions.*;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.*;

import com.kasisoft.cdi.services.yaml.annotation.*;
import com.kasisoft.cdi.services.yaml.annotation.Type;
import com.kasisoft.cdi.services.yaml.internal.*;
import com.kasisoft.libs.common.text.*;

import lombok.experimental.*;

import lombok.*;

import javax.inject.*;

import javax.ejb.*;
import javax.ejb.Singleton;

import java.util.function.*;

import java.util.*;

import java.lang.reflect.*;

/**
 * The service is used to provide Yaml parsers and loaders for source with front matter configurations.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@Named @Singleton
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class YamlService {

  private static final String DEFAULT_FM_MARKER = "---";

  ThreadLocal<Map<Class<?>, Yaml>>    yamlInstances;
  
  @Getter
  String                              frontMatterMarker;
  
  Map<Class<?>,Constructor>           constructors;
  
  public YamlService() {
    frontMatterMarker = DEFAULT_FM_MARKER;
    constructors      = new Hashtable<>();
    yamlInstances     = new ThreadLocal<Map<Class<?>,Yaml>>() {

      @Override
      protected Map<Class<?>, Yaml> initialValue() {
        // we're using the simple approach here. we could use an explicit root type provided
        // by the caller but that wouldn't allow to use lenient pojos as each yaml node would
        // have to exist as a property
        return new Hashtable<>();
      }

    };
    
  }

  /**
   * Alters the marker which has to be used. Note that you need to recreate loaders in order to make use of the
   * updated markers.
   * 
   * @param newmarker   The new marker. If <code>null</code> or empty the default '---' is being used.
   */
  public void setFrontMatterMarker( String newmarker ) {
    String newvalue = StringFunctions.cleanup( newmarker );
    if( newvalue == null ) {
      newvalue = DEFAULT_FM_MARKER;
    }
    frontMatterMarker = newvalue;
  }
  
  
  /**
   * Returns a default loader for Yaml based texts.
   * 
   * @return   A default loader for Yaml bases texts. Not <code>null</code>.
   */
  public YamlLoader newSourceLoader() {
    return new SourceLoader<>( frontMatterMarker, this, SourceWithFrontMatter::new, new DefaultFrontMatterConfigurator<>() );
  }

  /**
   * Returns a loader for Yaml based texts for specific {@link SourceWithFrontMatter}.
   * 
   * @return   A loader for Yaml based texts for specific {@link SourceWithFrontMatter}. Not <code>null</code>.
   */
  public <T extends SourceWithFrontMatter> YamlLoader<T> newSourceLoader( @NonNull Supplier<T> factory ) {
    return new SourceLoader<>( frontMatterMarker, this, factory, new DefaultFrontMatterConfigurator<T>() );
  }

  /**
   * Returns a loader for Yaml based texts for specific {@link SourceWithFrontMatter}.
   * 
   * @return   A loader for Yaml based texts for specific {@link SourceWithFrontMatter}. Not <code>null</code>.
   */
  public <T extends SourceWithFrontMatter> YamlLoader<T> newSourceLoader( @NonNull Supplier<T> factory, @NonNull BiConsumer<T,Object> configurator ) {
    return new SourceLoader<>( frontMatterMarker, this, factory, configurator );
  }
  
  public <T> YamlLoader<T> newLoader( @NonNull Class<T> targetClass ) {
    return new ObjectLoader<>( targetClass, this );
  }

  public <T> YamlSaver<T> newSaver() {
    return new ObjectSaver<>( this );
  }

  /**
   * Returns the current thread local {@link Yaml} instance.
   * 
   * @return   The current thread local {@link Yaml} instance. Not <code>null</code>.
   */
  public Yaml yaml() {
    return yaml( Object.class );
  }
  
  /**
   * Returns the current thread local {@link Yaml} instance.
   * 
   * @return   The current thread local {@link Yaml} instance. Not <code>null</code>.
   */
  public Yaml yaml( @NonNull Class<?> targetClass ) {
    synchronized( yamlInstances ) {
      Map<Class<?>,Yaml> map    = yamlInstances.get();
      Yaml               result = map.get( targetClass );
      if( result == null ) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle( FlowStyle.BLOCK );
        options.setIndent(2);
        result = new Yaml( getConstructor( targetClass ), new Representer(), options );
        map.put( targetClass, result );
      }
      return result;
    }
  }
  
  private Constructor getConstructor( Class<?> clazz ) {
    synchronized( constructors ) {
      Constructor result = constructors.get( clazz );
      if( result == null ) {
        result = buildConstructor( clazz );
        constructors.put( clazz, result );
      }
      return result;
    }
  }

  private Constructor buildConstructor( Class<?> clazz ) {
    Constructor     result      = new Constructor( clazz );
    TypeDescription description = new TypeDescription( clazz );
    boolean         use         = false;
    Field[]         fields      = clazz.getDeclaredFields();
    if( fields != null ) {
      for( Field field : fields ) {
        EntryType entryType = field.getAnnotation( EntryType.class );
        Type      type      = field.getAnnotation( Type.class );
        if( (entryType != null) || (type != null) ) {
          String fieldName = field.getName();
          use              = true;
          if( type != null ) {
            description.putListPropertyType( fieldName, type.value() );
          } else if( entryType != null ) {
            description.putMapPropertyType( fieldName, entryType.key(), entryType.value() );
          }
        }
      }
    }
    if( use ) {
      result.addTypeDescription( description );
    }
    return result;
  }

} /* ENDCLASS */
