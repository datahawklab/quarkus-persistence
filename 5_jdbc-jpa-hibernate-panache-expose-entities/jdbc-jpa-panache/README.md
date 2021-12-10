# artist Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/artist-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)


```bash
mvn -U io.quarkus:quarkus-maven-plugin:create \
        -DprojectGroupId=org.agoncal.course.quarkus.orm \
        -DprojectArtifactId=artist \
        -DpackageName="org.agoncal.quarkus.jdbc" \
        -Dextensions="jdbc-mysql, quarkus-agroal"
```


### adding jsonb resteasy extension

```bash
mvn quarkus:add-extension -Dextensions="resteasy-jsonb"
```

```bash
curl http://localhost:8080/api/publishers -v
curl http://localhost:8080/api/artists -v

```

### add open api and swagger ui

```bash
mvn quarkus:add-extension -Dextensions="quarkus-smallrye-openapi"
# add following to application.properties
#quarkus.http.host=0.0.0.0
mvn quarkus:dev
http://192.168.1.168:8080/q/swagger-ui
```

### return 200 for persist

```java
  @POST
  @Transactional
  public Artist persistArtist(Artist artist) {
    repository.persist(artist);
    return artist;
  }
```

### return 200 and path to persisted object

use the Context to get the path to persisted object and return it via the Response object
Response.created returns a 200

```java
  @POST
  @Transactional
  public Response persistArtist(Artist artist, @Context UriInfo uriInfo) {
    repository.persist(artist);
    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(artist.getId()));
    return Response.created(builder.build()).build();
  }
```


### add qute template 

### add open api and swagger ui

```bash
mvn quarkus:add-extension -Dextensions="resteasy-qute"
# add following to application.properties
#quarkus.http.host=0.0.0.0
mvn quarkus:dev
http://localhost:8080/page/artists
http://192.168.1.168:8080/page/publishers
http://192.168.1.168:8080/page/publishers/1
```