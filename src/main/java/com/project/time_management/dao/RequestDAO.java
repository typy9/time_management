package com.project.time_management.dao;

import com.project.time_management.entity.Request;
import com.project.time_management.entity.RequestStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class RequestDAO extends AbstractDAO<Request> {



    private int noOfRecords;
    private static final Logger LOG = Logger.getLogger(RequestDAO.class);
    private static final String SQL_SELECT_ALL = "SELECT * FROM activity_request ORDER BY request_id";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM activity_request " +
            "WHERE request_id=? ORDER BY request_id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM activity_request WHERE request_id=?";
    private static final String SQL_DELETE = "DELETE FROM activity_request WHERE request_id in(%s)";
    private static final String SQL_CREATE = "INSERT INTO activity_request(user_id, activity_id, time, status) " +
            "VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE activity_request " +
            "SET user_id=?, activity_id=?, time=?, status=? WHERE request_id=?";
    private static final String SQL_UPDATE_STATUS = "UPDATE activity_request " +
            "SET status=? WHERE request_id=?";
    private static final String SQL_GET_STATUS = "SELECT status FROM activity_request " +
            "WHERE request_id=?";

    private static final String SQL_GET_REQUEST_BY_USER_ACTIVITY_STATUS = "SELECT * FROM activity_request " +
            "WHERE user_id=? AND activity_id=? AND status=? ORDER BY request_id";

    private static final String SQL_UPDATE_STATUS_BY_USER_ACTIVITY_ID = "UPDATE activity_request " +
            "SET status=? WHERE user_id=? AND activity_id=?";

    public RequestDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Request> findAll() throws DBException {

        LOG.debug("findAll request method starts");

        List<Request> resultList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while ( resultSet.next() ) {
                Request request = new Request();
                request.setRequest_id(resultSet.getInt("request_id"));
                request.setUserId(resultSet.getInt("user_id"));
                request.setActivityId(resultSet.getInt("activity_id"));
                request.setTime(resultSet.getInt("time"));
                request.setStatus(resultSet.getString("status"));
                resultList.add(request);
            }

        } catch (SQLException e) {
            LOG.error("Error while finding all activity requests : " + e);
            throw new DBException("Error while finding all activity requests", e);
        }
        LOG.trace("return list : " + resultList);
        LOG.debug("findAll request method terminates");
        return resultList;
    }

    @Override
    public Optional<Request> findEntityById(int id) throws DBException {

        LOG.debug("findEntityById Request method starts");

        if (id <= 0) {
            LOG.trace("findEntityById : " + Optional.empty());
            return Optional.empty();
        }

        Request request = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ) {
                request = Request.createRequest(
                        resultSet.getInt("request_id"),
                        RequestStatus.valueOf(resultSet.getString("status")));
                request.setUserId(resultSet.getInt("user_id"));
                request.setActivityId(resultSet.getInt("activity_id"));
                request.setTime(resultSet.getInt("time"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while getting request", e);
        }
        LOG.trace("return request : " + Optional.ofNullable(request));
        LOG.debug("findEntityById request method terminates");
        return Optional.ofNullable(request);
    }

    @Override
    public boolean delete(int id) throws DBException {

        LOG.debug("delete request by id method starts");

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
            LOG.error("SQLException : " + e);
            throw new DBException("Error while deleting activity request", e);
        }
        LOG.trace("delete delete request : " + rowsUpdate);
        LOG.debug("delete request by id method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean delete(Request... requests) throws DBException {

        LOG.debug("delete list of requests method starts");

        if (requests == null || requests.length == 0) {
            LOG.trace("invalid input, delete requests : " + false);
            return false;
        }

        boolean rowsUpdate;
        StringJoiner sj = new StringJoiner(",");

        for ( Request request : requests ) {
            sj.add(String.valueOf(request.getRequest_id()));
        }
        String sql = String.format(SQL_DELETE, sj);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while deleting activity requests", e);
        }
        LOG.trace("return delete requests : " + rowsUpdate);
        LOG.debug("delete list of requests method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean create(Request request) throws DBException {

        LOG.debug("create request method starts");
        if (request == null) {
            LOG.trace("invalid input, create request : " + false);
            return false;
        }

        boolean rowsUpdate;

        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, request.getUserId());
            ps.setInt(2, request.getActivityId());
            ps.setInt(3, request.getTime());
            ps.setString(4, request.getStatus());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while ( rs.next() ) {
                request.setRequest_id(rs.getInt(1));
            }
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while creating activity request", e);
        }
        LOG.trace("return create request : " + rowsUpdate);
        LOG.debug("create request method terminates");
        return rowsUpdate;
    }

    @Override
    public boolean update(Request request) throws DBException {

        LOG.debug("update request method starts");

        if (request == null) {
            LOG.trace("invalid input, create request : " + false);
            return false;
        }

        boolean rowsUpdate;

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setInt(1, request.getUserId());
            preparedStatement.setInt(2, request.getActivityId());
            preparedStatement.setInt(3, request.getTime());
            preparedStatement.setString(4, request.getStatus());
            preparedStatement.setInt(5, request.getRequest_id());
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while updating request", e);
        }
        LOG.trace("return update request : " + rowsUpdate);
        LOG.debug("update request method terminates");
        return rowsUpdate;
    }

    public boolean updateStatusById(int id, String status) throws DBException {

        LOG.debug("updateStatusById request method starts");

        if (id <= 0 || status == null || status.length() <= 0) {
            LOG.trace("invalid id or status, request updateStatusById : " + false);
            return false;
        }
        boolean rowsUpdate;
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement(SQL_UPDATE_STATUS)) {
            preparedStatement.setString(1,status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            rowsUpdate = true;
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while updating request status", e);
        }
        LOG.trace("updateStatusById request method return : " + rowsUpdate);
        LOG.debug("updateStatusById request method terminates");
        return rowsUpdate;
    }

    public String getStatusById(int id) throws DBException {
        LOG.debug("getStatusById request method starts");
        if (id <= 0) {
            LOG.trace("invalid id or status, request getStatusById : " + false);
            return null;
        }

        String result = null;
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement(SQL_GET_STATUS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString("status");
            }
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while getting request status", e);
        }
        LOG.trace("getStatusById request method return : " + result);
        LOG.debug("getStatusById request method terminates");
        return result;
    }

    public boolean findByUserIdActivityIdStatus(int requestUserId, int requestActivityId, String status)
            throws DBException{

        LOG.debug("findByUserIdActivityId request method starts");
        if (requestUserId <= 0 || requestActivityId <= 0 || status.isEmpty()) {
            LOG.trace("invalid user id or activity id, request findByUserIdActivityId or status " );
            throw new DBException("invalid user id or activity id, request findByUserIdActivityId, or status");
        }


        Request request = null;

        try(PreparedStatement preparedStatement =
                    connection.prepareStatement(SQL_GET_REQUEST_BY_USER_ACTIVITY_STATUS)) {
            preparedStatement.setInt(1, requestUserId);
            preparedStatement.setInt(2, requestActivityId);
            preparedStatement.setString(3, status);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                request = Request.createRequest(
                        resultSet.getInt("request_id"),
                        RequestStatus.valueOf(resultSet.getString("status").toUpperCase()));
                request.setUserId(resultSet.getInt("user_id"));
                request.setActivityId(resultSet.getInt("activity_id"));
                request.setTime(resultSet.getInt("time"));
                break;
            }

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while getting request status", e);
        }
        LOG.trace("findByUserIdActivityId request method return : " + request != null);
        LOG.debug("findByUserIdActivityId request method terminates");

        return request != null;
    }

    public boolean updateStatusByUserIdActivityId(int requestUserId, int requestActivityId, String status)
            throws DBException{


        if (requestUserId <= 0 || requestActivityId <= 0 ||
                status == null || status.length() <= 0) {
            LOG.trace("invalid id or status, request updateStatusByUserIdActivityId : " + false);
            return false;
        }

        boolean rowsUpdate;

        try(PreparedStatement preparedStatement =
                    connection.prepareStatement(SQL_UPDATE_STATUS_BY_USER_ACTIVITY_ID)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, requestUserId);
            preparedStatement.setInt(3, requestActivityId);
            preparedStatement.executeUpdate();
            rowsUpdate = true;

        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new DBException("Error while updating request status", e);
        }
        LOG.trace("updateStatusById request method return : " + rowsUpdate);
        LOG.debug("updateStatusById request method terminates");
        return rowsUpdate;
    }

    public List<Request> findAllWithLimits(int offset, int noOfRecords)
            throws DBException {

        LOG.debug("findAllWithLimits requests method starts");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM activity_request LIMIT "
                + offset + ", " + noOfRecords;

        List<Request> resultList = new ArrayList<>();
        Request request;

        try (Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                request = new Request();
                request.setRequest_id(resultSet.getInt("request_id"));
                request.setUserId(resultSet.getInt("user_id"));
                request.setActivityId(resultSet.getInt("activity_id"));
                request.setTime(resultSet.getInt("time"));
                request.setStatus(resultSet.getString("status"));
                resultList.add(request);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new RuntimeException(e);
        }
        LOG.trace("findAllWithLimits request method returns list : " + resultList);
        LOG.debug("findAllWithLimits request method terminates");
        return resultList;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }


}
