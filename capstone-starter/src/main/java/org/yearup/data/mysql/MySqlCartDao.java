package org.yearup.data.mysql;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {
    MySqlProductDao productService;

    public MySqlCartDao(DataSource dataSource) {
        super(dataSource);
        this.productService = new MySqlProductDao(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart();
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                int productId = result.getInt("product_id");
                int quantity = result.getInt("quantity");

                ShoppingCartItem item = new ShoppingCartItem();
                item.setQuantity(quantity);
                item.setProduct(productService.getById(productId));

                cart.add(item);
                //TODO - IMPLEMENT GET WITH MULTIPLE ID'S
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;
    }

    @Override
    public ShoppingCart addToShoppingCart(int userId, int productId) {

        ShoppingCart cart = getByUserId(userId);

        if (!cart.contains(productId)) {
            String sql = "INSERT INTO shopping_cart(user_id, product_id, quantity) VALUES (?,?,?)";

            try (Connection connection = getConnection()) {
                ShoppingCartItem item = new ShoppingCartItem();
                item.setQuantity(1);
                item.setProduct(productService.getById(productId));

                PreparedStatement ps = connection.prepareStatement(sql);

                ps.setInt(1, userId);
                ps.setInt(2, productId);
                ps.setInt(3, item.getQuantity());

                ps.executeUpdate();
                cart.add(item);

                return cart;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            int newQuantity = cart.get(productId).getQuantity() + 1;
            return updateShoppingCart(userId, productId, newQuantity);
        }
    }

    @Override
    public ShoppingCart updateShoppingCart(int userId, int productId, int quantity) {

        ShoppingCart cart = getByUserId(userId);
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);

            ps.executeUpdate();

            cart.get(productId).setQuantity(quantity);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;
    }

    @Override
    public ShoppingCart deleteShoppingCart(int userId) {
        ShoppingCart cart = getByUserId(userId);

        if (cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean itemExistsInCart(int userId, int productId) {
        String sql = "SELECT COUNT(*) FROM shopping_cart WHERE user_id = ? AND product_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, productId);

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }


}
