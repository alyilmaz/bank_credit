# 🏦 Bank Credit Application API

This is a Spring Boot Application for managing bank credit applications. The system allows customers to apply for loans, and the bank to manage and track these applications and installments.

---

## 🚀 Getting Started

### 📦 Requirements

- Java 21+
- Gradle 7+
- H2 database

---

## ⚙️ Build and Run

### 🛠️ Build the Project

```bash
./gradlew clean build 
```

### 🧪 Run Tests
```bash
./gradlew test
```
### 🧾 Test reports will be generated at:
```
build/reports/tests/test/index.html
```

### ▶️ Run the Application
```bash
./gradlew bootRun
```

### 🔍 Access Swagger UI
After running the application, to access the swagger-ui please click the below link:
```
http://localhost:8080/swagger-ui/index.html
```

## Endpoints

### User-Controller 

- /user/register  (POST)

Request Body schemas:
```
{
  "username": "string",
  "password": "string"
}
```

- /user/hello  (GET)

This end point is used for checking the authentication 

### Loan-Controller 
- /loan/pay (POST)

This end-point is used for payment of installment. Request Body schema:
```
{
  "loanId": 0,
  "amount": 1
}
```

- /loan/create (POST)

This end-point is used for creating a loan. The request body schema:
````
{
  "customerId": 0,
  "amount": 1,
  "interestRate": 0.5,
  "numberOfInstallments": 0
}
````


- /loan/{customerId} (GET)

This end-point is used for get the loan by customerId via Path variable. 


### Installment-Controller 
- loaninstallment/{loanId} (GET)

This end-point is used for get installment by loanId via Path variable

## Database
In this application, H2 mechanism is used and some data are inserted into the tables with using data.sql file from resources folder.

User table;

| Username  | Password | Role       |
|-----------|----------| ---------- |
| `admin`   | `admin`  | `ADMIN`    |
| `ayilmaz` | `admin`  | `CUSTOMER` |
| `cyilmaz` | `admin`  | `CUSTOMER` |

Customer table;

| ID | Name  | Surname | Credit Limit | Used Credit Limit |
| -- | ----- | ------- | ------------ | ----------------- |
| 1  | Admin | Admin   | 10000.0      | 0.0               |
| 2  | Ali   | YILMAZ  | 1000.0       | 0.0               |
| 3  | Can   | YILMAZ  | 1000.0       | 0.0               |


Loan table;

| ID | Create Date | Is Paid | Loan Amount | Installments | Customer ID |
| -- | ----------- | ------- | ----------- | ------------ | ----------- |
| 1  | 2025-03-11  | false   | 50.0        | 6            | 2           |

Loan installment table;

| ID | Amount | Due Date   | Is Paid | Paid Amount | Payment Date | Loan ID |
| -- | ------ | ---------- | ------- | ----------- | ------------ | ------- |
| 1  | 12.5   | 2025-03-01 | true    | 12.5        | 2025-03-01   | 1       |
| 2  | 12.5   | 2025-04-01 | false   | –           | –            | 1       |
| 3  | 12.5   | 2025-05-01 | false   | –           | –            | 1       |
| 4  | 12.5   | 2025-06-01 | false   | –           | –            | 1       |
| 5  | 12.5   | 2025-07-01 | false   | –           | –            | 1       |
| 6  | 12.5   | 2025-08-01 | false   | –           | –            | 1       |
