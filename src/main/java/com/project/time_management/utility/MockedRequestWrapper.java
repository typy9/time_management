package com.project.time_management.utility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MockedRequestWrapper extends HttpServletRequestWrapper {
    private Map<String,Object> m = null;
    private final HttpServletRequest origReq;

    public MockedRequestWrapper(HttpServletRequest request)
            throws IOException, ServletException {
        super(request);
        origReq = request;
    }

    @Override
    public void setAttribute(String name, Object o){
        m = new HashMap<>();
        m.put(name, o);
        super.setAttribute(name, o);
        origReq.setAttribute(name, o);
    }

    @Override
    public Object getAttribute(String name){
        if(m != null){
            return m.get(name);
        } else if(origReq.getAttribute(name) != null){
            return origReq.getAttribute(name);
        } else {
            return super.getAttribute(name);
        }
    }
    @Override
    public String getParameter(String name) {
        if(m != null){
            if (m.get(name) == null) {
                return null;
            }
            return String.valueOf(m.get(name));
        } else if(origReq.getAttribute(name) != null){
            return String.valueOf(origReq.getAttribute(name));
        } else {
            return String.valueOf(super.getAttribute(name));
        }
    }
}
