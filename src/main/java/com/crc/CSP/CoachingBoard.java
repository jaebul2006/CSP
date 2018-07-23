package com.crc.CSP;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crc.CSP.bean.CoachingBoardVO;
import com.crc.CSP.service.BoardPager;
import com.crc.CSP.service.CoachingBoardService;

@Controller
@RequestMapping("/CoachingBoard/*")
public class CoachingBoard {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	CoachingBoardService boardService;
	
	@Resource(name="uploadPath")
	String uploadPath;
	
	//@Autowired
	//private ServletContext svl_context;
	
	@RequestMapping("CoachingBoardListHome")
	public String list(@RequestParam(defaultValue="title")String searchOption, @RequestParam(defaultValue="")String keyword,
						@RequestParam(defaultValue="1")int curPage) throws Exception
	{
		return "CoachingBoardListHome";
	}
	
	@RequestMapping("CoachingBoardList")
	public ModelAndView list(@RequestParam(defaultValue="title")String searchOption, @RequestParam(defaultValue="")String keyword,
						@RequestParam(defaultValue="1")int curPage, @RequestParam("id")String user_id, @RequestParam("content_type")String content_type) throws Exception
	{
		logger.info("user_id=> {}", user_id);
		
		int count = boardService.countArticle(searchOption, keyword, user_id, content_type);
		logger.info("===================================================================================");
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();
		System.out.println(start + "," + end);
		
		List<CoachingBoardVO> list = boardService.listAll(start, end, searchOption, keyword, user_id, content_type);
		logger.info("{}", list.size());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("boardPager", boardPager);
		map.put("content_type", content_type);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("CoachingBoardList");
		return mav;
		
		/*
		int count = boardService.TcountArticle(searchOption, keyword, content_type);
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();
		
		List<CoachingBoardVO> list = boardService.TlistAll(start, end, searchOption, keyword, content_type);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("boardPager", boardPager);
		map.put("content_type", content_type);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("TCoachingBoardList");
		return mav;
		*/
	}
	
	
	@RequestMapping(value="CoachingBoardWrite", method=RequestMethod.GET)
	public ModelAndView write(@RequestParam("content_type")String content_type)
	{
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("content_type", content_type);
		ModelAndView mav = new ModelAndView();;
		mav.addObject("map", map);
		mav.setViewName("CoachingBoardWrite");
		//return "CoachingBoardWrite";
		return mav;
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute CoachingBoardVO vo, HttpSession session) throws Exception
	{
		// 절대경로상에 특정 동영상파일 썸네일 추출은 일단 가능
		// 업로드된 영상파일의 경로를 기준으로 썸네일 뽑아내기 시도 ㄱㄱ
		logger.info("파일패스:{}", vo.getFileName());
		
		/*String ff_path = "C:\\ffmpeg\\bin\\";
		
		String temp = vo.getFileName().replace('/', '\\');
		logger.info(temp);
		
		logger.info("가즈아{}", uploadPath + temp);
			
		Runtime run = Runtime.getRuntime();
		
		String command = ff_path + "ffmpeg.exe -ss 00:00:01 -t 0.4 -i " + uploadPath + temp + " -r 1 "
				+ "-aspect 16:9 -qscale:v 2 -f image2 " + uploadPath + temp + "Thumbnail-%d.jpg"; 
		
		
		try {
			run.exec("cmd.exe chcp 65001");
			run.exec(command);
		}
		catch(Exception e) {
			logger.info("동영상추출실패");
			e.printStackTrace();
		}*/
		
		String title = new String(vo.getTitle().getBytes("8859_1"), "UTF-8");
		String content = new String(vo.getContent().getBytes("8859_1"), "UTF-8");
		String writer = new String(vo.getWriter().getBytes("8859_1"), "UTF-8");
		String content_type = new String(vo.getContent_type().getBytes("8859_1"), "UTF-8");
		
		// 로그인 시스템과 통합후에 윗라인 적용. 일단은 테스트
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWriter(writer);
		vo.setFileName(vo.getFileName());
		vo.setContent_type(content_type);
		boardService.create(vo);
		return "redirect:CoachingBoardListHome";
	}
	
	@RequestMapping(value="CoachingBoardView", method=RequestMethod.GET)
	public ModelAndView view(@RequestParam int bno, @RequestParam int curPage, @RequestParam String searchOption, 
			@RequestParam String keyword, HttpSession session) throws Exception
	{
		boardService.increaseViewcnt(bno, session);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("CoachingBoardView");
		mav.addObject("dto", boardService.read(bno));
		mav.addObject("curPage",  curPage);
		mav.addObject("searchOption",  searchOption);
		mav.addObject("keyword",  keyword);
		logger.info("mav:", mav);
		return mav;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	public String update(@ModelAttribute CoachingBoardVO vo) throws Exception
	{
		boardService.update(vo);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String delete(@RequestParam int bno) throws Exception
	{
		boardService.delete(bno);
		return "redirect:list";
	}
	
	@RequestMapping("/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno")int bno) throws Exception
	{
		return boardService.getAttach(bno);
	}
	
	@RequestMapping(value="UpdateThumbnail", method=RequestMethod.GET)
	public String UpdateThumbnail()
	{
		return "UpdateThumbnail";
	}
	
	@RequestMapping("TCoachingBoardListHome")
	public String TCoachingBoardListHome(@RequestParam(defaultValue="title")String searchOption, @RequestParam(defaultValue="")String keyword,
						@RequestParam(defaultValue="1")int curPage) throws Exception
	{
		return "TCoachingBoardListHome";
	}
	
	@RequestMapping("TCoachingBoardList")
	public ModelAndView TCoachingBoardList(@RequestParam(defaultValue="title")String searchOption, @RequestParam(defaultValue="")String keyword,
						@RequestParam(defaultValue="1")int curPage, @RequestParam("content_type")String content_type) throws Exception
	{
		int count = boardService.TcountArticle(searchOption, keyword, content_type);
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();
		
		List<CoachingBoardVO> list = boardService.TlistAll(start, end, searchOption, keyword, content_type);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("boardPager", boardPager);
		map.put("content_type", content_type);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("TCoachingBoardList");
		return mav;
	}
	
	@RequestMapping(value="TCoachingBoardView", method=RequestMethod.GET)
	public ModelAndView TCoachingBoardView(@RequestParam int bno, @RequestParam int curPage, @RequestParam String searchOption, 
			@RequestParam String keyword, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("TCoachingBoardView");
		mav.addObject("dto", boardService.read(bno));
		mav.addObject("curPage",  curPage);
		mav.addObject("searchOption",  searchOption);
		mav.addObject("keyword",  keyword);
		logger.info("mav:", mav);
			
		return mav;
	}
	
	// 스트리밍 서버단 소스지만 비디오 태그의 경로만 지정해주면 나와서 밑에 함수는 무쓸모인듯? ㄷㄷ
	@RequestMapping(value="CoachingBoardStreaming", method=RequestMethod.GET)
	private void ShowStream(@PathVariable("file_name")String file_name, HttpServletResponse response, HttpServletRequest request) throws IOException
	{
		RandomAccessFile randomFile = new RandomAccessFile(new File(uploadPath, file_name), "r");
		long rangeStart = 0;
		long rangeEnd = 0;
		boolean isPart = false;
		
		try {
			long movieSize = randomFile.length();
			
			String range = request.getHeader("range");
			logger.debug("range: {}", range);
			if(range != null) {
				if(range.endsWith("-")) {
					range = range + (movieSize - 1);
				}
				int idxm = range.trim().indexOf("-");
				rangeStart = Long.parseLong(range.substring(6, idxm));
				rangeEnd = Long.parseLong(range.substring(idxm + 1));
				if(rangeStart > 0) {
					isPart = true;
				}
			}
			else {
				rangeStart = 0;
				rangeEnd = movieSize - 1;
			}
			
			long partSize = rangeEnd - rangeStart + 1;
			logger.debug("accepted range: {}", rangeStart + "-" + rangeEnd + "/" + partSize + "isPart:" + isPart);
			response.reset();
			response.setStatus(isPart?206:200);
			response.setContentType("video/mp4");
			response.setHeader("Content-Range", "bytes" + rangeStart + "-" + rangeEnd + "/" + movieSize);
			response.setHeader("Accept-Range", "bytes");
			response.setHeader("Content-Length", "" + partSize);
			
			OutputStream out = response.getOutputStream();
			randomFile.seek(rangeStart);
			int bufferSize = 8 * 1024;
			byte[] buf = new byte[bufferSize];
			do {
				int block = partSize > bufferSize ? bufferSize : (int)partSize;
				int len = randomFile.read(buf, 0, block);
				out.write(buf, 0, len);
				partSize -= block;
			}while(partSize > 0);
			
		}catch(IOException e) {
			logger.debug("전송이 취소되었음");
		}finally {
			randomFile.close();
		}
	}
	
	
}
