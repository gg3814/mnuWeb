package ce.mnu.siteuser;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;


import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.data.domain.*;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import org.springframework.http.*;
import org.springframework.core.io.*;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping(path="/siteuser")
public class SiteUserController {
	@Autowired
	private SiteUserRepository userRepository;
	
	@GetMapping(value = {"","/"})
	public String start(Model model) {
		return "start";
	}
	
	@GetMapping(path="/signup")
	public String signup(Model model) {
		model.addAttribute("siteuser", new SiteUser());
		return "new_user";
	}
	
	@PostMapping(path="/signup")
	public String signup(@ModelAttribute SiteUser user, Model model) {
		userRepository.save(user);
		model.addAttribute("name", user.getName());
		return "signup_deon"; // 오타로 제목 잘못설정
	}
	
	@PostMapping(path="/find")
	public String findUser(@RequestParam(name="email") String email, HttpSession session, Model model, RedirectAttributes rd) {
		SiteUser user = userRepository.findByEmail(email);
		if(user != null) {
			model.addAttribute("user", user);
			return "find_done";
		}
		rd.addFlashAttribute("reason", "wrong email");
		return "redirect:/error";
	}
	
	@GetMapping(path="/find")
	public String find() {
		return "find_user";
	}
	
	@PostMapping(path = "/login")
	public String loginUser(@RequestParam(name="email") String email, @RequestParam(name="passwd") String passwd,
			HttpSession session, RedirectAttributes rd) {
		SiteUser user = userRepository.findByEmail(email);
		if(user != null) {
			if(passwd.equals(user.getPasswd())) {
				session.setAttribute("email", email);
				return "login_done";
			}
		}
		rd.addFlashAttribute("reason","wrong password");
		return "redirect:/error";
	}
	
	@GetMapping(path="/login")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return ""; // 로그아웃하면 처음 페이지로 이동
		}
	
	// 게시판 관련
	@Autowired
	private ArticleRepository articleRepository;
	
	@GetMapping(path = "/bbs/write") // 글 쓰기 페이지로 이동
	public String bbsForm(Model model) {
		model.addAttribute("article", new Article());
		return "new_article";
		
	}
	
	@PostMapping(path = "/bbs/add") // 글을 DB에 저장
	public String addArticle(@ModelAttribute Article article, Model model) {
		articleRepository.save(article);
		model.addAttribute("article", article);
		return "saved";
	}
	
	@GetMapping(path = "/bbs") // 전체 글 목록 보기
	public String getAllArticles(@RequestParam(name = "pno", defaultValue = "0")
		String pno, Model model, HttpSession session, RedirectAttributes rd) {
		String email = (String) session.getAttribute("email"); // 로그인 확인
		if(email == null) {
			rd.addFlashAttribute("reason","login required");
			return "redirect:/error"; // 로그인 안 했으면, 에러 페이지로
		}
		Integer pageNo = 0; // 페이지 번호
		if(pno != null) {
			pageNo = Integer.valueOf(pno);
		}
		Integer pageSize = 2; //페이지 크기
		Pageable paging = PageRequest.of(pageNo, pageSize, org.springframework.data.domain.Sort.Direction.DESC, "num");  // sort 오류로 저렇게 해야함
		Page<ArticleHeader> data = articleRepository.findArticleHeaders(paging);
		model.addAttribute("articles", data);
		return "articles";
	}
	
	@GetMapping(path = "/read")
	public String readArticle(@RequestParam(name="num") String num, HttpSession session, Model model) {
		Long no = Long.valueOf(num);
		Article article = articleRepository.getReferenceById(no);
		model.addAttribute("article", article);
		return "article";
	}
	
	// 파일 업로드
	@PostMapping(path="/upload")
	public String upload(@RequestParam MultipartFile file, Model model) throws IllegalStateException, IOException{
		if (!file.isEmpty()) {
			String newName = file.getOriginalFilename();
			newName = newName.replace(' ', '_');
			FileDto dto = new FileDto(newName, file.getContentType());
			File upfile = new File(dto.getFileName());
			file.transferTo(upfile);
			model.addAttribute("file", dto);
		}
		return "result";
	}
	
	@GetMapping(path="/upload")
	public String visitUpload() {
		return "uploadForm";
	}
	
	@Value("${spring.servlet.multipart.location}")
	String base; //파일 저장 폴더
	// 파일 다운로드
	@GetMapping(path="/download")
	public ResponseEntity<Resource> download(@ModelAttribute FileDto dto) throws IOException{
		Path path = Paths.get(base + "/" + dto.getFileName());
		String contentType = Files.probeContentType(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename(dto.getFileName(),StandardCharsets.UTF_8).build());
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		Resource rsc = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(rsc,headers, HttpStatus.OK);
	}
	
//	public Article getArticle(Long id) {
//		Optional<Article> article = this.articleRepository.findById(id);
//		if(article.isPresent()) {
//			Article article1 = Article.get();
//			article1.setView(article1.getView()+1);
//			this.articleRepository.save(article1);
//			return article1;
//		}else {
//			throw new DataNotFoundException("article not found");
//		}
//	}

}
