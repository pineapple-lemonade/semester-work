package ru.itis.ruzavin.serlvets;

import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.models.User;
import ru.itis.ruzavin.repositories.UserRepository;
import ru.itis.ruzavin.repositories.UserRepositoryJdbcImpl;
import ru.itis.ruzavin.services.SecurityService;
import ru.itis.ruzavin.services.SecurityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@WebServlet(name = "signInServlet", urlPatterns = "/signIn")
public class SignInServlet extends HttpServlet {

	private SecurityService securityService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("pages/signIn.ftl");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		securityService = new SecurityServiceImpl();

		if(securityService.signIn(req)){

		} else {
			req.setAttribute("login", req.getParameter("login"));
			req.setAttribute("isFailedToSignIn",true);
			getServletContext().getRequestDispatcher("/pages/signIn.ftl").forward(req, resp);
		}
	}
}
