package ce.mnu.siteuser;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConmmentDTO {
	private Long id; // 댓글 아이디 값
	private String commentWriter;
	private String commentContens;
	private	Long articleId; // 게시글 넘버
	private LocalDateTime commentCreatedTime;
}
