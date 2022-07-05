package com.project.time_management.helpers;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Class that provides method to get page for pagination on the front.
 */
public class PaginationHelper {

    private static final Logger LOG = Logger.getLogger(PaginationHelper.class);

    public static final int RECORDS_PER_PAGE = 1;

    public int getPageParameter (HttpServletRequest req) {
        LOG.debug("PaginationHelper invoked");
        int page = 1;

        if((req.getParameter("page") != null)
                && (Integer.parseInt(req.getParameter("page")) > 0)) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        LOG.trace("Page parameter --> " + page);
        return page;
    }
}
