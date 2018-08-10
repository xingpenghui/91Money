package com.qfedu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@Author feri
 *@Date Created in 2018/7/25 11:35
 */
@WebServlet("/cookie.do")
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("ck").equals("r")){
            readCookie(req,resp);
        }else{
            write(req, resp);
        }
    }
    //读取
    public void readCookie(HttpServletRequest request,HttpServletResponse response){
       Cookie[] arrc= request.getCookies();
       for(Cookie c:arrc){
           System.out.println(c.getName()+"--->"+c.getMaxAge()+"---->"+c.getValue());
       }
    }
    //设置Cookie
    public void write(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie=new Cookie("username","admin");
        cookie.setMaxAge(-1);//单位秒
        cookie.setPath("/");
        response.addCookie(cookie);//设置消息头  告知浏览器需要添加Cookie
    }
}
