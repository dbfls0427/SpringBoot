package board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface jpaBoardRepository extends CrudRepository<BoardEntity, Integer> {

	//내림차순으로 뽑아주세요
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	
	@Query("SELECT file FROM BoardFileEntity file WHERE boardIdx =:boardIdx AND idx=:idx")
	BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
	
	
}
