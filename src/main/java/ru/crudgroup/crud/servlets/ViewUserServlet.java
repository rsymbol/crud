package ru.crudgroup.crud.servlets;

import ru.crudgroup.crud.models.User;
import ru.crudgroup.crud.store.UserBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page");
        if (pageStr!=null) UserBase.setCurrentPage(Integer.parseInt(pageStr));
        String sort = req.getParameter("sort");
        if (sort!=null) UserBase.current_sort = sort;
        req.setAttribute("page", UserBase.current_page);
        req.setAttribute("sort", UserBase.current_sort);
        String status = req.getParameter("statusForm");
        if ("delete".equals(status)) {
            int id = Integer.parseInt(req.getParameter("id"));
            UserBase.getInstance().delete(id);
        }
        req.setAttribute("base", UserBase.getInstance().values());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/ViewUser.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String mes_cr1 = ""; String val_cr1 = "";
            String mes_cr2 = ""; String val_cr2 = "";
            String val_cr3 = "";
            String pageStr = req.getParameter("page");
            if (pageStr!=null) UserBase.setCurrentPage(Integer.parseInt(pageStr));
            String sort = req.getParameter("sort");
            if (sort!=null) UserBase.current_sort = sort;
            String status = req.getParameter("statusForm");
            if (!"".equals(status)) {
                String name = req.getParameter("name");
                String ageStr = req.getParameter("age");
                String admin = req.getParameter("admin");
                if (name!=null) mes_cr1 = validName(name);
                if (ageStr!=null) mes_cr2 = validAge(ageStr);
                if (!"".equals(mes_cr1) || !"".equals(mes_cr2)){
                    val_cr1 = name;
                    val_cr2 = ageStr;
                    val_cr3 = admin;
                } else {
                    boolean isAdmin = admin.equals("true");
                    int age = Integer.valueOf(ageStr);
                    if ("create".equals(status)) {

                        User user = new User(UserBase.getInstance().generateId(), name, age, isAdmin, new Timestamp(new Date().getTime()));
                        UserBase.getInstance().add(user);
                    }
                    if ("edit".equals(status)) {
                        int id = Integer.parseInt(req.getParameter("id"));
                        User user = new User(id, name, age, isAdmin, new Timestamp(new Date().getTime()));
                        UserBase.getInstance().edit(user);
                    }
                }
            }
            req.setAttribute("mes_cr1", mes_cr1); req.setAttribute("val_cr1", val_cr1);
            req.setAttribute("mes_cr2", mes_cr2); req.setAttribute("val_cr2", val_cr2);
            req.setAttribute("val_cr3", val_cr3);
            req.setAttribute("page", UserBase.current_page);
            req.setAttribute("sort", UserBase.current_sort);
            req.setAttribute("base", UserBase.getInstance().values());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/view/ViewUser.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String validName(String name) {
        if ("".equals(name)) return "need to fill";
        if (name.length() > 25) return "max length 25 symbol";
        Pattern pattern = Pattern.compile("[A-Za-z0-9@_А-Яа-я]+");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) return "username is invalid (must be symbol [A-Za-z0-9@_А-Яа-я])";
        return "";
    }

    private String validAge(String age) {
        if ("".equals(age)) return "need to fill";
        if (age.length() > 2) return "max length 2 digit";
        Pattern pattern = Pattern.compile("[1-]?[0-9]+");
        Matcher matcher = pattern.matcher(age);
        if (!matcher.matches()) return "only digit";
        return "";
    }
}

