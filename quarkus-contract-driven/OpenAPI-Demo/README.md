
### clone the openapi generator templates

```bash
git clone https://github.com/swapan-datahawklab/quarkus-persistence.git
cd quarkus-persistence/quarkus-contract-driven/OpenAPI-Demo/
```

### place open api spec in root dir as openapi.yml

```yaml
---
openapi: 3.0.2
info:
  title: todo
  version: 1.0.0
  description: Todo List
  contact:
    name: Swapan Chakrabarty
    url: https://datahawklab.com
    email: swapan.chakrabarty@gmail.netease_comment
  license:
    name: Apache 2.0
    url: https://www.apache.org/licenses/LICENSE-2.0
servers:
- url: http://localhost:8080/v1
  description: Localhost
paths:
  /todos:
    summary: Path used to manage the list of todos.
    description: The REST endpoint/path used to list and create zero or more `todo`
      entities.  This path contains a `GET` and `POST` operation to perform the list
      and create tasks, respectively.
    get:
      tags:
      - todo
      responses:
        "200":
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/todo'
          description: Successful response - returns an array of `todo` entities.
      operationId: getTodos
      summary: List All todos
      description: Gets a list of all `todo` entities.
    post:
      requestBody:
        description: A new `todo` to be created.
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/todo'
        required: true
      tags:
      - todo
      responses:
        "201":
          description: Successful response.
      operationId: createTodo
      summary: Create a todo
      description: Creates a new instance of a `todo`.
    parameters:
    - name: completed
      description: Show completed/uncompleted todo items
      schema:
        type: boolean
      in: query
  /todos/{todoId}:
    summary: Path used to manage a single todo.
    description: The REST endpoint/path used to get, update, and delete single instances
      of an `todo`.  This path contains `GET`, `PUT`, and `DELETE` operations used
      to perform the get, update, and delete tasks, respectively.
    get:
      tags:
      - todo
      responses:
        "200":
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/todo'
          description: Successful response - returns a single `todo`.
        "404":
          description: Todo item not found
      operationId: getTodo
      summary: Get a todo
      description: Gets the details of a single instance of a `todo`.
    put:
      requestBody:
        description: Updated `todo` information.
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/todo'
        required: true
      tags:
      - todo
      responses:
        "202":
          description: Successful response.
        "404":
          description: Todo item not found
      operationId: updateTodo
      summary: Update a todo
      description: Updates an existing `todo`.
    delete:
      tags:
      - todo
      responses:
        "204":
          description: Successful response.
        "404":
          description: Todo item not found
      operationId: deleteTodo
      summary: Delete a todo
      description: Deletes an existing `todo`.
    parameters:
    - name: todoId
      description: A unique identifier for a `todo`.
      schema:
        type: string
      in: path
      required: true
components:
  schemas:
    todo:
      title: Todo
      description: Todo List item
      type: object
      properties:
        name:
          type: string
        description:
          type: string
        date:
          format: date-time
          type: string
        completed:
          description: Is Completed
          type: boolean
      example:
        value:
          name: Todo item
          description: Description of item
          date: 2018-05-06T18:25:43.511Z
          completed: true
tags:
- name: todo
```

### generate the server from the openapi.yml spec above

```bash
openapi-generator-cli generate -g jaxrs-resteasy -i openapi.yml -o todo-server
```

### generate the client from the openapi.yml spec above

```bash
openapi-generator-cli generate -g typescript-axios -i openapi.yml -c api-specifications/todo-ts-client-config.yml -o todo-client
```

### generate all tests with openapi.yml spec abiov

```bash
openapi-generator generate -g java --library rest-assured -i ../openapi.yml -c ../api-specifications/todo-test-generator-config.yml --ignore-file-override ../api-specifications/openapi-test-ignore -t ../api-specifications/rest-assured-templates/ -o ./
```

### generate server, client and test using maven archetype

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