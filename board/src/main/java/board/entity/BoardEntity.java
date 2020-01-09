package board.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import board.dto.BoardFileDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {

	@Id
	@Column(name = "boardIdx")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq2")
	@SequenceGenerator(sequenceName = "board_seq2", allocationSize = 1, name = "board_seq2")
	private int boardIdx;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String contents;
	
	@Column(nullable = false)
	private int hitCnt;
	
	@Column(nullable = false)
	private String creatorId;
	
	@Column(nullable = false)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updaterId;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private String updatedDatetime;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "boardIdx")
	private List<BoardFileEntity> fileList;
}
