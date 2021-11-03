package ru.itis.ruzavin.servlets;


import ru.itis.ruzavin.services.SecurityService;
import ru.itis.ruzavin.services.SecurityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "signInServlet", urlPatterns = "/signIn")
public class SignInServlet extends HttpServlet {

	private SecurityService securityService;

	@Override
	public void init() throws ServletException {
		securityService = new SecurityServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/pages/signIn.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(securityService.signIn(req, resp)){
			resp.sendRedirect("/profile");
		} else {
			req.setAttribute("userLogin", req.getParameter("userLogin"));
			req.setAttribute("message","failed to sign in");
			getServletContext().getRequestDispatcher("/pages/signIn.ftl").forward(req, resp);
		}
	}
}
