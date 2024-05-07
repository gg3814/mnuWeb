package ce.mnu.siteuser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	@PostMapping("/save")
	public @ResponseBody String save(@ModelAttribute ConmmentDTO commentDTO) {
		System.out.println("commentDTO =" + commentDTO);
		return "요청 성공";
	}
}
