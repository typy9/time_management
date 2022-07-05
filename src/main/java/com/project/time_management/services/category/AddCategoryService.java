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

public class AddCategoryService {

    private static final Logger LOG = Logger.getLogger(AddCategoryService.class);
    private final DataSource dataSource;
    public int noOfPages;

    public AddCategoryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Category> addCategory(String name, int page, int recordsPerPage) {
        LOG.debug("Start executing addCategory");
        List<Category> categories = new ArrayList<>();

        if (requestIsValid(name)) {

            final Category categoryInstance = new Category(name);

            try (Connection conn = dataSource.getConnection()){
                CategoryDAO categoryDAO = new CategoryDAO(conn);
                categoryDAO.create(categoryInstance);
                categories = categoryDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);
                int noOfRecords = categoryDAO.getNoOfRecords();
                noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            } catch (SQLException e) {
                LOG.error("SQLException while addCategory");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while addCategory");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing addCategory");
        return categories;
    }

    private boolean requestIsValid(String name) {
        return name != null && name.length() > 0;
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
