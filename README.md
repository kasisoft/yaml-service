# DISCONTINUED
# DISCONTINUED
# DISCONTINUED

# Purpose


A simple service allowing to use Yaml.


# Infos

* [eMail: daniel.kasmeroglu@kasisoft.net](mailto:daniel.kasmeroglu@kasisoft.net)
* [GIT](https://kasisoft.com/bitbucket/projects/GRAV/repos/yaml-service)


# Development Setup

I assume that you're familiar with Maven. If not I suggest to visit the following page:

* https://maven.apache.org/


## Requirements

* Java 8


## Maven

### Releases

     <dependency>
         <groupId>com.kasisoft.cdi</groupId>
        <artifactId>yaml-service</artifactId>
        <version>0.8</version>
     </dependency>


### Snapshots

Snapshots can be used while accessing a dedicated maven repository. Your POM needs the following settings:

     <dependency>
       <groupId>com.kasisoft.cdi</groupId>
       <artifactId>yaml-service</artifactId>
       <version>0.9-SNAPSHOT</version>
     </dependency>
     
     <repositories>
         <repository>
             <id>libs-kasisoft</id>
             <url>https://kasisoft.com/artifactory/libs-kasisoft</url>
             <releases>
                 <enabled>true</enabled>
             </releases>
             <snapshots>
                 <enabled>true</enabled>
             </snapshots>
         </repository>
     </repositories>


# USAGE

Either bei injection or instantiation.

    private YamlService   instantiated = new YamlService();
    
    @Inject
    private YamlService   injected;


Loading content with front matter data:

    # this function allows to configure the instance in question using the yaml data
    BiConsumer<SourceWithFrontMatter,Object>  configurator = ...;
    Reader                                    reader       = ...;
    YamlLoader<SourceWithFrontMatter>         loader       = yamlService.newSourceLoader( SourceWithFrontMatter::new, configurator );
    SourceWithFrontMatter                     source       = loader.load( reader );


Loading an object directly:


    # provide a loader for an ordinary pojo
    Reader             reader = ...;
    YamlLoader<MyPojo> loader = yamlService.newLoader( MyPojo.class );
    MyPojo             pojo   = loader.load( reader );


  
# License

MIT License

Copyright (c) 2017 Daniel Kasmeroglu (Kasisoft)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
