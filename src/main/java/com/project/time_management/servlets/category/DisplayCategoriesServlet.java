package com.project.time_management.servlets.category;

import com.project.time_management.entity.Category;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.category.DisplayCategoriesService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/categories_list")
public class DisplayCategoriesServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DisplayCategoriesServlet.class);
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        DisplayCategoriesService processRequest = new DisplayCategoriesService(dataSource);
        List<Category> categories = processRequest.displayCategory(page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("categories", categories);

        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/categories_list");

        request.getRequestDispatcher("/categories_list.jsp").forward(request, response);

        LOG.debug("Finish executing doGet");
    }

    public void destroy() {

    }
}
