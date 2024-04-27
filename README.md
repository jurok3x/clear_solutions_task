# User App
This is a test task for Clear Solutions. Application can save user to database, update it fields, delete user, or retrieve users with specified date of birth. Additionally, unit and integration tests are implemented.
## Technologies Used:
- **`Spring Boot:`** Facilitates rapid development of production-ready applications.
- **`H2 Database:`** An in-memory database for storing user data during application runtime.
- **`Spring Data JPA:`** Simplifies the implementation of data access layers in Spring applications.
- **`MapStruct`** Streamlines the process of mapping between DTOs and entities.
- **`Spring Validation`** Ensures that incoming data meets specified criteria, enhancing the robustness of the application.
- **`JUnit and Mockito`** Utilized for writing unit and integration tests to validate the functionality of the application.
## Endpoints
1. **POST** *api/v1/users* : Creates a new user and saves to database. Field validation enabled.
2. **PUT** *api/v1/users/{id}* : Updates user entity if one exists. Field validation enabled.
3. **PATCH** *api/v1/users/{id}* : Partially updates user entity if one exists. Field validation enabled.
4. **DELETE** *api/v1/users/{id}* : Deletes  user entity if one exists.
5. **GET** *api/v1/users?from=date&to=date* : Retrieve list of users with date of birth is between specified range if  the range is correct.

## Entity: User
- **`id`** (UUID)
- **`firstName`** (String) mandatory field
- **`lastName`** (String) mandatory field
- **`email`** (String) mandatory field
- **`birthDate`** (LocalDate) mandatory field, user should be older than 18
- **`address`** (String)
- **`phone`** (String)