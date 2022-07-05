package com.project.time_management.servlets.category;

import com.project.time_management.entity.Category;
import com.project.time_management.services.category.UpdateCategoryService;
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


@WebServlet("/category_update")
public class UpdateCategoryServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UpdateCategoryServlet.class);
//    private List<Category> categories;

    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object categories = getServletContext().getAttribute("categories");
//
//        if (categories == null || !(categories instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("You're repo does not initialize!");
//        } else {
//            this.categories = (CopyOnWriteArrayList<Category>) categories;
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        final String id = request.getParameter("id");
        final String name = request.getParameter("name");

        if (idIsInvalid(id)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        UpdateCategoryService processRequest = new UpdateCategoryService(dataSource);
        Category category = processRequest.getCategoryForUpdate(id);

        request.setAttribute("category", category);
        request.getRequestDispatcher("/update_category.jsp").forward(request, response);

        LOG.debug("Finish executing doGet");
    }

    private boolean idIsInvalid(String id) {
        return !(id != null && id.matches("[+]?\\d+"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final int id = Integer.parseInt(request.getParameter("id"));
        final String name = request.getParameter("name");

        UpdateCategoryService processRequest = new UpdateCategoryService(dataSource);
        List<Category> categories = processRequest.updateCategory(id, name);

        request.setAttribute("categories", categories);
        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/category_update");

        response.sendRedirect(request.getContextPath() + "/categories_list");

        LOG.debug("Finish executing doPost");
    }
}
