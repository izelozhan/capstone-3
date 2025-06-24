package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);

    // add additional method signatures here
    ShoppingCart addToShoppingCart(int userId, int productId);

    ShoppingCart updateShoppingCart(int userId, int productId, int quantity);

    ShoppingCart deleteShoppingCart(int userId);

    boolean itemExistsInCart(int userId, int productId);
}
