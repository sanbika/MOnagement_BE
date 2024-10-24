# MOnagement
This is the backend codes for an item management web application.

## Backend Overview
The backend is built with Spring Boot, utilizing JPA for entity mapping to a MySQL database. It provides RESTful APIs to handle CRUD operations and integrates business logic for managing items, types, and subtypes, as well as processing user requests.


## Table of Contents

- [Completed Features](#completed-features)
- [Upcoming Features](#upcoming-features)
- [Project Structure](#project-structure)

## Completed Features

### 1. Item Management

- ✅ **CRUD Operations:** Add, view, edit, and delete items.
- ✅ **Categorization:** Organize items by types and subtypes.
- ✅ **Expiration & Stock Tracking:** Track item expiration dates and low stock levels.

### 2. User Interface

- ✅ **Interactive Tables:** Display and manage items, types, and subtypes.
- ✅ **Modals for Item Creation:** Easily add new items, types, and subtypes through pop-up forms.
- ✅ **Inplace Editing:** Allow editing of items, types, and subtypes directly within the table.
- ✅ **Batch Operations:** Select multiple items for batch actions, such as deletion.
- ✅ **Real-time Notifications:** Display status messages for actions like adding or deleting items.

### 3. API Integration

- ✅ **Axios for API Requests:** Seamless interaction with backend services for real-time data.

## Upcoming Features

- 🔜 **Login System:** Implement user authentication to secure access to the application.
- 🔜 **Scan to Add Item:** Enable users to scan barcodes or QR codes for quick item addition.
- 🔜 **Automated Testing and Deployment:** Implement CI/CD pipelines to automate testing and deployment processes.

## Project Structure

The project structure under the `src` directory is organized as follows:

```
src/
└── main/
    └──java/com/example/item_repo_spring/
        ├── controllers/ # Handles HTTP requests and responses
        ├── models/ # Defines entity classes representing data
        ├── repositories/ # Interfaces for database access (CRUD operations)
        ├── services/ # Contains business logic and service layer code
        ├── ItemRepoApplication.java # Main entry point of the Spring Boot application
        └── resources/
            └── application.properties # Application configuration file
```
