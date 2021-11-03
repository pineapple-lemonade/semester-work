package ru.itis.ruzavin.servlets;

import ru.itis.ruzavin.dto.BuildDTO;
import ru.itis.ruzavin.helper.TextHelper;
import ru.itis.ruzavin.services.BuildService;
import ru.itis.ruzavin.services.BuildServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/allBuilds")
public class AllBuildsServlet extends HttpServlet {

	private BuildService buildService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BuildDTO> builds = buildService.findAll();
		mapBuildsList(req, resp, builds);
	}

	private void mapBuildsList(HttpServletRequest req, HttpServletResponse resp, List<BuildDTO> builds) throws ServletException, IOException {
		builds = builds.stream()
				.map(article -> new BuildDTO(article.getId(), article.getUserNick(), article.getTitle(),
						TextHelper.editText(article.getText()), article.getPhotoUrl(), article.getData()))
				.collect(Collectors.toList());
		Collections.reverse(builds);
		req.setAttribute("builds", builds);

		req.getRequestDispatcher("/pages/allBuilds.ftl").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		List<BuildDTO> builds = buildService.findAllByTitle(title);
		mapBuildsList(req, resp, builds);
	}

	@Override
	public void init() throws ServletException {
		buildService = new BuildServiceImpl();
	}
}
