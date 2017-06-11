package com.hyunbin.spring;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final int listMax=10;//한 페이지에 띄울 list 갯수
	private static final int pageMax=10;//페이지 그룹에서 페이지의 갯수
	@Autowired
	BoardMybatisDAO bDao;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "index";
	}
	
	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String boardList(@RequestParam String pageNum, Model model) {
		
		
		if(pageNum==null){
			pageNum="1";
		}
		
		int currentNum=Integer.parseInt(pageNum);
		int startRow = (currentNum-1)*listMax+1;
		int endRow = currentNum*listMax;
		
		int totalCnt=0;
		
		List<BoardVO> boardList=bDao.selectAllBoards();
		totalCnt=boardList.size();
		List<BoardVO> tmeList = new ArrayList<BoardVO>();
		
		
		if(totalCnt>0){
		int i=0;	
		if(totalCnt<endRow)
			endRow=totalCnt;
			
			while(true){// listMax의 크기만큼 받아옴.
				if(i>=(endRow-(currentNum-1)*10))
					break;	
			tmeList.add(boardList.get(startRow+i-1));
				i++;
			}
		}else
			tmeList = null;//////한페이지에 등록 할 수있는 리스트의 형태
		int pageGroupCount = totalCnt/(listMax*pageMax)+ ((totalCnt%listMax*pageMax)==0?0:1);//페이지 그룹의 갯수
		
		int pageGroupNum = (int)Math.ceil((double)currentNum/pageMax);//현재 페이지의  페이지 그룹 번호
		
		model.addAttribute("pageGroupNum", pageGroupNum);
		model.addAttribute("pageGroupCount", pageGroupCount);
		model.addAttribute("pageMax", pageMax);
		model.addAttribute("listMax", listMax );
		model.addAttribute("tmeList", tmeList);
		model.addAttribute("currentNum",currentNum);
		model.addAttribute("totalCnt", totalCnt);
		
				
		return "boardList";
	}
	//write 폼
		@RequestMapping(value = "/writeform", method = RequestMethod.GET)
		public String boardWriteForm(Model model) {
			
			//페이지이동구간.
			
			return "boardWrite";
		}
		//write
		@RequestMapping(value = "/write", method = RequestMethod.POST)
		public String boardWrite(BoardVO bVo) {
			
			bDao.insertBoard(bVo);
			
			//sql insert 하기. 
			
			return "index";
		}
		//updateform
		@RequestMapping(value = "/updateform", method = RequestMethod.GET)
		public String boardUpdateForm(@RequestParam String num,Model model) {
			
			//페이지 이동구간
			bDao.updateReadCount(num);
			BoardVO bVo = bDao.selectOneBoardByNum(num);
			model.addAttribute("board", bVo);
			
			return "boardUpdate";
		}
		//update
		@RequestMapping(value = "/update", method = RequestMethod.POST)
		public String boardUpdate(BoardVO bVo) {
			
			//sql update시키기
			
			bDao.updateBoard(bVo);
			
			return "index";
		}
		
		//view 상세보기
		@RequestMapping(value = "/view", method = RequestMethod.GET)
		public String boardView(@RequestParam String pageNum,@RequestParam String num,Model model) {
					
					//select 로 불러오기

						
			bDao.updateReadCount(num);
			
			BoardVO bVo= bDao.selectOneBoardByNum(num);
			model.addAttribute("board", bVo);
			model.addAttribute("pageNum",pageNum);
					return "boardView";
		}
			
		//check form
		@RequestMapping(value = "/checkform", method = RequestMethod.GET)
		public String boardCheckForm(@RequestParam String num,Model model) {
				//slect 로 불러오기
					model.addAttribute("num", num);
					return "boardCheckPass";
		}
				
		//check
		@RequestMapping(value = "/check", method = RequestMethod.GET)
		public String boardCheck(@RequestParam String num,@RequestParam String pass,Model model) {
					
					//select 로 불러오기			
			BoardVO bVo= bDao.selectOneBoardByNum(num);
			model.addAttribute("num",num);
			if(bVo.getPass().equals(pass)){
				
				return "checkSuccess";
				
			}else{
				
				model.addAttribute("message", "비밀번호가 틀렸습니다.");
				return "boardCheckPass";

			}
		}
		//delete
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public String boardDelete(@RequestParam String num,Model model) {
			
			bDao.deleteBoard(num);
			
			return "index";
		
		}
			
				
}
