package ru.itis.ruzavin.serlvets;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("pages/signUp.ftl");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		securityService = new SecurityServiceImpl();

		if(securityService.signUp(req)){
			resp.sendRedirect(getServletContext().getContextPath() + "/profile");
		} else {
			req.setAttribute("isFailedToSignUp",true);
			getServletContext().getRequestDispatcher("/pages/signUp.ftl").forward(req, resp);
		}
	}
}
