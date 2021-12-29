# Contract-First API Development - Achieving The Promise Of Microservices, One Contract At A Time

View the slides:
* [PDF w/ Notes](Slide%20Deck/Contract-First%20API%20Development.pdf)
* [PowerPoint](Slide%20Deck/Contract-First%20API%20Development.pptx)
* [OpenDocument Format](Slide%20Deck/Contract-First%20API%20Development.odp)

## Demo Walkthrough

The demo uses a simple Todo app as an example. We start from creating an OpenAPI specification
and move through generating MOST of the code needed to implement the application using [Quarkus](https://quarkus.io) for the server and [Quasar+VueJS+Axios](https://quasar.dev) for the UI.

### Step 1: Create The API Specification
1. Start with an example JSON object which represents the response/request body for the Entity you plan to interact with via your API. For example:
   ```json
   {
     "title": "Example Todo",
     "description": "This is a Todo entity with a description",
     "created": "2020-02-14T09:00:00.000Z",
     "complete": false
   }
   ```
2. Open and log in to [Apicur.io](https://apicur.io)
   ![Step 1, Item 2](images/Step1_item2.png)
3. Click on "Try Live"
4. Sign in, or sign up using your GitHub or Google account
5. Click on "Create New API"
   ![Step 1, Item 5](images/Step1_Item5.png)
6. Fill in the "Name" and optionally the "Description". The "Type" should be "Open API 3.0.1". And choose "Blank API" before clicking "Create API"
   ![Step1_Item6](images/Step1_Item6.png)
7. Proceed to "Edit API"
   ![Step1_Item7](images/Step1_Item7.png)
8. Click on "Add a data type"
   ![Step1_Item8](images/Step1_Item8.png)
9. Fill in the fields and paste our example JSON into the example space. Select "REST Resource" and click "Save"
   ![Step1_Item9](images/Step1_Item9.png)
10. You will notice that not only has the data type been defined (determined from the example JSON), but the typical [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) operations have also been defined.
11. One caveat... If you look at the source for the definition of the Type you will see:
    ```yaml
    components:
    schemas:
        Todo:
            title: Root Type for Todo
            description: ''
            type: object
            properties:
                title:
                    type: string
                description:
                    type: string
                created:
                    format: date-time
                    type: string
                complete:
                    type: boolean
    ```
    a. Depending on the generator you use, that "title" field value of **Root Type for Todo** could be a problem. I generally recommend to make it MUCH simpler and just change it to `Todo`

### Step 2: Get Some Feedback

With JUST an initial API Specification, we can start using [tools](https://openapi.tools/#mock) to stand up a Mock Server and start getting feedback from your stakeholders, customers, users, and colleagues.

1. Download your API specification fom [Apicur.io](https://Apicur.io)
   1. Click on "Dashboard"
   2. Click on your "Todo" API
   3. Click on the ellipsis menu and select "Download" ![Download Spec](images/Step2_Item1_3.png)
2. Run a tool like [API Sprout](https://github.com/danielgtaylor/apisprout) to stand up a Mock instance of your API using the specification
   ```bash
   docker run -p 8000:8000 -v ${PWD}/openapi.yml:/api.yaml danielgtaylor/apisprout /api.yaml
   ```
3. Get feedback.... No coding needed!
   1. For our example, we can try out the REST API by requesting `http://localhost:8000/todos` or `http://localhost:8000/todos/1`
4. In our example, we learn from our stakeholders that we need a missing field added to the spec:
   1. `dueDate` - A UTC Timestamp indicating when this Todo is due
5. We also learn that we need to make some of the fields "required"
6. Make the required changes in the spec:
   ```yaml
   components:
    schemas:
        Todo:
            title: Todo
            description: ''
            type: object
                required:
                  - title
            properties:
                title:
                    type: string
                description:
                    type: string
                created:
                    format: date-time
                    type: string
                complete:
                    type: boolean
                dueDate:
                    type: string
                    format: date-time
            example:
                id: 2b0e7a10-4d49-11ea-ad70-830e1b13a0e2
                name: Example Todo
                description: This is a Todo entity with a description
                created: '2020-02-14T09:00:00.000Z'
                complete: false
                dueDate: '2020-02-20T09:00:00.000Z'
    ```
7. Repeat feedback loop until you have something which works for the consumers of your API
   1. So, we have gotten fast feedback on IF the API we are creating will actually provide the intended value to our stakeholders without inveting time and money to write code which we might have to throw away or rewrite. Only AFTER we have confirmed that what we have created will provide value should be start to implement code.

### Step 3: Generate Some Code

There are a number of ways to create code from an API specification. From [OpenAPI Generator](https://openapi-generator.tech) to [Apicur.io](https://apicur.io/) itself. For this demonstration, we will use OpenAPI Generator. Go ahead and clone this repository locally and copy the API Specification you created using Apicur.io into the root of this repository and name it `openapi.yml`.

```bash
git clone https://gitlab.com/contract-first-api-development/demo.git OpenAPI-Demo
cp ~/Downloads/Todo.yaml OpenAPI-Demo/openapi.yml
```

1. Install OpenAPI Generator
   ```bash
   npm install -g @openapitools/openapi-generator-cli
   ```
2. Generate A JAX-RS/Java Server Implementation
   ```bash
   openapi-generator generate -g jaxrs-resteasy -i openapi.yml -o todo-server
   ```
   1. A caveat, some generators are more complete or more flexible than others. This server generator creates a pretty complete JAX-RS server, but it DOES NOT add JPA or other ancillary requirements.
3. At this point, the project in the `todo-server` directory merely needs the `TodosApiServiceImpl.java` class to be completed. No writing annotations, no handling exceptions, nothing... Just implement that class and you have a working REST API. How detailed you are as you implement that class determines how capable your implementation is. You have all you need there to be able to handle authentication, authorization, CORS, XSRF, etc... You just need to fill in the business logic.
4. 'But we need \<insert other library here>'
   1. This is where you can customize the template for your generator.
   2. A customized version of the JAX-RS Resteasy template is already created in the `api-specifications/quarkus-resteasy-templates` directory
   3. There are also an [ignore](api-specifications/openapi-server-ignore) file and a [configuration](api-specifications/todo-server-generator-config.yml) file in the `api-specifications` directory
   4. Generate the server implementation using those templates:
      ```bash
      openapi-generator generate -g jaxrs-resteasy -i openapi.yml -c api-specifications/todo-server-generator-config.yml -t api-specifications/quarkus-resteasy-templates/ --ignore-file-override=api-specifications/openapi-server-ignore -o todo-server
      ```
5. Since the project is using [Quarkus+Panache](https://quarkus.io/guides/hibernate-orm-panache), we have a very simple path the implement `TodosApiServiceImpl`
   ```java
   package com.redhat.msa.todo.api.impl;

   import com.redhat.msa.todo.api.*;
   import com.redhat.msa.todo.models.*;
   import com.redhat.msa.todo.models.Todo;

   import java.util.List;
   import com.redhat.msa.todo.api.NotFoundException;

   import java.io.InputStream;

   import javax.enterprise.context.ApplicationScoped;
   import javax.transaction.Transactional;
   import javax.ws.rs.core.Response;
   import javax.ws.rs.core.SecurityContext;

   @ApplicationScoped
   public class TodosApiServiceImpl implements TodosApiService {

     @Transactional  // Any operation which makes changes to the database needs to be annotated with @Transactional
     public Response createTodo(Todo todo,SecurityContext securityContext) throws NotFoundException {
         todo.persistAndFlush();
         return Response.ok().entity(todo).build();
     }

     @Transactional  // Any operation which makes changes to the database needs to be annotated with @Transactional
     public Response deleteTodo(Long todoId,SecurityContext securityContext) throws NotFoundException {
         Todo.findById(todoId).delete()
         return Response.noContent().build();
     }

     public Response getTodo(Long todoId,SecurityContext securityContext) throws NotFoundException {
         return Response.ok().entity(Todo.findById(todoId)).build();
     }

     public Response gettodos(SecurityContext securityContext) throws NotFoundException {
         return Response.ok().entity(Todo.listAll()).build();
     }

     @Transactional  // Any operation which makes changes to the database needs to be annotated with @Transactional
     public Response updateTodo(Long todoId,Todo todo,SecurityContext securityContext) throws NotFoundException {
         Todo existing = Todo.findById(todoId);
         existing.setComplete(todo.isComplete());
         existing.setTitle(todo.getTitle());
         existing.setDescription(todo.getDescription());
         existing.setDueDate(todo.getDueDate());
         existing.persistAndFlush();
         return Response.ok().entity(existing).build();
     }
   }
   ```
   1. We have just implemented a REST API while only writing 17 lines of actual code!
6. Now, we just need to add some configuration for [Quarkus](https://quarkus.io/), but that has already been included in this project
   1. [application.properties](todo-server/src/main/resources/application.properties)
   2. Database Schema Migration Files in `todo-server/src/main/resources/db/migration/`
7. At this point, we can compile and run the project assuming that we have a running PostgreSQL database as shown below:
   ```bash
   docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=tododb -e POSTGRES_USER=tododb -e POSTGRES_DB=todo --name=tododb postgres:latest
   ## Wait a few seconds for the database to be available
   ./mvnw clean compile quarkus:dev
   ```

### Step 4: Generate A Client SDK

Generating most of the code needed to implement the server is great and gets us a long ways, but it doesn't stop there. We can use that same API specification to make it simple for others to consume our API

1. Create a configuration file for generating our typescript client SDK. There is an example [HERE](api-specifications/todo-ts-client-config.yml)
2. Generate the client SDK
   ```bash
   openapi-generator generate -g typescript-axios -i openapi.yml -c api-specifications/todo-ts-client-config.yml -o todo-client
   ```
3. We now have an implementation of our Client SDK using Typescript and Axios which the UI can now just add to the project. At that point, integrating with the backend has now become almost too easy.
   1. Example:
      ```javascript
      import { DefaultApi } from 'todoClientSDK';

      // The BASE URL of your API is the second param
      const client = new DefaultApi(null, 'http://localhost:8086/api/v1');

      client.getTodos()
            .then(res -> {
              // Handle a successful GET response
            })
            .catch(err -> {
              // Handler an unsuccessful GET response
            });
      ```
4. In this case, the generated client SDK is type-safe (using Typescript Types) and simple to call. No need to know anything but the base URL of the API server

### Step 5: Test Your API

Testing is an integral part of product development, so we MUST be sure that our API is doing what it is supposed to do. Fortunately, for us, we can generate a lot of the tests we need using our [OpenAPI Tooling](https://openapi.tools/#testing)

1. Change to the `todo-server` subdirectory.
2. There are a set of templates, configuration files, and an ignore file in the `api-specifications` directory which will allow us to generate most of what we need for testing with Rest Assured:
   ```bash
   openapi-generator generate -g java --library rest-assured -i ../openapi.yml -c ../api-specifications/todo-test-generator-config.yml --ignore-file-override ../api-specifications/openapi-test-ignore -t ../api-specifications/rest-assured-templates/ -o ./
   ```
3. We do need to fill in some test data after the code is generated so that the tests will actually run. For example:
   ```java
       /**
     * Successful response.
     */
    @Test
    public void shouldSee204AfterDeleteTodo() {
        Long todoId = new Long(1);     // Must fill in a value for the Long here
        api.deleteTodo()
                .todoIdPath(todoId).execute(r -> r.prettyPeek());
        // TODO: test validations
    }
    ```
4. Once you have set the appropriate data, you can run those tests. One thing I will emphasize is that these are just testing for a successful response code as they are now. If you want to truly validate your application, you will need to customize those tests/inputs/assertions/etc...


### Step 6: Put It All In A (Series Of) Pipeline(s)

All of the work and effort we have saved allows us to potentially skip over weeks or months of work in a real-world project. That said, it's painful to do this correctly using manual processes. Ideally, you will want to configure all of this to use CI/CD pipelines so that you remove the manual work of integration all of these tools and re-running them every time there is a change/update to your API specifications. The following steps are specific to [OpenShift](https://openshift.com) Kubernetes Distribution from [Red Hat](https://redhat.com) and [Jenkins](https://jenkins.io/).

1. Log on to your OpenShift cluster using the `oc` command-line tool
2. Ensure that you have [Ansible](https://www.ansible.com/) installed and that you are running on a *NIX system (Ansible does not work on Windows, but it might work in the Windows Subsystem For Linux (WSL))
3. Change into this `infrastructure-as-code` subdirectory
4. Use `ansible-galaxy` to install the OpenShift Applier Role
   ```bash
   ansible-galaxy install -r requirements.yml -p roles -f
   ```
5. Execute the Ansible playbook to build out the complete project in OpenShift, including CI/CD, artifact repository, deployments, services, routes, secrets, etc...
   ```bash
   ansible-playbook -i inventory/ site.yml
   ```
   1. **NOTE** This playbook REQUIRES that you have sufficient privileges to create and administer your own Namespaces/Projects in OpenShift
