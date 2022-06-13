package com.project.time_management.services.request;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.dao.RequestDAO;
import com.project.time_management.entity.Request;
import com.project.time_management.services.category.AddCategoryService;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddActivityRequestService {

    private static final Logger LOG = Logger.getLogger(AddActivityRequestService.class);
    private final DataSource dataSource;
    public int noOfPages;

    public AddActivityRequestService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Request> addActivityRequest(String activity, int userId) {
        LOG.debug("Start executing addActivityRequest");
        List<Request> activityRequests = null;
        if (requestIsValid(activity, userId)) {

            try ( Connection conn = dataSource.getConnection()) {
                RequestDAO requestDAO = new RequestDAO(conn);
                ActivityDAO activityDAO = new ActivityDAO(conn);

                int activityId = activityDAO.findActivityIdByName(activity);
                final Request request = new Request(userId, activityId, "created");
                requestDAO.create(request);
                activityRequests = requestDAO.findAll();

            } catch (SQLException e) {
                LOG.error("SQLException while addActivityRequest");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while addActivityRequest");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing addActivityRequest");
        return activityRequests;
    }

    private boolean requestIsValid(String activity, int userId) {
        return activity != null && activity.length() > 0 && userId > 0;
    }
}
