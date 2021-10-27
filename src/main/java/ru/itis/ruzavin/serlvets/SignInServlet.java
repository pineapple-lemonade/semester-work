package ru.itis.ruzavin.serlvets;


import ru.itis.ruzavin.services.SecurityService;
import ru.itis.ruzavin.services.SecurityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
		req.setAttribute("message","");
		req.getRequestDispatcher("/pages/signIn.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(securityService.signIn(req, resp)){
			HttpSession session = req.getSession(false);
			req.setAttribute("user", session.getAttribute("user"));
			req.getRequestDispatcher("/pages/profile.ftl").forward(req, resp);
		} else {
			req.setAttribute("userLogin", req.getParameter("userLogin"));
			req.setAttribute("isFailedToSignIn",true);
			getServletContext().getRequestDispatcher("/pages/signIn.ftl").forward(req, resp);
		}
	}
}
