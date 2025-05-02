# taskmanager_miraiedgeTechnologies



A RESTful backend API built with Spring Boot and MongoDB that supports multi-user task management with JWT authentication, analytics, and task summaries.

---

## 🚀 Features

- User registration & login (JWT-based authentication)
- CRUD operations for tasks
- Task filtering by status/user
- Summary endpoint (task counts per status, overdue tasks)
- Swagger UI for API documentation

---

## 🛠️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/task-manager-api.git
cd task-manager-api



Application.properties
# MongoDB config
spring.data.mongodb.uri=mongodb://localhost:27017/taskmanager

# JWT secret
app.jwt.secret=your-secret-key

# Swagger UI
springdoc.swagger-ui.path=/docs


📚 API Documentation
Swagger UI: http://localhost:8080/docs



Register User
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{"name":"Alice", "email":"alice@example.com", "password":"123456"}'


Login and Get Token
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"alice@example.com", "password":"123456"}'


Create Task
curl -X POST http://localhost:8080/api/tasks \
-H "Authorization: Bearer <YOUR_JWT_TOKEN>" \
-H "Content-Type: application/json" \
-d '{"title":"Test Task", "description":"Description", "dueDate":"2025-05-10"}'


Get Task Summary
curl -X GET http://localhost:8080/tasks/summary \
-H "Authorization: Bearer <YOUR_JWT_TOKEN>"





✅ Design Choices
1. Choice of Frameworks and Tools
Spring Boot:

Chosen for its simplicity, flexibility, and widespread adoption in the Java ecosystem. Spring Boot allows quick setup and auto-configures many components, reducing boilerplate code.

Provides robust features like dependency injection, data binding, and security, making it ideal for building scalable and maintainable applications.

MongoDB:

Selected as the database for its schema-less nature, which is perfect for applications that may evolve over time or require flexibility in terms of data structure.

MongoDB’s document-oriented data model allows us to represent task and user information in a natural way and scale as needed.

Provides good performance for handling large volumes of unstructured data, making it ideal for a task management system with diverse task properties.

Spring Security with JWT Authentication:

JWT was chosen for authentication because it is stateless and easy to integrate with modern APIs. It doesn’t require session storage, ensuring scalability and ease of use across distributed systems.

It allows for secure communication between the client and server and is widely used in modern web applications.

Swagger (Springdoc):

Integrated to provide an easy-to-use interactive API documentation interface.

Swagger allows developers to quickly test and validate API endpoints in a user-friendly UI and ensures the documentation is always up-to-date.

Ensures that other developers or teams working with the API can quickly understand the expected requests and responses.

2. Task Model and Structure
Task Management:

Each task is associated with key attributes such as title, description, due date, status, and assigned user, reflecting the typical needs for a task management system.

Status: Tasks can have multiple statuses like "To Do," "In Progress," and "Completed," enabling users to track the progress of their tasks.

User Task Assignment:

Tasks are associated with specific users through a relationship, ensuring that each task is traceable to a user.

This provides a clear ownership structure and helps in filtering tasks by users.

Overdue Tasks:

An "overdue" task is detected based on the current date vs. the task’s due date.

This is handled at the API level, where the system checks for tasks that are past their due date and returns them as part of the summary.

3. Stateless Authentication
JWT (JSON Web Token) is used for authentication, which makes the application stateless. This means that the server does not have to store session information, and each request comes with its own authentication token.

This method is ideal for scaling in cloud-based systems where multiple instances of the application could be running.

JWT also ensures security through signature verification, ensuring that the token has not been tampered with during transmission.

4. API Design and Endpoints
RESTful API Principles:

All endpoints are designed to follow REST principles, ensuring that the application is easy to use and conforms to industry standards.

HTTP methods (GET, POST, PUT, DELETE) are used appropriately to perform CRUD operations.

Resource URLs are intuitive and follow a logical structure for ease of use. For instance:

GET /tasks — Retrieve all tasks.

POST /tasks — Create a new task.

GET /tasks/{id} — Retrieve a specific task by ID.

PUT /tasks/{id} — Update a task.

DELETE /tasks/{id} — Delete a task.

Summary Endpoint:

A special GET /tasks/summary endpoint aggregates data for easy access to key metrics such as task counts per status and overdue task counts. This endpoint simplifies querying complex analytics by bundling them into a single call.

5. Scalability Considerations
Horizontal Scaling:

MongoDB’s document-oriented approach allows for easy scaling of the database to handle large amounts of tasks. Data can be sharded across different nodes if necessary.

The stateless nature of JWT authentication ensures that the backend can be horizontally scaled by adding more instances without worrying about session management.

Asynchronous Processing:

If future requirements include handling time-consuming tasks (e.g., sending emails or generating reports), asynchronous processing could be introduced using a message broker like RabbitMQ or Kafka.

This ensures that the application remains responsive even under heavy loads.

6. Error Handling and Validations
Global Exception Handling:

Global exception handling is provided using @ControllerAdvice, ensuring that exceptions are caught and returned with appropriate HTTP status codes and error messages.

For instance, validation errors return a 400 Bad Request, and unauthorized access is handled with a 401 Unauthorized.

Input Validation:

Task input is validated using @Valid annotations to ensure that the data meets necessary criteria (e.g., due date cannot be in the past).

This prevents invalid data from entering the system and ensures that the API responds with appropriate error messages when the user sends invalid data.

7. Testing and Coverage
Unit Testing:

JUnit and Mockito are used for unit testing individual components such as services and controllers.

MockMvc is used to test API endpoints and validate response codes, body content, and headers.

Tests are written to ensure that the API is working correctly and that edge cases are handled properly.

Integration Testing:

Integration tests ensure that components of the application (controller, service, and repository) work together as expected, especially for tasks like user authentication and task creation.

Postman Collection:

A Postman collection is provided for manual testing, making it easier for developers and testers to run predefined API tests and check functionality.

