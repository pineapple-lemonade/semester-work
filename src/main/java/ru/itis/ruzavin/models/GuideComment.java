package ru.itis.ruzavin.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GuideComment {
	private int id;
	private int userId;
	private int guideId;
	private String text;

	public GuideComment(int userId, int guideId, String text) {
		this.userId = userId;
		this.guideId = guideId;
		this.text = text;
	}
}
