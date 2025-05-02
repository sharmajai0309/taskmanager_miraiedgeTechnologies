High-Level Design (HLD)
The High-Level Design (HLD) provides an overview of the system's architecture, components, and interactions between them.

1. System Architecture
Client:

Front-end: React.js or any web/mobile application that consumes the REST APIs.

Authentication: JWT is used for authentication.

Backend:

Framework: Spring Boot

Database: MongoDB (for data storage)

API Layer: RESTful APIs

Authentication: JWT Authentication for securing endpoints

API Documentation:

Swagger/OpenAPI: Provides a UI to explore and test the API endpoints.

2. Components
Task Management API: Manages all operations related to tasks such as creating, updating, deleting, and retrieving tasks.

Task CRUD Operations: API endpoints for task creation, reading, updating, and deletion.

Task Summary: Provides a summary of tasks by status and overdue tasks.

Status Tracking: Track the current status of tasks (To Do, In Progress, Completed).

User Authentication & Authorization:

JWT-based authentication for users.

Login API to generate a token and validate requests.

Database:

MongoDB is used to store tasks and users.

Task documents store fields like title, description, status, due date, and assigned user.

Swagger UI:

Provides interactive API documentation to test API endpoints.

Security Layer:

JWT Authentication

Role-based access control (RBAC) could be added for more secure endpoints.

3. Data Flow
User Authentication:

The user sends login credentials to the /auth/login API endpoint.

The server validates the credentials and returns a JWT token.

Task Operations:

After authenticating, the user can perform task operations (create, update, delete, or fetch tasks) via REST APIs.

Task creation is done via the POST /tasks endpoint, and data is stored in MongoDB.

Task Summary:

The user can retrieve a summary of tasks using the GET /tasks/summary endpoint, which gives the count of tasks per status and overdue tasks.

API Testing:

Swagger is used for API testing and documentation.

                           +--------------------+
                           |     User Client    |
                           |  (Web/Mobile App)  |
                           +---------+----------+
                                     |
                                     |
                           +---------v----------+
                           |    REST APIs       |   <-- Task Management APIs, Auth APIs, etc.
                           +--------------------+
                           | + /tasks           | 
                           | + /auth/login      |
                           | + /tasks/summary   |
                           +---------+----------+
                                     |
                                     |
                           +---------v----------+
                           |   Spring Boot      |   <-- Application Logic
                           |    Backend         |
                           +--------------------+
                                     |
                                     |
                           +---------v----------+
                           |    MongoDB         |   <-- Task and User Data Storage
                           |  (Database)        |
                           +--------------------+
                                     |
                                     |
                           +---------v----------+
                           |  Swagger UI        |   <-- API Documentation
                           +--------------------+
