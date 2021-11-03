package ru.itis.ruzavin.servlets;

import ru.itis.ruzavin.dto.BuildCommentDTO;
import ru.itis.ruzavin.dto.BuildDTO;
import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.models.BuildComment;
import ru.itis.ruzavin.services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/buildInfo")
public class BuildInfoServlet extends HttpServlet {

	private BuildService buildService;

	private UserService userService;

	private BuildCommentService buildCommentService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDTO userNow = (UserDTO) req.getAttribute("user");
		BuildDTO build = buildService.findById(Integer.parseInt(req.getParameter("id")));
		UserDTO user = userService.findUserByNick(build.getUserNick());
		List<BuildCommentDTO> comments =
				buildCommentService.findAllByBuildId(Integer.parseInt(req.getParameter("id")));

		req.setAttribute("build", build);
		req.setAttribute("u", null);
		req.setAttribute("author", user);
		req.setAttribute("isComments", comments);
		req.setAttribute("comments", comments);
		req.setAttribute("userNow", userNow);

		req.getRequestDispatcher("/pages/buildInfo.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDTO user = (UserDTO) req.getAttribute("user");

		String text = req.getParameter("comment");
		int userId = user.getId();

		BuildComment comment = new BuildComment(userId, Integer.parseInt(req.getParameter("id")), text);
		buildCommentService.save(comment);

		String redirect = "/buildInfo?id=" + req.getParameter("id");
		resp.sendRedirect(redirect);
	}

	@Override
	public void init() throws ServletException {
		buildService = new BuildServiceImpl();
		userService = new UserServiceImpl();
		buildCommentService = new BuildCommentServiceImpl();
	}
}
