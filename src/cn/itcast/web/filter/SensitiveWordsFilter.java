package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("getParameter")){
                   String value = (String) method.invoke(req,args);
                   if (value != null){
                       for (String str : list){
                           if (value.contains(str)){
                               value = value.replaceAll(str,"***");
                           }
                       }
                   }
                   return value;
                }
                return method.invoke(req,args);
            }
        });
        chain.doFilter(proxy_req, resp);
    }
    private List<String> list = new ArrayList<String>();
    public void init(FilterConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        String realpath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(realpath));
            String line = null;
            while ((line = br.readLine())!=null){
                list.add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
