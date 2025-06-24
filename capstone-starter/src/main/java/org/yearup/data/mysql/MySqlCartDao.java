package org.yearup.data.mysql;

import org.yearup.data.ShoppingCartDao;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart();
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                int productId = result.getInt("product_id");
                int quantity = result.getInt("quantity");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public ShoppingCart addToShoppingCart(int userId, int productId) {

        String sql = "INSERT INTO shopping_cart(user_id, product_id, quantity) VALUES (?,?,?)";

        return null;
    }

    @Override
    public ShoppingCart updateShoppingCart(int userId, int productId, int quantity) {
        return null;
    }

    @Override
    public ShoppingCart deleteShoppingCart(int userId) {
        return null;
    }

    @Override
    public boolean itemExistsInCart(int userId, int productId) {
        return false;
    }


}
