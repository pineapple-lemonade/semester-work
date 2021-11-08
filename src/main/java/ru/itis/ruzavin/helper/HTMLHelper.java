package ru.itis.ruzavin.helper;

import ru.itis.ruzavin.dto.BuildDTO;
import ru.itis.ruzavin.dto.GuideDTO;
import ru.itis.ruzavin.dto.UserDTO;

import java.util.List;

public class HTMLHelper {
	public static String makeGuideHTML(List<GuideDTO> guides) {
		StringBuilder result = new StringBuilder();

		if(guides.size() == 0) {
			result.append("<p class=\"lead\">No guides that matches these requirements</p>");
		} else {
			for(GuideDTO guide : guides) {
				result.append("<a href=\"/guideInfo?id=").append(guide.getId()).append("\">")
						.append("<div class=\"alert alert-dark\" role=\"alert\">")
						.append("<h2>").append(guide.getTitle()).append("</h2>")
						.append("<div>").append(guide.getText()).append("</div>")
						.append("<br>")
						.append("<img src=\"").append(guide.getPhotoUrl()).append("\" width=\"665\" height=\"350\">")
						.append("<br>").append("<br>")
						.append("<div><small class=\"text-muted\">").append(guide.getUserNick())
						.append(" ").append(guide.getData()).append("</small></div>")
						.append("<div><small class=\"text-muted\">Guide ").append(guide.getId()).append("</small></div>")
						.append("</div>").append("</a>");
			}
		}

		return result.toString();
	}

	public static String makeBuildHTML(List<BuildDTO> articles) {
		StringBuilder result = new StringBuilder();

		if(articles.size() == 0) {
			result.append("<p class=\"lead\">No builds that matches requirements</p>");
		} else {
			for(BuildDTO build : articles) {
				result.append("<a href=\"/buildInfo?id=").append(build.getId()).append("\">")
						.append("<div class=\"alert alert-dark\" role=\"alert\">")
						.append("<h2>").append(build.getTitle()).append("</h2>")
						.append("<div>").append(build.getText()).append("</div>")
						.append("<br>")
						.append("<img src=\"").append(build.getPhotoUrl()).append("\" width=\"665\" height=\"350\">")
						.append("<br>").append("<br>")
						.append("<div><small class=\"text-muted\">").append(build.getUserNick())
						.append(" ").append(build.getData()).append("</small></div>")
						.append("<div><small class=\"text-muted\">Build ").append(build.getId()).append("</small></div>")
						.append("</div>").append("</a>");
			}
		}

		return result.toString();
	}

	public static String makeUserHTML(List<UserDTO> users) {
		StringBuilder result = new StringBuilder();

		if(users.size() == 0) {
			result.append("<p class=\"lead\">No users with such nickname</p>");
		} else {
			for(UserDTO user : users) {
				result.append("<a href=\"/userInfo?id=").append(user.getId()).append("\">")
						.append("<div class=\"alert alert-dark\" role=\"alert\">")
						.append("<table>").append("<tr>")
						.append("<td><img alt=\"user_img\" src=\"").append(user.getAvatarUrl())
						.append("\" width=\"50\" height=\"50\" class=\"rounded-circle\"></td>")
						.append("<td>").append("<h3>")
						.append("<strong>").append(user.getNick()).append("</strong>")
						.append("</h3></td></tr></table></div></a>");
			}
		}

		return result.toString();
	}

}
