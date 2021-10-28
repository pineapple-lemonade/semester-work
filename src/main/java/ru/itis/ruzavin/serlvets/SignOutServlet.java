package ru.itis.ruzavin.serlvets;

import ru.itis.ruzavin.services.SecurityService;
import ru.itis.ruzavin.services.SecurityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/signOut")
public class SignOutServlet extends HttpServlet {

	private SecurityService securityService;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		securityService.signOut(req, resp);
		resp.sendRedirect("/signIn");
	}

	@Override
	public void init() throws ServletException {
		securityService = new SecurityServiceImpl();
	}
}
