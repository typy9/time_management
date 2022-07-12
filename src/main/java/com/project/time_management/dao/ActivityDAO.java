package com.project.time_management.dao;

import com.project.time_management.entity.Activity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class ActivityDAO extends AbstractDAO<Activity> {

    private static final Logger LOG = Logger.getLogger(ActivityDAO.class);


    private int noOfRecords;

    private static final String ACTIVITY_ID = "activity_id";
    private static final String NAME = "name";
    private static final String CATEGORY_ID = "activity_category_id";
    private static final String SQL_SELECT_ALL = "SELECT * FROM activities ORDER BY activity_id";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM activities WHERE activity_id=? ORDER BY activity_id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM activities WHERE activity_id=?";
    private static final String SQL_DELETE = "DELETE FROM activities WHERE activity_id in(%s)";
    private static final String SQL_CREATE = "INSERT INTO activities(name, activity_category_id) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE activities SET name=?, activity_category_id=? WHERE activity_id=?";
    private static final String SQL_SORT_BY_NAME = "SELECT * FROM activities ORDER BY name";
    private static final String SQL_SORT_BY_CATEGORY = "SELECT * FROM activities ORDER BY activity_category_id";
    private static final String SQL_FIND_ACTIVITY_BY_CATEGORY_ID = "SELECT * FROM activities " +
            "WHERE activity_category_id=? ORDER BY activity_id";
    private static final String SQL_SELECT_RANGE = "SELECT * FROM activities " +
            "ORDER BY activity_id LIMIT ?,?";
    private static final String SQL_FIND_ACTIVITY_BY_NAME = "SELECT activity_id FROM activities WHERE name=?";

    public ActivityDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Activity> findAll() throws DBException {
        LOG.debug("findAll method starts");
        List<Activity> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Activity activity = new Activity();
                activity.setActivityId(resultSet.getInt(ACTIVITY_ID));
                activity.setName(resultSet.getString(NAME));
                activity.setCategory(resultSet.getInt(CATEGORY_ID));
                resultList.add(activity);
            }

        } catch (SQLException e) {
            LOG.error("SQLException thrown : " + e);
            throw new DBException("Error while finding all activities in findAll()", e);
        }
        LOG.trace("return list : " + resultList);
        LOG.debug("findAll method terminates");
        return resultList;
    }

    @Override
    public Optional<Activity> findEntityById(int id) throws DBException {
        LOG.debug("findEntityById method starts");
        if (id <= 0) {
            LOG.trace("findEntityById : " + Optional.empty());
            return Optional.empty();
        }

        Activity activity = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                activity = Activity.createActivity(resultSet.getString(NAME));
                activity.setActivityId(resultSet.getInt(ACTIVITY_ID));
                activity.setCategory(resultSet.getInt(CATEGORY_ID));
                break;
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while getting activity", e);
        }
        LOG.trace("return activity : " + activity);
        LOG.debug("findEntityById method terminates");
        return Optional.ofNullable(activity);
    }

    @Override
    public boolean delete(int id) throws DBException {
        LOG.debug("delete activity by id method starts");
        if (id <= 0) {
            LOG.trace("invalid id, delete activity : " + false);
            return false;
        }
        boolean rowsUpdate;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while deleting activity", e);
        }
        LOG.trace("delete delete activity : " + rowsUpdate);
        LOG.debug("delete method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean delete(Activity... activities) throws DBException {
        LOG.debug("delete list of activities method starts");
        if (activities == null || activities.length == 0) {
            LOG.trace("invalid input, delete activities : " + false);
            return false;
        }

        boolean rowsUpdate;
        StringJoiner sj = new StringJoiner(",");

        for ( Activity activity : activities ) {
            sj.add(String.valueOf(activity.getActivityId()));
        }
        String sql = String.format(SQL_DELETE, sj);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while deleting activities", e);
        }
        LOG.trace("return delete activity : " + rowsUpdate);
        LOG.debug("delete list of activities method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean create(Activity activity) throws DBException {

        LOG.debug("create activity method starts");

        if (activity == null) {
            LOG.trace("invalid input, create activity : " + false);
            return false;
        }
        boolean rowsUpdate;

        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, activity.getName());
            ps.setInt(2, activity.getCategory());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while ( rs.next() ) {
                activity.setActivityId(rs.getInt(1));
            }
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while creating activity", e);
        }
        LOG.trace("return create activity : " + rowsUpdate);
        LOG.debug("create activity method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean update(Activity activity) throws DBException {

        LOG.debug("update activity method starts");

        if (activity == null) {
            LOG.trace("invalid input, create activity : " + false);
            return false;
        }

        boolean rowsUpdate;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, activity.getName());
            preparedStatement.setInt(2, activity.getCategory());
            preparedStatement.setInt(3, activity.getActivityId());
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while updating activity", e);
        }
        LOG.trace("return create activity : " + rowsUpdate);
        LOG.debug("update activity method starts method terminates");
        return rowsUpdate;
    }

    public List<Activity> sortByName() throws DBException {

        LOG.debug("sortByName activity method starts");

        List<Activity> sortedList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SORT_BY_NAME)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Activity activity = new Activity();
                activity.setActivityId(resultSet.getInt(ACTIVITY_ID));
                activity.setName(resultSet.getString(NAME));
                activity.setCategory(resultSet.getInt(CATEGORY_ID));
                sortedList.add(activity);
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while finding all activities in sortByName()", e);
        }
        LOG.trace("returned activity list : " + sortedList);
        LOG.debug("sortByName activity method terminates");
        return sortedList;
    }

    public List<Activity> sortByCategory() throws DBException {

        LOG.debug("sortByCategory activity method starts");

        List<Activity> sortedList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SORT_BY_CATEGORY)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Activity activity = new Activity();
                activity.setActivityId(resultSet.getInt(ACTIVITY_ID));
                activity.setName(resultSet.getString(NAME));
                activity.setCategory(resultSet.getInt(CATEGORY_ID));
                sortedList.add(activity);
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while finding all activities", e);
        }
        LOG.trace("return activity list : " + sortedList);
        LOG.debug("sortByCategory activity method terminates");
        return sortedList;
    }

    public List<Activity> findActivitiesByCategory(int id) throws DBException {

        LOG.debug("findActivitiesByCategory id activity method starts");

        if (id <= 0) {
            LOG.trace("findActivitiesByCategory id : " + false);
            return Collections.emptyList();
        }

        List<Activity> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACTIVITY_BY_CATEGORY_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Activity activity = new Activity();
                activity.setActivityId(resultSet.getInt(ACTIVITY_ID));
                activity.setName(resultSet.getString(NAME));
                activity.setCategory(resultSet.getInt(CATEGORY_ID));
                resultList.add(activity);
            }
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while finding activities by category", e);
        }
        LOG.trace("return activity list : " + resultList);
        LOG.debug("findActivitiesByCategory id method terminates");
        return resultList;
    }

    public int findActivityIdByName(String name) throws DBException {
        LOG.debug("findActivitiesByCategory id activity method starts");
        int result = 0;
        if (name == null || name.length() <= 0) {
            LOG.trace("findActivityIdByName : " + result);
            return result;
        }

        try ( PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACTIVITY_BY_NAME)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                result = resultSet.getInt(ACTIVITY_ID);
            }
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while finding activity id by its name", e);
        }
        LOG.trace("return activity id : " + result);
        LOG.debug("findActivityIdByName id method terminates");
        return result;
    }

    public List<Activity> getRecords(int pageId, int total) throws DBException {

        LOG.debug("getRecords activity method starts");

        List<Activity> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_RANGE)) {

            statement.setInt(1, pageId - 1);
            statement.setInt(2, total);
            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Activity activity = new Activity();
                activity.setActivityId(resultSet.getInt(ACTIVITY_ID));
                activity.setName(resultSet.getString(NAME));
                activity.setCategory(resultSet.getInt(CATEGORY_ID));
                resultList.add(activity);
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while finding all activities", e);
        }
        LOG.trace("return list : " + resultList);
        LOG.debug("getRecords activity method terminates");
        return resultList;
    }

    public List<Activity> findAllWithLimits(int offset, int noOfRecords)
            throws DBException {

        LOG.debug("findAllWithLimits activity method starts");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM activities LIMIT "
                + offset + ", " + noOfRecords;
        List<Activity> resultList = new ArrayList<>();
        Activity activity;

        try (Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                activity = new Activity();
                activity.setActivityId(resultSet.getInt(ACTIVITY_ID));
                activity.setName(resultSet.getString(NAME));
                activity.setCategory(resultSet.getInt(CATEGORY_ID));
                resultList.add(activity);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new RuntimeException(e);
        }
        LOG.trace("findAllWithLimits activity method returns list : " + resultList);
        LOG.debug("findAllWithLimits activity method terminates");
        return resultList;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }
}
