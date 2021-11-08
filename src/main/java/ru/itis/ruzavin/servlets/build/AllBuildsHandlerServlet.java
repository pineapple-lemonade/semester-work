package ru.itis.ruzavin.servlets.build;

import ru.itis.ruzavin.dto.BuildDTO;
import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.helper.HTMLHelper;
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

@WebServlet(urlPatterns = "/allBuildsHandler")
public class AllBuildsHandlerServlet extends HttpServlet {

	private BuildService buildService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		if (title == null){
			List<BuildDTO> builds = buildService.findAll();
			mapBuilds(resp, builds);
			return;
		}
		List<BuildDTO> builds = buildService.findAllByTitle(title);
		mapBuilds(resp, builds);
	}

	private void mapBuilds(HttpServletResponse resp, List<BuildDTO> builds) throws IOException, ServletException {
		builds = builds.stream()
				.map(build -> new BuildDTO(build.getId(), build.getUserNick(), build.getTitle(),
						TextHelper.editText(build.getText()), build.getPhotoUrl(), build.getData()))
				.collect(Collectors.toList());

		Collections.reverse(builds);

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(HTMLHelper.makeBuildHTML(builds));
	}

	@Override
	public void init() throws ServletException {
		buildService = new BuildServiceImpl();
	}
}
