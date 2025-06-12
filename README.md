# 🏠 Online Listings Backend

This is a Java Spring Boot application that serves as a backend for managing online ads (listings), using PostgreSQL as the database.

---

## 💻 Technologies

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- PostgreSQL
- Hibernate
- Maven

---

## ⬇️ Project Cloning

```bash
git clone https://github.com/davidstakic/online-listings-backend.git
cd online-listings-backend
```

---

## 📥 Importing in IntelliJ IDEA
 - File → New → Project from Existing Sources
 - Choose directory online-listings-backend that you've just cloned.
 - Choose Maven as project type.
 - Wait for IntelliJ to finish loading all dependencies.

---

## 📥 Importing in Eclipse IDE
 - File → Import → Existing Maven Projects → Next
 - Choose directory online-listings-backend that you've just cloned.
 - Click finish and wait for Eclipse to load all dependencies.

---

## 🛠️ Setting up the PostgreSQL database
 - Start the PostgreSQL server (e.g. with pgAdmin or command).
 - Create a database in PostgreSQL with the following SQL query:
```sql
CREATE DATABASE online_listings;
```
 - Check and, if necessary, change the username and password in the file:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/online_listings
spring.datasource.username=postgres
spring.datasource.password=david
```
Note: Spring.jpa.hibernate.ddl-auto=create-drop is set, so the database will be reset on every boot.

---

## ▶️ Launching the application
### Option 1: IntelliJ and Eclipse
 - Open OnlineListingsApplication.java and click Run button.
### Option 2: From the terminal using Maven
 - If you are using system Maven:
```bash
mvn spring-boot:run
```
 - Or using a wrapper:
```bash
./mvnw spring-boot:run
```
