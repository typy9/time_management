package com.project.time_management.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.Writer;

public class PaginationFormatterTag extends SimpleTagSupport {

    private int currentPage;
    private int noOfPages;
    String page;
//    String nextPage;
//    String previousPage;
//
//    public void setNextPage(String nextPage) {
//        this.nextPage = nextPage;
//    }
//
//    public void setPreviousPage(String previousPage) {
//        this.previousPage = previousPage;
//    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private Writer getWriter() {
        JspWriter out = getJspContext().getOut();
        return out;
    }

    @Override
    public void doTag() throws JspException {
        Writer out = getWriter();

        try {
//            <c:if test="${requestScope.currentPage != 1}">
//                <td><a href="users_list?page=${requestScope.currentPage - 1}"><fmt:message key="pagination.previous" /></a></td>
//            </c:if>
//            if (currentPage != 1) {
//                out.write("<td><a href=\"users_list?page=" + (currentPage - 1) +
//                        "\">" + previousPage + "</a></td>");
//            }

            for (int i = 1; i <= noOfPages; i++) {
                if (currentPage == i) {
                    out.write("<td>" + i + "</td>");
                } else
                    out.write("<td><a href=\"" + page + "?page=" + i +
                            "\">" + i + "</a></td>");
            }

//            if (currentPage < noOfPages) {
//                out.write("<td><a href=\"users_list?page=" + (currentPage + 1) +
//                        "\">" + nextPage + "</a></td>");
//            }

        } catch (java.io.IOException ex) {
            throw new JspException("Error in Paginator tag", ex);
        }
    }
}
