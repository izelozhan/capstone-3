package org.yearup.data.mysql;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    // Returns orderId
    @Override
    public int createOrder(Order order) {

        String sql = """
                    INSERT INTO orders (user_id, date, address, city, state, zip, shipping_amount)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setString(2, order.getDate().format(formatter));
            ps.setString(3, order.getAddress());
            ps.setString(4, order.getCity());
            ps.setString(5, order.getState());
            ps.setString(6, order.getZip());
            ps.setDouble(7, order.getShipping_amount());

            return ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addOrderLineItem(OrderLineItem orderLineItem) {
        String sql = "INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount) VALUES (?,?,?,?,?)";

        try(Connection connection = getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, orderLineItem.getOrderId());
            ps.setInt(2, orderLineItem.getProductId());
            ps.setBigDecimal(3, orderLineItem.getSalesPrice());
            ps.setInt(4, orderLineItem.getQuantity());
            ps.setBigDecimal(5, orderLineItem.getDiscount());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
