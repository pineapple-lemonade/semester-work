package ru.itis.ruzavin.servlets.security;

import ru.itis.ruzavin.services.SecurityService;
import ru.itis.ruzavin.services.SecurityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "signUpServlet", urlPatterns = "/signUp")
public class SignUpServlet extends HttpServlet {

	private SecurityService securityService;

	@Override
	public void init() throws ServletException {
		securityService = new SecurityServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/pages/signUp.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (securityService.signUp(req)) {
			resp.sendRedirect("/info");
		} else {
			req.setAttribute("message", "true");
			resp.sendRedirect("/signUp");
		}
	}
}
