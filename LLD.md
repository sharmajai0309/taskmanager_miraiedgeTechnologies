Low-Level Design (LLD)
The Low-Level Design (LLD) dives deeper into individual components, database schema, API request/response structures, and interactions at a granular level.

1. API Layer
Task Management API
Create Task (POST /tasks)

Request:

{
  "title": "Task Title",
  "description": "Task Description",
  "dueDate": "2025-05-01",
  "assignedTo": "user1"
}
Response:


{
  "id": "task_id",
  "title": "Task Title",
  "status": "To Do",
  "assignedTo": "user1",
  "dueDate": "2025-05-01"
}
Get Tasks (GET /tasks)

Response:

[
  {
    "id": "task_id",
    "title": "Task Title",
    "status": "To Do",
    "assignedTo": "user1",
    "dueDate": "2025-05-01"
  },
  ...
]
Update Task (PUT /tasks/{id})

Request:


{
  "status": "In Progress",
  "assignedTo": "user2"
}
Response:


{
  "id": "task_id",
  "status": "In Progress",
  "assignedTo": "user2",
  "dueDate": "2025-05-01"
}
Delete Task (DELETE /tasks/{id})

Response:

{
  "message": "Task deleted successfully"
}
Task Summary (GET /tasks/summary)

Response:

{
  "statusCounts": {
    "To Do": 5,
    "In Progress": 3,
    "Completed": 10
  },
  "overdueCount": 2
}
Authentication API
Login (POST /auth/login)

Request:

{
  "username": "user1",
  "password": "password123"
}
Response:


{
  "token": "jwt-token-here"
}
2. Security Layer
JWT Authentication: Secure endpoints are protected by JWT. Tokens are passed in the Authorization header.

Example Header:


Authorization: Bearer <jwt-token>
Security Configuration: The SecurityConfig class configures HTTP security, enabling JWT authentication, and restricting access based on roles.

3. Database Design
Task Model (MongoDB Document)

{
  "_id": "task_id",
  "title": "Task Title",
  "description": "Task Description",
  "status": "To Do",  
  "assignedTo": "user1",
  "dueDate": "2025-05-01",
  "createdAt": "2025-04-01T00:00:00",
  "updatedAt": "2025-04-01T00:00:00"
}
User Model (MongoDB Document)
json
Copy
Edit
{
  "_id": "user1",
  "username": "user1",
  "password": "hashed-password",
  "role": "USER",  // Enum: USER, ADMIN
  "createdAt": "2025-01-01T00:00:00"
}
4. Swagger Configuration
To enable Swagger for API documentation, configure SwaggerConfig.java as follows:
