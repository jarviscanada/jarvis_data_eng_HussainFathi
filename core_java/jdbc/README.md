# JDBC APP

## Introduction
In this application JDBC API was employed to connect the Java program with the Relational 
Database Management System (RDBMS). This allows the user to perform Create, Read, Update and Delete
(CRUD) operations on the data. This application utilizes a Docker container to run the PostgreSQL 
database. IntelliJ was used to develop the application and Maven as the project management tool.

## Implementation
### ER Diagram
Multiple database tables were created and populated in order to test the functionality of the JDBC
application. The figure below illustrates the relationships between the tables:

![ERD](assets/ERD.png)

### Design Patterns
#### Data Access Object (DAO)
The Data Access Object (DAO) is a structural pattern that allows us to isolate the business logic and the underlying data source using an abstract API. The API hides all the complexity from the application associated with the execution of CRUD operation in the underlying storage mechanism. Therefore, the business logic does not interact directly with the data source, instead
it relays the information to the DAO layer which in turn communicates it with the data source. This
allows both the business layer and the underlying storage layer to evolve separately without knowing anything about each other.

In this project, two DAO classes are created `CustomerDAO` and `OrderDAO` to CRUD the Data Transfer
Objects (DTO) to the data source

#### Repository Pattern
Unlike DAO the Repository Pattern is a single table access per class. In addition, the joining takes place in the code, instead of the database. The repository pattern is considered to be ideal
when horizontal scaling is needed as it allows sharding of the database such that it focuses
on single table access instead of accessing the database as a whole.

## Test
To test the application, a PSQL Docker container was created and started. The database tables were created and populated by running SQL scripts. Then, the implemented methods were tested
by manually calling them in the `JDBCExecutor` class. Finally, the outputs of the `JDBCExecutor`
were compared with the expected results.

The image below is an example of a sample dataset that was obtained from PostgreSQL using
the DAO:

![JDBC_output](assets/JDBC_output.JPG)
