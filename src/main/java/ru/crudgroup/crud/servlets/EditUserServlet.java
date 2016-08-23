package ru.crudgroup.crud.servlets;

import ru.crudgroup.crud.store.UserBase;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * контроллер - сервлет
 * вызывается при редактировании данных существующих пользователей
 */
public class EditUserServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // получаем ID редактируемого пользователя
            int id = Integer.parseInt(req.getParameter("id"));
            // передаем в реквест текущие параметры редактируемого пользователя
            req.setAttribute("name", UserBase.getInstance().get(id).getName());
            req.setAttribute("age", UserBase.getInstance().get(id).getAge());
            req.setAttribute("admin", UserBase.getInstance().get(id).isAdmin());
            // передаем реквест и респонс в jsp
            RequestDispatcher dispatcher = req.getRequestDispatcher("/view/EditUser.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
