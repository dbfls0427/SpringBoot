package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;

@Controller
public class RestController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public ModelAndView openBoardList()throws Exception{
		ModelAndView mav = new ModelAndView("board/restBoardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		System.out.println(list);
		mav.addObject("list", list);
		
		return mav;
	}
	
	
	  @RequestMapping(value = "/board/write", method = RequestMethod.GET) 
	  public String openBoardWrite()throws Exception{
		  return "/board/restBoardWrite"; 
		  }
	  
	  @RequestMapping(value = "/board/write", method = RequestMethod.POST) 
	  public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest)throws Exception{ 
	  
	  boardService.insertBoard(board, multipartHttpServletRequest);
	  
	  return "redirect:/board"; 
	  }
  
	  @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.GET) 
	  public String openBoardDetail(@PathVariable int boardIdx, Model model) throws Exception{
	  
	  BoardDto board = boardService.selectBoardDetail(boardIdx);
	  model.addAttribute("board", board);
	  
	  return "board/restBoardDetail"; 
	  }
	  
	  @RequestMapping(value = "/board/{boardIdx}", method = {RequestMethod.PUT, RequestMethod.POST}) 
	  public String updateBoard(BoardDto board) throws Exception{ 
	   boardService.updateBoard(board); 
	   return "redirect:/board"; 
	   }
	  
	  
	  @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.DELETE) 
	  public String deleteBoard(int boardIdx) throws Exception{ 
	  boardService.deleteBoard(boardIdx); return
	  "redirect:/board"; 
	  }
	  
	  
	  
	  @RequestMapping(value = "/board/file", method = RequestMethod.GET) 
	  public void downloadBoardFile(@PathVariable int idx, @PathVariable int boardIdx, HttpServletResponse response) throws Exception{ 
	  BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
	  if(ObjectUtils.isEmpty(boardFile) == false) { 
		  String fileName =
		  boardFile.getOriginalFileName();
		  
		  byte[] files = FileUtils.readFileToByteArray(new
		  File(boardFile.getStoredFilePath()));
		  
		  response.setContentType("application/octet-stream");
		  response.setContentLength(files.length);
		  response.setHeader("Content-Disposition", "attachment; fileName=\"" +
		  URLEncoder.encode(fileName,"UTF-8")+"\";");
		  response.setHeader("Content-Transfer-Encoding", "binary");
		  
		  response.getOutputStream().write(files); response.getOutputStream().flush();
		  response.getOutputStream().close(); 
	  	} 
	  
	  }
	 
	 
}
