package ru.itis.ruzavin.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BuildComment {
	private int id;
	private int userId;
	private int buildId;
	private String text;

	public BuildComment(int userId, int buildId, String text) {
		this.userId = userId;
		this.buildId = buildId;
		this.text = text;
	}
}
