package com.project.time_management.dao;

import com.project.time_management.entity.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserCredentialsDAO extends AbstractDAO<UserCredentials>{

    private static final Logger LOG = Logger.getLogger(UserCredentialsDAO.class);

    private static final String SQL_SELECT_BY_ID = "SELECT user_id, user_name, role " +
            "FROM users_credentials WHERE user_id=?";
    private static final String SQL_CREATE = "INSERT INTO users_credentials(user_id, user_login, user_password) " +
            "VALUES (?,?,?)";
    private static final String SQL_FIND_BY_NAME = "SELECT user_id FROM users_credentials WHERE user_login=?";
    public UserCredentialsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<UserCredentials> findAll() throws DBException {
        return null;
    }

    @Override
    public Optional<UserCredentials> findEntityById(int id) throws DBException {
        return Optional.empty();
    }


    @Override
    public boolean delete(int id) throws DBException {
        return false;
    }

    @Override
    public boolean delete(UserCredentials... entity) throws DBException {
        return false;
    }

    @Override
    public boolean create(UserCredentials userCredentials) throws DBException {
        LOG.debug("create user credentials method starts");
        if (userCredentials == null) {
            LOG.trace("invalid input, create user credentials : " + false);
            return false;
        }
        boolean rowsUpdate;
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userCredentials.getUserId());
            ps.setString(2, userCredentials.getLogin());
            ps.setString(3, userCredentials.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while ( rs.next() ) {
                userCredentials.setId(rs.getInt(1));
            }
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while adding user credentials to DB", e);
        }
        LOG.trace("return create user credentials : " + rowsUpdate);
        LOG.debug("create user credentials method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean update(UserCredentials entity) throws DBException {
        return false;
    }

    public User getUserByLoginPassword(final String login, final String password) throws DBException {
        LOG.debug("user credentials getUserByLoginPassword method starts");
        if (login == null || password == null
                || login.length() <= 0 || password.length() <= 0) {
            LOG.trace("invalid input in getUserByLoginPassword : " + false);
            return null;
        }

        User user = null;
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id " +
                "FROM users_credentials WHERE user_login=? AND user_password=?")) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                id = resultSet.getInt("user_id");
                break;
            }
        } catch (SQLException e) {
            LOG.error("Error while getting user id : " + e);
            throw new DBException("Error while getting user id", e);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * " +
                "FROM users WHERE user_id=?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                user = User.createUser(resultSet.getString("user_name"));
                user.setUserId(resultSet.getInt("user_id"));
                user.setRole(resultSet.getString("role"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while getting user by user credentials", e);
        }
        LOG.trace("return user credentials getUserByLoginPassword : " + user);
        LOG.debug("user credentials getUserByLoginPassword method terminates");
        return user;
    }

    public User.ROLE getRoleByLoginPassword(final String login, final String password) throws DBException {
        LOG.debug("user credentials getRoleByLoginPassword method starts");

        if (login == null || password == null
                || login.length() <= 0 || password.length() <= 0) {
            LOG.trace("invalid input in getRoleByLoginPassword : " + false);
            return User.ROLE.UNKNOWN;
        }

        User.ROLE result = User.ROLE.UNKNOWN;
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id " +
                "FROM users_credentials WHERE user_login=? AND user_password=?")) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                id = resultSet.getInt("user_id");
                break;
            }


        } catch (SQLException e) {
            LOG.error("Error while getting user id : " + e);
            throw new DBException("Error while getting user role", e);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT role " +
                "FROM users WHERE user_id=?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                String role = resultSet.getString("role");
                result = User.ROLE.valueOf(role.toUpperCase());
                break;
            }

        } catch (SQLException e) {
            LOG.error("Error while getting user role by id : " + e);
            throw new DBException("Error while getting user role by id", e);
        }
        LOG.trace("return user role getRoleByLoginPassword : " + result);
        LOG.debug("user credentials getRoleByLoginPassword method terminates");
        return result;
    }

    public boolean userExists(final String login, final String password) throws DBException {
        LOG.debug("user credentials userExists method starts");
        if (login == null || password == null
                || login.length() <= 0 || password.length() <= 0) {
            LOG.trace("invalid input in getRoleByLoginPassword : " + false);
            return false;
        }

        boolean result = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id " +
                "FROM users_credentials WHERE user_login=? AND user_password=?")) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                int id = resultSet.getInt("user_id");
                if (id > 0) result = true;
                break;
            }

        } catch (SQLException e) {
            LOG.error("Error while checking if user exists : " + e);
            throw new DBException("Error while checking if user exists", e);
        }
        LOG.trace("return user credentials userExists : " + result);
        LOG.debug("user credentials userExists method terminates");
        return result;
    }

    public boolean findEntityByName(String login) throws DBException {
        LOG.debug("user credentials findEntityByName method starts");

        if (login == null || login.isEmpty()) {
            LOG.trace("invalid input in findEntityByName : " + false);
            return false;
        }

        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_NAME)) {

            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                id = resultSet.getInt("user_id");
                break;
            }
            if (id != 0) {
                LOG.trace("return user credentials findEntityByName : " + true);
                return true;
            }

        } catch (SQLException e) {
            LOG.error("Username is not present in the DB : " + e);
            throw new DBException("Username is not present in the DB", e);
        }
        LOG.trace("return user credentials findEntityByName : " + false);
        LOG.debug("user credentials findEntityByName method terminates");
        return false;
    }
}
