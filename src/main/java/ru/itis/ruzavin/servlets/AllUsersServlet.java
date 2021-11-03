package ru.itis.ruzavin.servlets;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.services.GuideService;
import ru.itis.ruzavin.services.GuideServiceImpl;
import ru.itis.ruzavin.services.UserService;
import ru.itis.ruzavin.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(urlPatterns = "/allUsers")
public class AllUsersServlet extends HttpServlet {

	private UserService userService;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserDTO> list = userService.findAll();
		req.setAttribute("users", list);


		req.getRequestDispatcher("/pages/allUsers.ftl").forward(req, resp);
	}

	@Override
	public void init() throws ServletException {
		userService = new UserServiceImpl();
	}
}
