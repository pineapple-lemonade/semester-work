package ru.itis.ruzavin.servlets;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.services.UserService;
import ru.itis.ruzavin.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/userInfo")
public class UserInfoServlet extends HttpServlet {

	private UserService userService;
	//private GuideService guideService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDTO userNow = (UserDTO) req.getAttribute("user");
		int id = Integer.parseInt(req.getParameter("id"));

		if (userNow != null && userNow.getId() == id) {
			req.getRequestDispatcher("/profile").forward(req, resp);
		} else {
			UserDTO user = userService.findUserById(id);

			req.setAttribute("detailUser", user);

			req.getRequestDispatcher("/pages/userInfo.ftl").forward(req, resp);
		}
	}

	@Override
	public void init() throws ServletException {
		userService = new UserServiceImpl();
		//guideService = new GuideServiceImpl();
	}
}
