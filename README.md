# File Management API

## Project Description

The File Management API is a RESTful web service that allows users to upload text files, process them, and retrieve processed files. This API is built using Spring Boot and provides endpoints for file upload and retrieval. The uploaded files are stored in a database and processed based on the implemented business logic.

## Features

- **Upload Files**: Users can upload text files which are stored and processed by the application.
- **Retrieve Files**: Users can retrieve processed files using their unique IDs.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- H2 Database (for testing)
- Swagger/OpenAPI for API documentation
- JUnit and Mockito for testing

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven

### Clone the Repository

```sh
git clone https://github.com/your-username/file-management-api.git
cd file-management-api

Build the Project - mvn clean install
Run the Application - mvn spring-boot:run

The application will start and run on http://localhost:8080.

API Documentation
The API is documented using Swagger. Once the application is running, you can access the API documentation at: http://localhost:8080/swagger-ui.html

Usage
Uploading a File
To upload a file, send a POST request to /api/files/upload with the file in the request body as multipart/form-data.

Retrieving a Processed File
To retrieve a processed file, send a GET request to /api/files/{id}, where {id} is the ID of the file.
