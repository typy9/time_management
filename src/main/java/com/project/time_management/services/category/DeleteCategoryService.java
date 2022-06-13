package com.project.time_management.services.category;

import com.project.time_management.dao.CategoryDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Category;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCategoryService {

    private static final Logger LOG = Logger.getLogger(DeleteCategoryService.class);
    private final DataSource dataSource;
    public int noOfPages;
    public DeleteCategoryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Category> deleteCategory (int id, int page, int recordsPerPage) {
        LOG.debug("Start executing deleteCategory");
        List<Category> categories = new ArrayList<>();

        if (idIsValid(id)) {

            try (Connection conn = dataSource.getConnection()) {
                CategoryDAO categoryDAO = new CategoryDAO(conn);
                categoryDAO.delete(id);
                categories = categoryDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);

                int noOfRecords = categoryDAO.getNoOfRecords();
                noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            } catch (SQLException e) {
                LOG.error("SQLException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing deleteCategory");
        return categories;
    }

    private boolean idIsValid(int id) {
        return id > 0;
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}

