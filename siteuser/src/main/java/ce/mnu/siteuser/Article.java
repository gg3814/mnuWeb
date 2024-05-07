package ce.mnu.siteuser;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter // 모든필드에 대한 접근자 메서드 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 생성
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long num;
	
	
	
	@Column(length = 20, nullable =  false)
	private String author;
	
	@Column(nullable = false, length = 50)
	private String title;
	
	@Column(nullable = false, length = 2048)
	private String body;
	
	public Long getNum() {return num;}
	public void setNum(Long n) {num = n;}
	
	public String getAuthor() {return author;}
	public void setAuthor(String e) {author = e;}
	
	public String getTitle() {return title;}
	public void setTitle(String e) {title = e;}
	
	public String getBody() {return body;}
	public void setBody(String n) {body = n;}
	
	/* Aticle 엔티티에 comments 필드를 추가하여 댓글 작성
	 * @OneToMany 일대다 관계 한개의 게시글에 댓글 여러개 작성
	 * @mappedBy comment 엔티티의 article 필드와 매핑
	 * @CascadeType.ALL Article 엔티티가 영구 저장될 때 연관된 모든 댓글도 함꼐 저장
	 * */
//    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();

    /* Article 엔티티에는 댓글을 추가하는 메서드인 addComment도 추가
	 * 이 메서드는 댓글을 받아서 Article에 추가하고, 댓글의 article 필드도 설정
     * 
     * */
//    public void addComment(Comment comment) {
//        comments.add(comment);
//        comment.setArticle(this);
//    }
//	
}
