package ru.itis.ruzavin.servlets;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ru.itis.ruzavin.dto.UserDTO;
import ru.itis.ruzavin.helper.CloudinaryHelper;
import ru.itis.ruzavin.helper.ImageHelper;
import ru.itis.ruzavin.services.UserService;
import ru.itis.ruzavin.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/upload")
@MultipartConfig(
		maxFileSize = 5 * 1024 * 1024,
		maxRequestSize = 10 * 1024 * 1024
)
public class AvatarUploadServlet extends HttpServlet {
	private Cloudinary cloudinary;

	private UserService service;

	@Override
	public void init() throws ServletException {
		cloudinary = CloudinaryHelper.getInstance();
		service = new UserServiceImpl();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File file = ImageHelper.makeFile(request.getPart("avatar"));
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");

		String filename = "profilePhoto" + user.getId();

		Map upload = cloudinary.uploader()
				.upload(file, ObjectUtils.asMap("public_id", filename));
		String url = (String) upload.get("url");
		user.setAvatarUrl(url);
		service.updateAvatar(user);
		response.sendRedirect("/pages/profile.ftl");
	}

}
