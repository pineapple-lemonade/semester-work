package ru.itis.ruzavin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GuideCommentDTO {
	private final int id;
	private final UserDTO user;
	private final int guideId;
	private final String text;


}
