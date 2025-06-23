package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        // get all categories
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                Category category = mapRow(result);
                categoryList.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoryList;
    }

    @Override
    public Category getById(int categoryId) {
        // get category by id
        String sql = "SELECT * FROM categories " +
                " WHERE category_id = ? ";

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, categoryId);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                return mapRow(result);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }



    @Override
    public Category create(Category category) {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category) {
        // update category
    }

    @Override
    public void delete(int categoryId) {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category() {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
