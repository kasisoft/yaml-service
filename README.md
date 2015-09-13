GIT
---

* ssh://git@kasisoft.com:7999/cdi/yaml-service.git


COORDINATES
-----------

    <dependency>
        <groupId>com.kasisoft.cdi</groupId>
        <artifactId>yaml-service</artifactId>
        <version>0.1-SNAPSHOT</version>
    </dependency>

USAGE
-----

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

