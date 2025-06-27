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
![category](https://github.com/user-attachments/assets/dbbd87e5-50f1-445c-9605-1c1862004f0b)
![filter](https://github.com/user-attachments/assets/cdadf331-c968-4396-aedb-0ad21ebd333c)
![profile](https://github.com/user-attachments/assets/2cecce98-7ea5-49ed-b087-28d74968120d)
![cart](https://github.com/user-attachments/assets/16211870-9347-4311-b59f-06ac7fe58f7a)

---

## 🥧Piece of Code 

```
  @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public void checkout(Principal principal) {

        //get user that is logged in
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();

        //use shopping card dao to get cart by user id
        ShoppingCart cart = shoppingCartDao.getByUserId(userId);

        //if cart is empty, throw error
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty.");
        }

        //get profile
        Profile profile = profileDao.getByUserId(userId);

        //create new order and set values using profileDao
        Order order = new Order();

        order.setUserId(userId);
        order.setDate(LocalDateTime.now());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
        order.setShipping_amount(10.0);

        int orderId = orderDao.createOrder(order);
        //int userId, LocalDateTime date, String address, String city, String state, String zip, Double shipping_amount

        //int orderId, int productId, Double sales_price, int quantity, Double discount
        for (ShoppingCartItem item : cart.getItems().values()) {
            OrderLineItem line = new OrderLineItem();

            line.setOrderId(orderId);
            line.setProductId(item.getProductId());
            line.setSalesPrice(item.getLineTotal());
            line.setQuantity(item.getQuantity());
            line.setDiscount(item.getDiscountPercent());

            orderDao.addOrderLineItem(line);
        }

        shoppingCartDao.clearCart(userId);
    }
```
- This method pulls together data from the user’s shopping cart and profile to complete the checkout in one step. It keeps things organized by letting the DAO and model classes handle the specific logic. The @PreAuthorize("hasRole('ROLE_USER')") annotation makes sure only logged-in users can place orders. It also uses LocalDateTime to timestamp the order and relies on SQL to generate the order ID when saving to the database.


