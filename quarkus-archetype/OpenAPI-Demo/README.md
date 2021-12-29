
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