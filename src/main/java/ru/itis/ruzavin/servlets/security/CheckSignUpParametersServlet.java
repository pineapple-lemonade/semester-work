package ru.itis.ruzavin.servlets.security;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.services.UserService;
import ru.itis.ruzavin.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet(urlPatterns = "/checkSignUp")
public class CheckSignUpParametersServlet extends HttpServlet {

	private UserService userService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UserDTO> users = userService.findAll();
		String result = "free";
		Enumeration<String> parameterNames = req.getParameterNames();
		String parameter = parameterNames.nextElement();

		result = findMatches(req, users, result, parameter);

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(result);
	}

	private String findMatches(HttpServletRequest req, List<UserDTO> users, String result, String parameter) {
		System.out.println(parameter);
		switch (parameter){
			case "email":
				for(UserDTO user : users) {
					if(user.getEmail().equals(req.getParameter(parameter))) {
						result = parameter + " taken";
						break;
					}
				}
				break;
			case "login":
				for(UserDTO user : users) {
					if(user.getLogin().equals(req.getParameter(parameter))) {
						result = parameter + " taken";
						break;
					}
				}
				break;
			case "nick":
				for(UserDTO user : users) {
					if(user.getNick().equals(req.getParameter(parameter))) {
						result = parameter + " taken";
						break;
					}
				}
				break;
		}
		return result;
	}

	@Override
	public void init() throws ServletException {
		userService = new UserServiceImpl();
	}
}
