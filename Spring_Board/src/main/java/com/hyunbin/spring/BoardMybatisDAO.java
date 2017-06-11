package com.hyunbin.spring;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
public class BoardMybatisDAO{

    @Autowired
	private SqlSessionTemplate session;
    
	
	public List<BoardVO> selectAllBoards() {
		
			return session.selectList("selectAllBoards");//selectList의 첫번째 인자는 네임스페이스명.함수id명 두번째 인자는 파라미터 값 넘기기. 
				
	}

	
	public void insertBoard(BoardVO bVo) {
		// TODO Auto-generated method stub
		
			session.insert("insertBoard",bVo);
			

			
	}

	
	public void updateReadCount(String num) {
		
			
			session.update("updateReadCount",num);

		
	}

	public BoardVO selectOneBoardByNum(String num) {
		// TODO Auto-generated method stub
		
			return session.selectOne("selectOneBoardByNum",num);
			
		
	}

	public void updateBoard(BoardVO bVo) {
		// TODO Auto-generated method stub
	
			session.update("updateBoard",bVo);


		
	}

	public BoardVO checkPassWord(String pass, String num) {
		// TODO Auto-generated method stub
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("pass", pass);
		map.put("num", num);
			return session.selectOne("checkPassWord",map);
			
	}

	public void deleteBoard(String num) {
		// TODO Auto-generated method stub
			session.delete("deleteBoard",num);


	}

	
	
}
