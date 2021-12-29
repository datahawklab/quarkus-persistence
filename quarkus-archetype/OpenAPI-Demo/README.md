### create the spec

place openapi.yml in the root directory with any spec


### generate the server from the openapi.yml

```bash
openapi-generator-cli generate -g jaxrs-resteasy -i openapi.yml -o todo-server
```

### generate the client from the openapi.yml spec

```bash
openapi-generator-cli generate -g typescript-axios -i openapi.yml -c api-specifications/todo-ts-client-config.yml -o todo-client
```

### generate all tests with openapi.yml spec

```bash
openapi-generator generate -g java --library rest-assured -i ../openapi.yml -c ../api-specifications/todo-test-generator-config.yml --ignore-file-override ../api-specifications/openapi-test-ignore -t ../api-specifications/rest-assured-templates/ -o ./
```

### generate using maven archetype

```bash
mvn archetype:generate -DarchetypeGroupId=com.redhat.consulting \
                       -DarchetypeArtifactId=openapi-quarkus-archetype \
                       -DarchetypeVersion=1.0.9 \
                       -Dpackage=com.redhat.runtimes \
                       -DgroupId=com.redhat.runtimes.quarkus \
                       -DartifactId=quarkus-petstore \
                       -Dversion=0.0.1-SNAPSHOT \
                       -Dopenapi_app_contract_uri=https://petstore.swagger.io/v2/swagger.yaml \
                       -Dinteractive=false \
                       -Dquarkus_orm_selection=hibernate-orm
```