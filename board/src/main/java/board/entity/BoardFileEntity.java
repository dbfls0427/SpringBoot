package board.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_jpa_file")
@NoArgsConstructor
@Data
public class BoardFileEntity {

	@Id
	@Column(name = "boardIdx")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "f_file_seq2")
	@SequenceGenerator(sequenceName = "f_file_seq2", allocationSize = 1, name = "f_file_seq2")
	private int idx;
	
	@Column(nullable = false)
	private String originalFileName;
	
	@Column(nullable = false)
	private String storedFilePath;
	
	@Column(nullable = false)
	private long fileSize;
	
	@Column(nullable = false)
	private String creatorId;
	
	@Column(nullable = false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updaterId;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private String updatedDatetime;
}
