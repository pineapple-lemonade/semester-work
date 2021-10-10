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
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name = "signUpServlet", urlPatterns = "/signUp")
public class SignUpServlet extends HttpServlet {

	private UserRepository userRepository;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("pages/signUp.ftl");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Properties properties = new Properties();
		properties.load(new FileReader("D:\\PROJECTS\\semester-work-web-project\\src\\main\\resources\\application.properties"));
		userRepository = new UserRepositoryJdbcImpl(new SimpleDataSource(properties));
		resp.setContentType("text/html");
		String nick = req.getParameter("userNick");
		String email = req.getParameter("userEmail");
		String password = req.getParameter("userPass");
		String login = req.getParameter("userLogin");
		userRepository.save(new User(null, nick, email, login, password));
	}
}
