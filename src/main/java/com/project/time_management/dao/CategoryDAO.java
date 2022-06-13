package com.project.time_management.dao;

import com.project.time_management.entity.Category;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class CategoryDAO extends AbstractDAO<Category> {

    private static final Logger LOG = Logger.getLogger(CategoryDAO.class);

    private int noOfRecords;
    private static final String SQL_SELECT_ALL = "SELECT category_id, name FROM categories ORDER BY category_id";
    private static final String SQL_SELECT_BY_ID = "SELECT category_id, name FROM categories" +
            " WHERE category_id=? ORDER BY category_id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM categories WHERE category_id=?";
    private static final String SQL_DELETE = "DELETE FROM categories WHERE id in(%s)";
    private static final String SQL_CREATE = "INSERT INTO categories(name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE categories SET name=? WHERE category_id=?";

    public CategoryDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Category> findAll() throws DBException {

        LOG.debug("findAll category method starts");

        List<Category> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("name"));
                resultList.add(category);
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while finding all activity categories", e);
        }
        LOG.trace("return list : " + resultList);
        LOG.debug("findAll category method terminates");
        return resultList;
    }

    @Override
    public Optional<Category> findEntityById(int id) throws DBException {

        LOG.debug("findEntityById category method starts");

        if (id <= 0) {
            LOG.trace("findEntityById : " + Optional.empty());
            return Optional.empty();
        }

        Category category = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                category = Category.createCategory(resultSet.getString("name"));
                category.setCategoryId(resultSet.getInt("category_id"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while getting user", e);
        }
        LOG.trace("return category : " + Optional.ofNullable(category));
        LOG.debug("findEntityById category method terminates");
        return Optional.ofNullable(category);
    }

    @Override
    public boolean delete(int id) throws DBException {

        LOG.debug("delete category by id method starts");

        if (id <= 0) {
            LOG.trace("invalid id, delete category : " + false);
            return false;
        }

        boolean rowsUpdate;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while deleting user", e);
        }
        LOG.trace("delete delete category : " + rowsUpdate);
        LOG.debug("delete category by id method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean delete(Category... categories) throws DBException {
        LOG.debug("delete list of categories method starts");
        if (categories == null || categories.length == 0) {
            LOG.trace("invalid input, delete categories : " + false);
            return false;
        }

        boolean rowsUpdate;
        StringJoiner sj = new StringJoiner(",");

        for ( Category category : categories) {
            sj.add(String.valueOf(category.getCategoryId()));
        }
        String sql = String.format(SQL_DELETE, sj);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while deleting categories", e);
        }
        LOG.trace("return delete categories : " + rowsUpdate);
        LOG.debug("delete list of categories method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean create(Category category) throws DBException {

        LOG.debug("create category method starts");
        if (category == null) {
            LOG.trace("invalid input, create category : " + false);
            return false;
        }
        boolean rowsUpdate;

        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, category.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while ( rs.next() ) {
                category.setCategoryId(rs.getInt(1));
            }
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while creating user", e);
        }
        LOG.trace("return create category : " + rowsUpdate);
        LOG.debug("create category method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean update(Category category) throws DBException {

        LOG.debug("update category method starts");

        if (category == null) {
            LOG.trace("invalid input, create category : " + false);
            return false;
        }

        boolean rowsUpdate;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getCategoryId());
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while updating category", e);
        }
        LOG.trace("return update category : " + rowsUpdate);
        LOG.debug("update category method terminates");
        return rowsUpdate;
    }

    public List<Category> findAllWithLimits(int offset, int noOfRecords)
            throws DBException {

        LOG.debug("findAllWithLimits categories method starts");

        String SQL_SELECT_ALL_LIMIT = "SELECT SQL_CALC_FOUND_ROWS * FROM categories LIMIT "
                + offset + ", " + noOfRecords;
        List<Category> resultList = new ArrayList<>();
        Category category;

        try (Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(SQL_SELECT_ALL_LIMIT);
            while (resultSet.next()) {
                category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("name"));
                resultList.add(category);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new RuntimeException(e);
        }
        LOG.trace("findAllWithLimits category method returns list : " + resultList);
        LOG.debug("findAllWithLimits category method terminates");
        return resultList;
    }

    public int getNoOfRecords() throws DBException {
        return noOfRecords;
    }
}
