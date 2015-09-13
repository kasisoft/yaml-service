package com.kasisoft.cdi.services.yaml;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import org.testng.annotations.*;

import com.kasisoft.libs.common.constants.*;

import com.kasisoft.libs.common.io.*;

import lombok.experimental.*;

import lombok.*;

import java.util.*;

import java.net.*;

import java.io.*;

/**
 * Tests for {@link YamlService}.
 * 
 * @author daniel.kasmeroglu@kasisoft.net
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YamlServiceTest {

  YamlService   yamlService;
  
  @BeforeTest
  public void setup() {
    yamlService = new YamlService();
  }
  
  private InputStream openResource( String file ) {
    String ref = String.format( "/yamlfiles/%s", file );
    URL    url = getClass().getResource( ref );
    assertNotNull( url );
    return IoFunctions.newInputStream( url );
  }
  
  private SourceWithFrontMatter sourceFrontMatter( String file ) {
    YamlLoader<SourceWithFrontMatter> loader = yamlService.newSourceLoader();
    return IoFunctions.forReader( openResource( file ), Encoding.UTF8, loader::load );
  }

  private ExampleSource openFrontMatterExampleSource( String file ) {
    YamlLoader<ExampleSource> loader = yamlService.newSourceLoader( ExampleSource::new, new ExampleSourceConfigurator() );
    return IoFunctions.forReader( openResource( file ), Encoding.UTF8, loader::load );
  }
  
  @Test
  public void yamlFile() {
    YamlLoader<ExamplePojo> loader = yamlService.newLoader( ExamplePojo.class );
    ExamplePojo             pojo   = IoFunctions.forReader( openResource( "yaml_file.txt" ), Encoding.UTF8, loader::load );
    assertNotNull( pojo );
    assertThat( pojo.getKey(), is("value") );
    assertNotNull( pojo.getList() ); 
    assertThat( pojo.getList().size(), is(3) );
    assertThat( pojo.getList().get(0).getValue(), is(13) );
    assertThat( pojo.getList().get(1).getValue(), is(19) );
    assertThat( pojo.getList().get(2).getValue(), is(34) );
    assertNotNull( pojo.getMap() ); 
    assertThat( pojo.getMap().size(), is(3) );
    assertThat( pojo.getMap().get(13).getValue(), is(13) );
    assertThat( pojo.getMap().get(19).getValue(), is(19) );
    assertThat( pojo.getMap().get(34).getValue(), is(34) );
  }

  @Test
  public void empty() {
    SourceWithFrontMatter data = sourceFrontMatter( "empty.txt" );
    assertNotNull( data );
    assertNotNull( data.getData() );
    assertTrue( data.getData().isEmpty() );
    assertNotNull( data.getContent() );
    assertTrue( data.getContent().isEmpty() );
  }

  @Test
  public void frontmatterWithoutContent() {
    SourceWithFrontMatter data = sourceFrontMatter( "frontmatter_without_content.txt" );
    assertNotNull( data );
    assertNotNull( data.getData() );
    assertTrue( data.getData().isEmpty() );
    assertNotNull( data.getContent() );
    assertTrue( data.getContent().isEmpty() );
  }

  @Test
  public void frontmatter() {
    SourceWithFrontMatter data = sourceFrontMatter( "frontmatter.txt" );
    assertNotNull( data );
    assertNotNull( data.getData() );
    assertThat( data.getData().size(), is(1) );
    assertThat( data.getData().get("key"), is("value") );
    assertNotNull( data.getContent() );
    assertTrue( data.getContent().isEmpty() );
  }

  @Test
  public void frontmatterWithText() {
    SourceWithFrontMatter data = sourceFrontMatter( "frontmatter_with_text.txt" );
    assertNotNull( data );
    assertNotNull( data.getData() );
    assertThat( data.getData().size(), is(1) );
    assertThat( data.getData().get("key"), is("value") );
    assertNotNull( data.getContent() );
    assertThat( data.getContent(), is("This is my text.\n   Hello World.\n   ") );
  }

  @Test
  public void complexFrontmatterWithText() {
    SourceWithFrontMatter data = sourceFrontMatter( "complex_frontmatter_with_text.txt" );
    assertNotNull( data );
    assertNotNull( data.getData() );
    assertThat( data.getData().size(), is(2) );
    assertThat( data.getData().get("key"), is("value") );
    Object obj = data.getData().get("list");
    assertNotNull( obj );
    assertTrue( obj instanceof List );
    List list = (List) obj;
    assertThat( list.size(), is(3) );
    assertThat( list.get(0), is("item 1") );
    assertThat( list.get(1), is("item 2") );
    assertThat( list.get(2), is("item 3") );
    assertNotNull( data.getContent() );
    assertThat( data.getContent(), is("This is my text.\n   Hello World.\n   ") );
  }

  @Test
  public void complexFrontmatterWithTextAndExtendedSource() {
    ExampleSource data = openFrontMatterExampleSource( "complex_frontmatter_with_text.txt" );
    assertNotNull( data );
    assertNotNull( data.getData() );
    assertThat( data.getData().size(), is(1) );
    assertThat( data.getData().get("key"), is("value") );
    assertNotNull( data.getItems() );
    assertThat( data.getItems().size(), is(3) );
    assertNotNull( data.getContent() );
    assertThat( data.getContent(), is("This is my text.\n   Hello World.\n   ") );
  }

} /* ENDCLASS */
