package ce.mnu.siteuser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity{ // 시간 관리를 위해서 BaseEntity 상속, BaseEntity 추가해야함
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length =20, nullable=false)
	private String commentWriter;
	
	@Column
	private String commentContents;
	
	/* Article:Comment = 1:N */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private BoardEntity boardEntity;
	
}
