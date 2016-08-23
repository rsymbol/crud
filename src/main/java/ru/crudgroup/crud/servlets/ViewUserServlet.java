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

/**
 * контроллер - сервлет
 * вызывается для отображения текущей базы пользователей
 */
public class ViewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // получаем страницу, которую необходимо отобразить
        String pageStr = req.getParameter("page");
        // проверяем корректность страницы, чтобы была >0 & < max (если не задано, то по умолчанию)
        if (pageStr!=null) UserBase.setCurrentPage(Integer.parseInt(pageStr));
        // получаем столбец, по которому необходимо сортировать данные
        String sort = req.getParameter("sort");
        // устанавливаем значение сортировки(если не задано, то по умолчанию)
        if (sort!=null) UserBase.current_sort = sort;
        // устанавливаем атрибуты в реквест
        req.setAttribute("page", UserBase.current_page);
        req.setAttribute("sort", UserBase.current_sort);
        // получаем статус формы реквеста
        String status = req.getParameter("statusForm");
        // если отправка на удаление, то удаляем
        if ("delete".equals(status)) {
            int id = Integer.parseInt(req.getParameter("id"));
            UserBase.getInstance().delete(id);
        }
        // передаем актуализированную базу пользователей для отображения
        req.setAttribute("base", UserBase.getInstance().values());
        // отображаем базу пользователей
        RequestDispatcher dispatcher = req.getRequestDispatcher("/view/ViewUser.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // значения по умолчанию для полей и сообщений об ошибках
            String mes_cr1 = ""; String val_cr1 = "";
            String mes_cr2 = ""; String val_cr2 = "";
            String val_cr3 = "";
            // получаем страницу, которую необходимо отобразить
            String pageStr = req.getParameter("page");
            // проверяем корректность страницы, чтобы была >0 & < max (если не задано, то по умолчанию)
            if (pageStr!=null) UserBase.setCurrentPage(Integer.parseInt(pageStr));
            // получаем столбец, по которому необходимо сортировать данные
            String sort = req.getParameter("sort");
            // устанавливаем значение сортировки(если не задано, то по умолчанию)
            if (sort!=null) UserBase.current_sort = sort;
            // получаем статус формы реквеста
            String status = req.getParameter("statusForm");
            if (!"".equals(status)) {
                // если статус не пустой, то получаем заведенные параметры по клиенту
                String name = req.getParameter("name");
                String ageStr = req.getParameter("age");
                String admin = req.getParameter("admin");
                // проверяем их на корректность
                if (name!=null) mes_cr1 = validName(name);
                if (ageStr!=null) mes_cr2 = validAge(ageStr);
                if (!"".equals(mes_cr1) || !"".equals(mes_cr2)){
                    // если параметры не корректны, то запоминаем их для повторного вывода
                    val_cr1 = name;
                    val_cr2 = ageStr;
                    val_cr3 = admin;
                } else {
                    // если корректны, то получаем оставшиеся параметры
                    boolean isAdmin = admin.equals("true");
                    int age = Integer.valueOf(ageStr);
                    // проверяем статус
                    if ("create".equals(status)) {
                        // создаем нового пользователя с заданными параметрами
                        User user = new User(UserBase.getInstance().generateId(), name, age, isAdmin, new Timestamp(new Date().getTime()));
                        UserBase.getInstance().add(user);
                    }
                    if ("edit".equals(status)) {
                        // редактируем ползователя с указанным id
                        int id = Integer.parseInt(req.getParameter("id"));
                        User user = new User(id, name, age, isAdmin, new Timestamp(new Date().getTime()));
                        UserBase.getInstance().edit(user);
                    }
                }
            }
            // передаем в реквест сообщения об ошибке и ранее введенные параметры (в случае ошибки)
            req.setAttribute("mes_cr1", mes_cr1); req.setAttribute("val_cr1", val_cr1);
            req.setAttribute("mes_cr2", mes_cr2); req.setAttribute("val_cr2", val_cr2);
            req.setAttribute("val_cr3", val_cr3);
            // устанавливаем атрибуты в реквест
            req.setAttribute("page", UserBase.current_page);
            req.setAttribute("sort", UserBase.current_sort);
            // передаем актуализированную базу пользователей для отображения
            req.setAttribute("base", UserBase.getInstance().values());
            // отображаем базу пользователей
            RequestDispatcher dispatcher = req.getRequestDispatcher("/view/ViewUser.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * проверка корректности имени
     * @param name
     * @return сообщение об ошибке
     */
    private String validName(String name) {
        if ("".equals(name)) return "need to fill";
        if (name.length() > 25) return "max length 25 symbol";
        Pattern pattern = Pattern.compile("[A-Za-z0-9@_А-Яа-я]+");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) return "username is invalid (must be symbol [A-Za-z0-9@_А-Яа-я])";
        return "";
    }

    /**
     * проверка корректности возраста
     * @param age
     * @return сообщение об ошибке
     */
    private String validAge(String age) {
        if ("".equals(age)) return "need to fill";
        if (age.length() > 2) return "max length 2 digit";
        Pattern pattern = Pattern.compile("[1-]?[0-9]+");
        Matcher matcher = pattern.matcher(age);
        if (!matcher.matches()) return "only digit";
        return "";
    }
}

