package com.project.time_management.servlets.category;

import com.project.time_management.entity.Category;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.category.DeleteCategoryService;
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

@WebServlet("/category_delete")
public class DeleteCategoryServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeleteCategoryServlet.class);
//    private List<Category> categories;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object categories = getServletContext().getAttribute("categories");
//
//        if (!(categories instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("categories repo does not initialize!");
//        } else {
//            this.categories = (CopyOnWriteArrayList<Category>) categories;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        int id = Integer.valueOf(request.getParameter("id"));

        DeleteCategoryService processRequest = new DeleteCategoryService(dataSource);
        List<Category> categories = processRequest.deleteCategory(id, page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("categories", categories);
        response.sendRedirect(request.getContextPath() + "/categories_list");

        LOG.debug("Finish executing doPost");
    }

}
