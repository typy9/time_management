package com.project.time_management.services.category;

import com.project.time_management.dao.CategoryDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Category;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DisplayCategoriesService {

    private static final Logger LOG = Logger.getLogger(DisplayCategoriesService.class);
    private final DataSource dataSource;
    public int noOfPages;

    public DisplayCategoriesService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Category> displayCategory(int page, int recordsPerPage) {
        LOG.debug("Start executing displayCategory");
        List<Category> categories;

        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            categories = categoryDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);

            int noOfRecords = categoryDAO.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        }catch (SQLException e) {
            LOG.error("SQLException while displayCategory");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while displayCategory");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing displayCategory");
        return categories;
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
