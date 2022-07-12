package com.project.time_management.dao;

import com.project.time_management.entity.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class UsersActivitiesDAO extends AbstractDAO<UsersActivity> {

    private static final Logger LOG = Logger.getLogger(UsersActivitiesDAO.class);

    private int noOfRecords;

    private static final String USER_ID = "user_id";
    private static final String ACTIVITY_ID = "activity_id";
    private static final String USER_NAME = "user_name";
    private static final String SQL_SELECT_ALL = "SELECT * FROM users_activities ORDER BY id";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM users_activities WHERE id=? ORDER BY id";
    private static final String SQL_SELECT_BY_USER_ID_ACTIVITY_ID = "SELECT * FROM users_activities " +
            "WHERE user_id=? AND activity_id=? ORDER BY id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users_activities WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM users_activities WHERE id in(%s)";
    private static final String SQL_CREATE = "INSERT INTO users_activities(user_id, activity_id, time) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE users_activities SET user_id=?,activity_id=?,time=? WHERE id=?";
    private static final String SQL_SELECT_ACTIVITIES_BY_USER = "SELECT activity_id, COUNT(user_id)" +
                                                                "FROM users_activities " +
                                                                "GROUP BY activity_id " +
                                                                "ORDER BY COUNT(user_id) DESC";
    private static final String SQL_SELECT_ALL_RECORDS = "SELECT l.id, u.user_name, a.name, l.time " +
            "FROM users_activities l " +
            "         CROSS JOIN users u " +
            "                    ON l.user_id = u.user_id " +
            "         CROSS JOIN activities a " +
            "                    ON l.activity_id = a.activity_id " +
            "ORDER BY user_name";
    private static final String SQL_SELECT_ACTIVITIES_BY_USER_ID = "SELECT l.id, a.name, l.time " +
            "FROM users_activities l CROSS JOIN activities a " +
            "ON l.activity_id = a.activity_id WHERE user_id=? ORDER BY id";
    private static final String SQL_SELECT_BY_ACTIVITY_ID = "SELECT l.id, u.user_name, a.name, l.time " +
            "FROM users_activities l CROSS JOIN activities a " +
            "                                ON l.activity_id = a.activity_id " +
            "                        CROSS JOIN users u " +
            "                                ON l.user_id = u.user_id " +
            "WHERE l.id =? AND u.user_id=? ORDER BY l.id";
    private static final String SQL_UPDATE_FULL_RECORD = "UPDATE users_activities SET time=? WHERE id=?";

    public UsersActivitiesDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<UsersActivity> findAll() throws DBException {
        LOG.debug("findAll UsersActivity method starts");
        List<UsersActivity> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                UsersActivity activityRecord = new UsersActivity();
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setUser_id(resultSet.getInt(USER_ID));
                activityRecord.setActivity_id(resultSet.getInt(ACTIVITY_ID));
                activityRecord.setTime(resultSet.getInt("time"));
                resultList.add(activityRecord);
            }

        } catch (SQLException e) {
            LOG.error("Error while finding all records : " + e);
            throw new DBException("Error while finding all records", e);
        }
        LOG.trace("return list : " + resultList);
        LOG.debug("findAll UsersActivity method terminates");
        return resultList;
    }

    @Override
    public Optional<UsersActivity> findEntityById(int id) throws DBException {
        LOG.debug("findEntityById UsersActivity method starts");
        if (id <= 0) {
            LOG.trace("UsersActivity findEntityById : " + Optional.empty());
            return Optional.empty();
        }

        UsersActivity activityRecord = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                activityRecord = UsersActivity.createUserActivity(
                        resultSet.getInt(USER_ID),
                        resultSet.getInt(ACTIVITY_ID));
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setTime(resultSet.getInt("time"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("Error while getting ActivityRecord in findEntityById() : " + e);
            throw new DBException("Error while getting ActivityRecord in findEntityById()", e);
        }
        LOG.trace("return user-activity : " + Optional.ofNullable(activityRecord));
        LOG.debug("findEntityById user activity method terminates");
        return Optional.ofNullable(activityRecord);
    }

    @Override
    public boolean delete(int id) throws DBException {
        LOG.debug("delete user-activity by id method starts");
        if (id <= 0) {
            LOG.trace("invalid id, delete user-activity : " + false);
            return false;
        }
        boolean rowsUpdate;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while deleting record : " + e);
            throw new DBException("Error while deleting record", e);
        }
        LOG.trace("delete delete user-activity : " + rowsUpdate);
        LOG.debug("delete user-activity by id method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean delete(UsersActivity... usersActivities) throws DBException {

        LOG.debug("delete list of records method starts");
        if (usersActivities == null || usersActivities.length == 0) {
            LOG.trace("invalid input, delete records : " + false);
            return false;
        }

        boolean rowsUpdate;
        StringJoiner sj = new StringJoiner(",");

        for ( UsersActivity ActivityRecord : usersActivities ) {
            sj.add(String.valueOf(ActivityRecord.getId()));
        }
        String sql = String.format(SQL_DELETE, sj);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while deleting records : " + e);
            throw new DBException("Error while deleting records", e);
        }
        LOG.trace("return delete records : " + rowsUpdate);
        LOG.debug("delete list of records method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean create(UsersActivity usersActivity) throws DBException {

        LOG.debug("create records method starts");

        if (usersActivity == null) {
            LOG.trace("invalid input, create users-activity : " + false);
            return false;
        }

        boolean rowsUpdate;
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, usersActivity.getUser_id());
            ps.setInt(2, usersActivity.getActivity_id());
            ps.setInt(3, usersActivity.getTime());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while ( rs.next() ) {
                usersActivity.setId(rs.getInt(1));
            }
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while creating record : " + e);
            throw new DBException("Error while creating record", e);
        }
        LOG.trace("return create user-activity : " + rowsUpdate);
        LOG.debug("create user-activity method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean update(UsersActivity usersActivity) throws DBException {
        LOG.debug("update usersActivity method starts");
        if (usersActivity == null) {
            LOG.trace("invalid input, create usersActivity : " + false);
            return false;
        }

        boolean rowsUpdate;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setInt(1, usersActivity.getUser_id());
            preparedStatement.setInt(2, usersActivity.getActivity_id());
            preparedStatement.setInt(3, usersActivity.getTime());
            preparedStatement.setInt(4, usersActivity.getId());
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("Error while updating record : " + e);
            throw new DBException("Error while updating record", e);
        }
        LOG.trace("return update usersActivity : " + rowsUpdate);
        LOG.debug("update usersActivity method terminates");
        return rowsUpdate;
    }

    public List<UsersActivity> findAllUsersWithActivity() throws DBException {

        LOG.debug("findAllUsersWithActivity UsersActivity method starts");

        List<UsersActivity> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACTIVITIES_BY_USER)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                UsersActivity activityRecord = new UsersActivity();
                activityRecord.setActivity_id(resultSet.getInt(ACTIVITY_ID));
                resultList.add(activityRecord);
            }

        } catch (SQLException e) {
            LOG.error("Error while counting all users with activity : " + e);
            throw new DBException("Error while counting all users with activity", e);
        }
        LOG.trace("result list of findAllUsersWithActivity UsersActivity method : " + resultList);
        LOG.debug("findAllUsersWithActivity UsersActivity method terminates");
        return resultList;
    }

    public List<UsersActivityFull> findAllUsersWithActivityFull() throws DBException {
        List<UsersActivityFull> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_RECORDS)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                UsersActivityFull activityRecord = new UsersActivityFull();
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setUser_name(resultSet.getString(USER_NAME));
                activityRecord.setActivity_name(resultSet.getString("name"));
                activityRecord.setTime(resultSet.getInt("time"));
                resultList.add(activityRecord);
            }

        } catch (SQLException e) {
            LOG.error("Error while counting all users with activity full record: " + e);
            throw new DBException("Error while counting all users with activity full record: ", e);
        }
        return resultList;
    }

    public List<UsersActivityFull> findAllUserActivitiesByUserId(int id)
            throws DBException {
        LOG.debug("findAllUserActivitiesByUserId of UsersActivityFull method starts");

        if (id <= 0) {
            LOG.trace("invalid id, findAllUserActivitiesByUserId of UsersActivityFull : " + Collections.emptyList());
            return Collections.emptyList();
        }

        List<UsersActivityFull> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACTIVITIES_BY_USER_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                UsersActivityFull activityRecord = new UsersActivityFull();
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setActivity_name(resultSet.getString("name"));
                activityRecord.setTime(resultSet.getInt("time"));
                resultList.add(activityRecord);
            }

        } catch (SQLException e) {
            throw new DBException("Error while finding all records of user", e);
        }
        LOG.trace("findAllUserActivitiesByUserId of UsersActivityFull method result list : " + resultList);
        LOG.debug("findAllUserActivitiesByUserId of UsersActivityFull method terminates");
        return resultList;
    }

    public Optional<UsersActivityFull> findEntityByActivityId(int activity_id, int user_id)
            throws DBException {
        LOG.debug("findEntityByActivityId of UsersActivityFull method starts");
        if (activity_id <= 0 || user_id <= 0) {
            LOG.trace("invalid id or user_id in findEntityByActivityId of UsersActivityFull : " + Optional.empty());
            return Optional.empty();
        }

        UsersActivityFull activityRecord = null;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_SELECT_BY_ACTIVITY_ID)) {

            preparedStatement.setInt(1, activity_id);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                activityRecord = UsersActivityFull.createUserActivity(
                        resultSet.getString(USER_NAME),
                        resultSet.getString("name"));
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setTime(resultSet.getInt("time"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("Error while getting ActivityRecord in findEntityByActivityId() : " + e);
            throw new DBException("Error while getting ActivityRecord in findEntityByActivityId()", e);
        }
        LOG.trace("findEntityByActivityId of UsersActivityFull method result list : "
                + Optional.ofNullable(activityRecord));
        LOG.debug("findEntityByActivityId of UsersActivityFull method terminates");
        return Optional.ofNullable(activityRecord);
    }

    public boolean updateFullRecord(UsersActivityFull usersActivityFull) throws DBException {
        LOG.debug("updateFullRecord of UsersActivityFull method starts");
        if (usersActivityFull == null) {
            LOG.trace("invalid input in updateFullRecord of UsersActivityFull : " + false);
            return false;
        }

        boolean rowsUpdate;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_FULL_RECORD)) {
            preparedStatement.setInt(1, usersActivityFull.getTime());
            preparedStatement.setInt(2, usersActivityFull.getId());
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("error while updating full record: " + e);
            throw new DBException("Error while updating record", e);
        }
        LOG.trace("updateFullRecord of UsersActivityFull method results : " + rowsUpdate);
        LOG.debug("updateFullRecord of UsersActivityFull method terminates");
        return rowsUpdate;
    }

    public List<UsersActivityFull> findAllWithLimits(int offset, int noOfRecords)
            throws DBException {

        LOG.debug("findAllWithLimits of UsersActivityFull method starts");

        final String SQL_SELECT_ALL_LIMIT = "SELECT SQL_CALC_FOUND_ROWS l.id, u.user_name, a.name, l.time " +
                "FROM users_activities l " +
                "         CROSS JOIN users u " +
                "                    ON l.user_id = u.user_id " +
                "         CROSS JOIN activities a " +
                "                    ON l.activity_id = a.activity_id " +
                "ORDER BY user_name LIMIT " + offset + ", " + noOfRecords;

        List<UsersActivityFull> resultList = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(SQL_SELECT_ALL_LIMIT);

            while ( resultSet.next() ) {
                UsersActivityFull activityRecord = new UsersActivityFull();
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setUser_name(resultSet.getString(USER_NAME));
                activityRecord.setActivity_name(resultSet.getString("name"));
                activityRecord.setTime(resultSet.getInt("time"));
                resultList.add(activityRecord);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT FOUND_ROWS()");

            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);

        } catch (SQLException e) {
            LOG.error("Error while findAllWithLimits records : " + e);
            throw new DBException("Error while findAllWithLimits records", e);
        }
        LOG.trace("findAllWithLimits of UsersActivityFull method result list : " + resultList);
        LOG.debug("findAllWithLimits of UsersActivityFull method terminates");
        return resultList;
    }

    public UsersActivity findRecord(int userId, int activityId) throws DBException {

        LOG.debug("findEntityById UsersActivity method starts");

        if (userId <= 0 || activityId <= 0) {
            LOG.trace("UsersActivity findEntityById : " + Optional.empty());
            return null;
        }

        UsersActivity activityRecord = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_USER_ID_ACTIVITY_ID)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, activityId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                activityRecord = UsersActivity.createUserActivity(
                        resultSet.getInt(USER_ID),
                        resultSet.getInt(ACTIVITY_ID));
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setTime(resultSet.getInt("time"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("Error while getting ActivityRecord in findRecord() : " + e);
            throw new DBException("Error while getting ActivityRecord in findRecord()", e);
        }
        LOG.trace("return user-activity : " + Optional.ofNullable(activityRecord));
        LOG.debug("findEntityById user activity method terminates");
        return activityRecord;
    }

    public List<UsersActivityFull> findAllUserRecordsWithLimits(int userId, int offset, int noOfRecords)
            throws DBException{

        LOG.debug("findAllUserRecordsWithLimits UsersActivity method starts");

        final String SQL_SELECT_ALL_LIMIT = "SELECT SQL_CALC_FOUND_ROWS l.id, u.user_name, a.name, l.time " +
                "FROM users_activities l " +
                "         CROSS JOIN users u " +
                "                    ON l.user_id = u.user_id " +
                "         CROSS JOIN activities a " +
                "                    ON l.activity_id = a.activity_id " +
                "WHERE u.user_id=" + userId + " ORDER BY user_name LIMIT " + offset + ", " + noOfRecords;

        List<UsersActivityFull> resultList = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(SQL_SELECT_ALL_LIMIT);

            while ( resultSet.next() ) {
                UsersActivityFull activityRecord = new UsersActivityFull();
                activityRecord.setId(resultSet.getInt("id"));
                activityRecord.setUser_name(resultSet.getString(USER_NAME));
                activityRecord.setActivity_name(resultSet.getString("name"));
                activityRecord.setTime(resultSet.getInt("time"));
                resultList.add(activityRecord);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT FOUND_ROWS()");

            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);

        } catch (SQLException e) {
            LOG.error("Error while findAllWithLimits records : " + e);
            throw new DBException("Error while findAllWithLimits records", e);
        }
        LOG.trace("findAllWithLimits of UsersActivityFull method result list : " + resultList);
        LOG.debug("findAllWithLimits of UsersActivityFull method terminates");
        return resultList;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

}
