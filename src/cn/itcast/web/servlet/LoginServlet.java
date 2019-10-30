package cn.itcast.web.servlet;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String verifycode=request.getParameter("verifycode").toLowerCase();
        HttpSession session = request.getSession();
        String check_session = (String) session.getAttribute("checkCode_session");
        User user = new User();
        session.removeAttribute("checkCode_session");
        if (check_session != null && check_session.equalsIgnoreCase(verifycode)){
            Map<String,String[]> map = request.getParameterMap();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else {
            request.setAttribute("login_msg","验证码错误");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }

        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);
        if (loginUser != null){
            session.setAttribute("user",loginUser);
            response.sendRedirect("index.jsp");
        }else {
            request.setAttribute("login_msg","用户名或密码错误");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
