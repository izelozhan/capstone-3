# 🛒 EasyShop E-Commerce API

This is a Java Spring Boot REST API for an e-commerce application called **EasyShop**. The backend powers core functionalities like user authentication, product listings, shopping cart management, and order checkout.

---

## Features

### 💻 Core Functionality (Version 1)
-  User registration & login with JWT authentication
-  Browse products by category
-  Product search & filtering (category, color, price)
-  Shopping cart (add/update/delete items)
-  Checkout: Convert shopping cart into an order

### 🪲  Bug Fixes
- Fixed product search returning incorrect results
- Fixed product updates that were duplicating instead of modifying entries

### ➕ New Features (Version 2)
- CRUD for categories and products (admin-only)
- Persistent shopping cart for logged-in users
- Checkout process: create order & line items, clear cart
- Profile view/update feature for users

---

## 🧰 Tech Stack

- **Java 17**
- **Spring Boot**
- **MySQL**
- **JDBC & DAO pattern**
- **JWT for authentication**
- **Postman** for testing

---

## 🔄 API Overview

### Authentication
- `POST /register`
- `POST /login` → returns JWT

### Categories
- `GET /categories`
- `POST /categories` *(admin only)*

### Products
- `GET /products?cat=1&color=red`
- `POST /products` *(admin only)*

### Shopping Cart
- `GET /cart`
- `POST /cart/products/{productId}`
- `PUT /cart/products/{productId}` → `{ "quantity": 3 }`
- `DELETE /cart`

### Orders (Checkout)
- `POST /orders` → Converts cart to order and clears it

### Profile
- `GET /profile`
- `PUT /profile`

---

## 🧪 Postman Collection for API Testing

- A Postman collection is included in the project under:
- `📁 src/test/resources/easyshop-optional-solo.postman_collection.json`
- `📁 src/test/resources/easyshop-solo.postman_collection.json`

This collection covers all the key endpoints — login, product search, cart actions, and checkout — and can be used to quickly test the API functionality.


---

## 📸 Screenshots

![category.png](../../Desktop/category.png)

![filter.png](../../Desktop/filter.png)

![profile.png](../../Desktop/profile.png)

![cart.png](../../Desktop/cart.png)
---

