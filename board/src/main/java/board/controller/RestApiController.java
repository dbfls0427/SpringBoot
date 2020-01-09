package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;

@RestController //json으로 반환
public class RestApiController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/api/board", method = RequestMethod.GET)
	public List<BoardDto> openBoardList()throws Exception{
		return boardService.selectBoardList();
	}
	  
	  @RequestMapping(value = "/api/board/write", method = RequestMethod.POST) 
	  public void insertBoard(@RequestBody BoardDto board)throws Exception{ 
		  boardService.insertBoard(board, null);
	  }
  
	  @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET) 
	  public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		  return boardService.selectBoardDetail(boardIdx); 
	  }
	  
	  @RequestMapping(value = "/api/board/{boardIdx}", method = {RequestMethod.PUT, RequestMethod.POST}) 
	  public void updateBoard(@RequestBody BoardDto board) throws Exception{ 
	   boardService.updateBoard(board); 
	   }
	  
	  
	  @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.DELETE) 
	  public void deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{ 
		  boardService.deleteBoard(boardIdx);
	  }
}
