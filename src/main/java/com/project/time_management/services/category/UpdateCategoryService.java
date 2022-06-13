package com.project.time_management.services.category;

import com.project.time_management.dao.CategoryDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Category;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UpdateCategoryService {

    private static final Logger LOG = Logger.getLogger(UpdateCategoryService.class);
    private final DataSource dataSource;
    public int noOfPages;

    public UpdateCategoryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Category getCategoryForUpdate(String id) {
        LOG.debug("Start executing getCategoryForUpdate");
        Category category;

        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            Optional<Category> categoryToUpdate = categoryDAO.findEntityById(Integer.parseInt(id));
            category = categoryToUpdate.get();
        } catch (SQLException e) {
            LOG.error("SQLException while getCategoryForUpdate");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getCategoryForUpdate");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing getCategoryForUpdate");
        return category;
    }

    public List<Category> updateCategory(int id, String name) {
        LOG.debug("Start executing updateCategory");
        List<Category> categories ;

        try (Connection conn = dataSource.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            Optional<Category> categoryToUpdate = categoryDAO.findEntityById(id);
            if (categoryToUpdate.isPresent()) {
                Category category = categoryToUpdate.get();
                category.setName(name);
                categoryDAO.update(category);
            }
            categories = categoryDAO.findAll();
        } catch (SQLException e) {
            LOG.error("SQLException while updateCategory");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while updateCategory");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing updateCategory");
        return categories ;
    }
}