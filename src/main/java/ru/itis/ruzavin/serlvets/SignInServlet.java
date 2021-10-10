package ru.itis.ruzavin.serlvets;

import ru.itis.ruzavin.jdbc.SimpleDataSource;
import ru.itis.ruzavin.models.User;
import ru.itis.ruzavin.repositories.UserRepository;
import ru.itis.ruzavin.repositories.UserRepositoryJdbcImpl;

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

	private UserRepository userRepository;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("pages/signIn.ftl");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Properties properties = new Properties();
		properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		userRepository = new UserRepositoryJdbcImpl(new SimpleDataSource(properties));
		resp.setContentType("text/html");
		String password = req.getParameter("userPass");
		String login = req.getParameter("userLogin");
		Optional<User> userByLogin = userRepository.findUserByLogin(login);
		//TODO need to process redirect and situation where we dont find user more correctly
		if(userByLogin.isPresent() && userByLogin.get().getPassword().equals(password)){
			resp.sendRedirect("index.html");
		} else {
			resp.getWriter().print("not valid");
		}
	}
}
