# BookFinder App Backend

A Java-based backend for the BookFinder application using Spring Boot, PostgreSQL (running locally), and a local email server.

---

## Table of Contents

- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Usage Steps](#usage-steps)
- [Running the Application](#running-the-application)
- [Testing](#testing)

---

## Features

- **User Registration:** Create a new account by providing first name, last name, email, and password.
- **Email Confirmation:** Unlock the account using a confirmation token sent via email.
- **Authentication:** Login using email and password to receive a JWT.
- **Favorite Books:** Add or remove favorite books for each user.
- **Security:** Passwords are encoded and JWT is used for secure access.
- **Local Services:** PostgreSQL and an email server run locally.
- **Login Restriction:** Users cannot log in before confirming their email.

---

## API Endpoints

### 1. User Registration

- **Endpoint:** `POST /api/v1/registration/register`
- **Description:** Registers a new user.
- **Note:** Users must provide a valid email address to receive the confirmation token.
- **Request Body:**

  ```json
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "yourPassword123"
  }
  ```

### 2. Email Confirmation

- **Endpoint:** `GET /api/v1/registration/confirm?token={{confirmationToken}}`
- **Description:** Confirms the user’s account using the token sent via email.
- **Note:** Users must confirm their email before they can log in.

### 3. User Login

- **Endpoint:** `GET /api/v1/login`
- **Description:** Authenticates a user and returns a JWT.
- **Note:** Users cannot log in before confirming their email.
- **Request Body:**

  ```json
  {
    "email": "john.doe@example.com",
    "password": "yourPassword123"
  }
  ```

- **Response:**

  ```json
  {
    "jwt": "your_jwt_token_here"
  }
  ```

### 4. Add Favorite Book

- **Endpoint:** `POST /api/v1/books/add`
- **Description:** Adds a book to the user's favorites.
- **Headers:**
  - `Authorization: Bearer your_jwt_token_here`
- **Request Body:**

  ```json
  {
    "id": "bookId123"
  }
  ```

### 5. Remove Favorite Book

- **Endpoint:** `DELETE /api/v1/books/remove`
- **Description:** Removes a book from the user's favorites.
- **Headers:**
  - `Authorization: Bearer your_jwt_token_here`
- **Request Body:**

  ```json
  {
    "id": "bookId123"
  }
  ```

### 6. Get Favorite Books

- **Endpoint:** `GET /api/v1/books/getBooks`
- **Description:** Retrieves the list of favorite books for the authenticated user.
- **Headers:**
  - `Authorization: Bearer your_jwt_token_here`

---

## Usage Steps

1. **Register a New User:**
   - Send a `POST` request to `/api/v1/registration/register` with the user's details.
   - Ensure the email provided is correct, as the confirmation token will be sent there.

2. **Confirm Email:**
   - Check your email for a confirmation token.
   - You will receve a mail with that sends a `GET` request to `/api/v1/registration/confirm?token={{confirmationToken}}` to activate your account.

3. **User Login:**
   - Send a `GET` request to `/api/v1/login` with your email and password.
   - Users cannot log in before confirming their email.
   - Receive a JWT token in the response.

4. **Add a Favorite Book:**
   - Send a `POST` request to `/api/v1/books/add` with the book's ID.
   - Include the JWT in the `Authorization` header as `Bearer your_jwt_token_here`.

5. **Remove a Favorite Book:**
   - Send a `DELETE` request to `/api/v1/books/remove` with the book's ID.
   - Include the JWT in the `Authorization` header.

6. **Get Favorite Books:**
   - Send a `GET` request to `/api/v1/books/getBooks` to retrieve your favorite books.
   - Include the JWT in the `Authorization` header.

---

## Running the Application

- Ensure you have Java 21 installed.
- The backend runs on `http://localhost:8080`.
- Run the application using Maven:

  ```bash
  mvn spring-boot:run
  ```

---

## Testing

- All APIs have been tested using Postman.
- Validate emails, ensure password encoding, and token-based account unlocking are functioning as expected.

---

Feel free to contribute or open an issue if you encounter any problems.

