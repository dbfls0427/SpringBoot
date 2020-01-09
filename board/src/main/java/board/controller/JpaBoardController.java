package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.service.JpaBoardService;

@Controller
public class JpaBoardController {

	@Autowired
	private JpaBoardService jpaBoardService;
	
	@RequestMapping(value="/jpa/board", method = RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("board/jpaBoardList");
		
		List<BoardEntity> list = jpaBoardService.selectBoardList();		
		mv.addObject("list", list);
		
		return mv;
	}
	
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/jpaBoardWrite";
	}
	
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		jpaBoardService.saveBoard(board, multipartHttpServletRequest);
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
		
		BoardEntity board = jpaBoardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		System.out.println("board: " + board);
		return mv;
	}
	
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.POST)
	public String updateBoard(BoardEntity board) throws Exception{
		jpaBoardService.saveBoard(board, null);
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/del/{boardIdx}", method=RequestMethod.POST)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		jpaBoardService.deleteBoard(boardIdx);
		return "redirect:/jpa/board";
	}
	
	@RequestMapping(value="/jpa/board/file", method=RequestMethod.GET)
	public void downloadBoardFile(@RequestParam int boardIdx, @RequestParam int idx, HttpServletResponse response) throws Exception{
		BoardFileEntity boardFile = jpaBoardService.selectBoardFileInformation(boardIdx, idx);
		
		byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(files.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFile.getOriginalFileName(),"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		response.getOutputStream().write(files);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		/*
		 * if(ObjectUtils.isEmpty(boardFile) == false) { String fileName =
		 * boardFile.getOriginalFileName();
		 * 
		 * byte[] files = FileUtils.readFileToByteArray(new
		 * File(boardFile.getStoredFilePath()));
		 * 
		 * response.setContentType("application/octet-stream");
		 * response.setContentLength(files.length);
		 * response.setHeader("Content-Disposition", "attachment; fileName=\"" +
		 * URLEncoder.encode(fileName,"UTF-8")+"\";");
		 * response.setHeader("Content-Transfer-Encoding", "binary");
		 * 
		 * response.getOutputStream().write(files); response.getOutputStream().flush();
		 * response.getOutputStream().close(); }
		 */
	}
	
}









