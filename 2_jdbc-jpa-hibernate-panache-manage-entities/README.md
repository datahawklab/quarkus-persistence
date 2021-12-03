# jdbc

```bash
mvn clean install
```

# jdbc-jpa

```bash
mkdir -p jdbc-jpa/src/main/resources/META-INF
touch jdbc-jpa/src/main/resources/META-INF/panache-archive.marker
mvn clean install
```

# jdbc-jpa-panache

### in pom.xml add 

<dependency>
      <groupId>org.agoncal.course.quarkus.orm</groupId>
      <artifactId>jdbc</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <exclusions>
        <exclusion>
          <groupId>io.quarkus</groupId>
          <artifactId>quarkus-jdbc-mysql</artifactId>
        </exclusion>
      </exclusions>
</dependency>
<dependency>
      <groupId>org.agoncal.course.quarkus.orm</groupId>
      <artifactId>jdbc-jpa</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <exclusions>
        <exclusion>
          <groupId>io.quarkus</groupId>
          <artifactId>quarkus-jdbc-mariadb</artifactId>
        </exclusion>
      </exclusions>
</dependency>

### to map a regualar pojo to a panache entity add an orm.xml to jdbc-jpa-panache/src/main/resources/META-INF/orm.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<entity-mappings xmlns="http://java.sun.com/xml/ns/persistence/orm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_2_0.xsd"
                 version="2.0">

  <entity class="org.agoncal.quarkus.jdbc.Artist">
    <table name="t_artists"/>
    <attributes>
      <id name="id">
        <generated-value strategy="AUTO"/>
      </id>
      <basic name="name">
        <column length="100" nullable="false"/>
      </basic>
      <basic name="bio">
        <column length="3000"/>
      </basic>
      <basic name="createdDate">
        <column name="created_date" nullable="false"/>
      </basic>
    </attributes>
  </entity>
</entity-mappings>
```

### mark external jpa entity as panache entity

in external project that has jpa entity add panache-archive.marker

```bash
mkdir -p jdbc-jpa/src/main/resources/META-INF
touch jdbc-jpa/src/main/resources/META-INF/panache-archive.marker
mvn clean install
```

```properties
quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.hibernate-orm.scripts.generation=drop-and-create
quarkus.hibernate-orm.scripts.generation.create-target=create.sql
quarkus.hibernate-orm.scripts.generation.drop-target=drop.sql
```

### compile and test

```bash
export PROJECT_ROOT=/home/swapanc/github-repos/vintage-store
cd $PROJECT_ROOT/jdbc 
mvn clean install
cd $PROJECT_ROOT/jdbc-jpa
mvn clean install
cd $PROJECT_ROOT/jdbc-jpa-panache
mvn clean test
```

### compile and install without tests

```bash
mvn clean install -Dmaven.test.skip=true
```

### compile and test

```bash
mvn clean test
```

### make use of dev-services

dev services automtically runs testcontainers to spin up databases for unit tests. 

just add the following to pom.xml, other db's are available as well. 

```xml
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
```

make sure docker is up and running

then run:

```bash
mvn clean test
```