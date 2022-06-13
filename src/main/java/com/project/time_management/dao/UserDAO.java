package com.project.time_management.dao;

import com.project.time_management.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class UserDAO extends AbstractDAO<User>{

    private static final Logger LOG = Logger.getLogger(UserDAO.class);
    private int noOfRecords;
    private static final String SQL_SELECT_ALL = "SELECT user_id, user_name, role FROM users ORDER BY user_id";
    private static final String SQL_SELECT_BY_ID = "SELECT user_id, user_name, role FROM users " +
            "WHERE user_id=? ORDER BY user_id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE user_id=?";
    private static final String SQL_DELETE = "DELETE FROM users WHERE id in(%s)";
    private static final String SQL_CREATE = "INSERT INTO users(user_name, role) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE users SET user_name=?,role=? WHERE user_id=?";
    private static final String SQL_GET_ID = "SELECT user_id FROM users WHERE user_name=? AND role=?";
    private static final String SQL_FIND_BY_NAME = "SELECT user_id FROM users WHERE user_name=?";


    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() throws DBException {
        LOG.debug("findAll user method starts");
        List<User> resultList = new ArrayList<>();
        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                User user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("user_name"));
                user.setRole(resultSet.getString("role"));
                resultList.add(user);
            }

        } catch (SQLException e) {
            LOG.error("Error while finding all users : " + e);
            throw new DBException("Error while finding all users", e);
        }
        LOG.trace("return list : " + resultList);
        LOG.debug("findAll user method terminates");
        return resultList;
    }

    @Override
    public Optional<User> findEntityById(int id) throws DBException {
        LOG.debug("findEntityById user method starts");
        if (id <= 0) {
            LOG.trace("user findEntityById : " + Optional.empty());
            return Optional.empty();
        }
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                user = User.createUser(resultSet.getString("user_name"));
                user.setUserId(resultSet.getInt("user_id"));
                user.setRole(resultSet.getString("role"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("Error while getting user : " + e);
            throw new DBException("Error while getting user", e);
        }
        LOG.trace("return user : " + Optional.ofNullable(user));
        LOG.debug("findEntityById user method terminates");
        return Optional.ofNullable(user);
    }

    @Override
    public boolean delete(int id) throws DBException{
        LOG.debug("delete user by id method starts");
        if (id <= 0) {
            LOG.trace("invalid id, delete request : " + false);
            return false;
        }
        boolean rowsUpdate;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while deleting user by id : " + e);
            throw new DBException("Error while deleting user by id", e);
        }
        LOG.trace("delete delete user : " + rowsUpdate);
        LOG.debug("delete user by id method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean delete(User... users) throws DBException {

        LOG.debug("delete list of users method starts");

        if (users == null || users.length == 0) {
            LOG.trace("invalid input, delete users : " + false);
            return false;
        }

        boolean rowsUpdate;
        StringJoiner sj = new StringJoiner(",");

        for ( User user : users ) {
            sj.add(String.valueOf(user.getUserId()));
        }
        String sql = String.format(SQL_DELETE, sj);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while deleting users : " + e);
            throw new DBException("Error while deleting users", e);
        }
        LOG.trace("return delete users : " + rowsUpdate);
        LOG.debug("delete list of users method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean create(User user) throws DBException {

        LOG.debug("create user method starts");
        if (user == null) {
            LOG.trace("invalid input, create user : " + false);
            return false;
        }

        boolean rowsUpdate;
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getRole());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while ( rs.next() ) {
                user.setUserId(rs.getInt(1));
            }
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while creating user : " + e);
            throw new DBException("Error while creating user", e);
        }
        LOG.trace("return create user : " + rowsUpdate);
        LOG.debug("create user method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean update(User user) throws DBException {
        LOG.debug("update user method starts");
        if (user == null) {
            LOG.trace("invalid input, create user : " + false);
            return false;
        }
        boolean rowsUpdate;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getRole());
            preparedStatement.setInt(3, user.getUserId());
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while updating user : " + e);
            throw new DBException("Error while updating user", e);
        }
        LOG.trace("return update user : " + rowsUpdate);
        LOG.debug("update user method terminates");
        return rowsUpdate;
    }


    public List<User> findAllWithLimits(int offset, int noOfRecords)
            throws DBException {
        LOG.debug("findAllWithLimits user method starts");

        final String SQL_SELECT_ALL_LIMIT = "SELECT SQL_CALC_FOUND_ROWS * FROM users LIMIT "
                + offset + ", " + noOfRecords;
        List<User> resultList = new ArrayList<>();
        User user;

        try (Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL_LIMIT);
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                user.setRole(rs.getString("role"));
                resultList.add(user);
            }
            rs.close();
            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            LOG.error("Error while findAllWithLimits user : " + e);
            throw new DBException("Error while findAllWithLimits user", e);
        }
        LOG.trace("findAllWithLimits user method return" + resultList);
        LOG.debug("findAllWithLimits user method terminates");
        return resultList;
    }

    public int getNoOfRecords() throws DBException {
        return noOfRecords;
    }

    public int findId(User user) throws DBException {
        int id = 0;
        if (user == null) return id;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ID)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getRole());
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                id = resultSet.getInt("user_id");
                break;
            }
        } catch (SQLException e) {
            LOG.error("Error while getting user's id : " + e);
            throw new DBException("Error while getting user's id", e);
        }
        return id;
    }

    public boolean findEntityByName(String name) throws DBException {

        if (name == null || name.isEmpty()) return false;
        int id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_NAME)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                id = resultSet.getInt("user_id");
                break;
            }
            if (id != 0) {return true;}

        } catch (SQLException e) {
            LOG.error("Username is not present in the DB : " + e);
            throw new DBException("Username is not present in the DB", e);
        }
        return false;
    }
}
