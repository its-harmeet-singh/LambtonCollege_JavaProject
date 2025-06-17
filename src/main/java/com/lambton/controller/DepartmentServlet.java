package com.lambton.controller;

import com.lambton.dao.DepartmentDAO;
import com.lambton.model.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {
    private DepartmentDAO dao = new DepartmentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("newDepartment.jsp").forward(req, resp);
                break;
            case "edit":
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("department", dao.getDepartmentById(id));
                req.getRequestDispatcher("editDepartment.jsp").forward(req, resp);
                break;
            case "delete":
                dao.deleteDepartment(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("departments");
                break;
            default:
                List<Department> list = dao.getAllDepartments();
                req.setAttribute("departments", list);
                req.getRequestDispatcher("listDepartments.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action  = req.getParameter("action");
        String name    = req.getParameter("name");
        String location= req.getParameter("location");

        if ("insert".equals(action)) {
            dao.insertDepartment(new Department(name, location));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updateDepartment(new Department(id, name, location));
        }
        resp.sendRedirect("departments");
    }
}
