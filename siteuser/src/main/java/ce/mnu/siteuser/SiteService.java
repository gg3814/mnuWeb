package ce.mnu.siteuser;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SiteService {
	private final SiteUserRepository siteUserRepository;
	private final ArticleRepository ariArticleRepository;
	
	public List<Article> getList(){
		return this.ariArticleRepository.findAll();
	}
}
