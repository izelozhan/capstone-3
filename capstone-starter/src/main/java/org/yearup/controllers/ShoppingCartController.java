package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;
import java.util.Map;

// convert this class to a REST controller
// only logged in users should have access to these actions

@RestController
@CrossOrigin
@PreAuthorize("permitAll()")
@RequestMapping("/cart")

public class ShoppingCartController {
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }


    // each method in this controller requires a Principal object as a parameter
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")


    public ShoppingCart getCart(Principal principal) {
        try {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return shoppingCartDao.getByUserId(userId);
            // use the shoppingcartDao to get all items in the cart and return the cart
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("products/{productId}")
    @PreAuthorize("hasRole('ROLE_USER')")

    public ShoppingCart addToShoppingCart(Principal principal, @PathVariable int productId) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            return shoppingCartDao.addToShoppingCart(userId, productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");

        }
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    @PutMapping("products/{productId}")
    @PreAuthorize("hasRole('ROLE_USER')")

    public ShoppingCart updateShoppingCart(Principal principal, @PathVariable int productId, @RequestBody ShoppingCartItem shoppingCartItem) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int quantity = shoppingCartItem.getQuantity();
            int userId = user.getId();

            boolean exists = shoppingCartDao.itemExistsInCart(userId, productId);

            if (!exists) {
                throw new IllegalStateException("Product not found in user's shopping cart.");
            }

            return shoppingCartDao.updateShoppingCart(userId, productId, quantity);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")

    public ShoppingCart deleteShoppingCart(Principal principal) {
        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();

        return shoppingCartDao.deleteShoppingCart(userId);
    }


}
