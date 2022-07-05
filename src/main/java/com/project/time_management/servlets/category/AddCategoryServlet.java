package com.project.time_management.servlets.category;

import com.project.time_management.entity.Category;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.category.AddCategoryService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/category_add")
public class AddCategoryServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AddCategoryServlet.class);
//    private List<Category> categories;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object categories = getServletContext().getAttribute("categories");
//
//        if (!(categories instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Categories repo did not initialize!");
//        } else {
//            this.categories = (CopyOnWriteArrayList<Category>) categories;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(req);

        final String name = req.getParameter("name");

        AddCategoryService processRequest = new AddCategoryService(dataSource);
        List<Category> categories = processRequest.addCategory(name, page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("recordsPerPage", recordsPerPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("categories", categories);
        resp.sendRedirect(req.getContextPath() + "/categories_list");

        LOG.debug("Finish executing doPost");
    }

}
