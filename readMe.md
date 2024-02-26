# QuizApp

QuizApp is a Spring Boot application designed to facilitate quiz creation, management, and participation. It provides functionalities to manage questions, quizzes, and allows users to take quizzes and receive results.

# Features

Question Management: Create, retrieve, update, and delete questions. Questions can be categorized and have options for multiple-choice answers.

Quiz Management: Create quizzes by selecting a category and specifying the number of questions desired. Quizzes are randomly generated based on the selected category and the specified number of questions.

Quiz Taking: Users can take quizzes and submit their responses. The system calculates the score based on the submitted responses and provides immediate feedback on the quiz result.

# Technologies Used

Spring Boot: Framework for creating standalone, production-grade Spring-based applications.

Maven: Dependency management and build automation tool.

PostgreSQL: Relational database management system used for storing application data.

Hibernate: ORM (Object-Relational Mapping) framework for mapping Java objects to database tables.

JUnit and Mockito: Libraries for unit testing and mocking dependencies in Java applications.

# Getting Started

## Prerequisites

JDK (Java Development Kit) 11 or higher
Apache Maven
PostgreSQL

## Installation

Clone the repository:

git clone https://github.com/mattpudlow/quizApp.git
Navigate to the project directory:

cd quizApp

## Build the project:

mvn clean package

## Run the application:

java -jar target/quizApp.jar

## Configuration

Database configuration: Modify the application.properties file to set up the database connection details.

Port configuration: By default, the application runs on port 8080. You can change it by modifying the server.port property in application.properties.

## Usage

Access the application through the following URL:

http://localhost:8080
Use the provided REST API endpoints to interact with the application:

/question: Endpoints for managing questions (GET, POST, PUT, DELETE).
/quiz: Endpoints for quiz management and participation (POST, GET).

## Testing

The application includes unit tests for service and controller classes. You can run the tests using the following command:

mvn test