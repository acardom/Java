# My Hibernate Project

This project demonstrates the use of Hibernate for managing a simple database application with a `Grado` entity.

## Project Structure

```
my-hibernate-project
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── MainApp.java
│   │   │           └── model
│   │   │               └── Grado.java
│   │   └── resources
│   │       └── hibernate.cfg.xml
│   └── test
│       └── java
├── pom.xml
└── README.md
```

## Setup Instructions

1. **Clone the repository**:
   ```
   git clone <repository-url>
   cd my-hibernate-project
   ```

2. **Build the project**:
   Use Maven to build the project:
   ```
   mvn clean install
   ```

3. **Configure the database**:
   Update the `hibernate.cfg.xml` file in the `src/main/resources` directory with your database connection settings.

4. **Run the application**:
   Execute the `MainApp` class to perform CRUD operations on the `Grado` entity:
   ```
   mvn exec:java -Dexec.mainClass="com.example.MainApp"
   ```

## Usage

- The application will insert a new `Grado` instance into the database.
- It will then retrieve and display all `Grado` instances using HQL and native SQL queries.
- You can modify the `Grado` entity and the database connection settings as needed.

## Dependencies

This project uses the following dependencies:
- Hibernate
- SLF4J for logging
- Maven for project management

Ensure that you have the necessary database driver in your `pom.xml` file.