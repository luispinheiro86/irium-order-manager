# Order management system

---

## 1. Database Configuration

1. Create a PostgreSQL database with the default name: irium
2. Update your `application.properties` file according to your local configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/irium
```

---

## 2. How to Run the Project

### Option 1: Command Line

```bash
mvn clean package
java -jar target/irium-order-manager-0.0.1-SNAPSHOT.jar
```

### Option 2: Using an IDE (IntelliJ, Eclipse, VSCode)

Run the main class:

```java
com.irium.order.manager.IriumOrderManagerApplication
```

---

## 3. Accessing Swagger (API Documentation)

After starting the application, open in your browser:

```
http://localhost:8085/swagger-ui.html
```

---

## 4. Request Examples

### Users

* **GET** all users:

```http
GET http://localhost:8085/api/users
```

* **POST** create a new user:

```http
POST http://localhost:8085/api/users
Content-Type: application/json

{
  "name": "Maria",
  "email": "maria@company.com"
}
```

---

### Items

* **GET** all items:

```http
GET http://localhost:8085/api/items
```

* **POST** create a new item:

```http
POST http://localhost:8085/api/items
Content-Type: application/json

{
  "name": "Keyboard"
}
```

---

### Orders

* **GET** all orders:

```http
GET http://localhost:8085/api/orders
```

* **POST** create a new order:

```http
POST http://localhost:8085/api/orders
Content-Type: application/json

{
  "userId": 1,
  "itemId": 2,
  "quantity": 10
}
```

---

### Stock Movements

* **POST** add stock:

```http
POST http://localhost:8085/api/stock-movements
Content-Type: application/json

{
  "itemId": 2,
  "quantity": 20
}
```


