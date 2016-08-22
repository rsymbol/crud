package ru.crudgroup.crud.servlets;

import ru.crudgroup.crud.store.UserBase;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("name", UserBase.getInstance().get(id).getName());
            req.setAttribute("age", UserBase.getInstance().get(id).getAge());
            req.setAttribute("admin", UserBase.getInstance().get(id).isAdmin());
            req.setAttribute("base", UserBase.getInstance().values());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/view/EditUser.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
