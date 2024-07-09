package com.tenco.model;

import java.util.List;

public interface UserDAO {

	// 기능 설계
//	void addUser(UserDTO userDTO); // void 도 가능하다. 사용하는데 지장없다.
	
	int addUser(UserDTO userDTO);
		UserDTO getUserById(int id); // 사용자 id로 조회 
		UserDTO getUserByUsername(String username); // 사용자 이름으로 조회 
		List<UserDTO> getAllUsers(); // db 조회시 다중 행으로 나온다. 그래서 리스트를 쓴다.
		
		// 권한 (마이정보 나만) - 인증 (세션ID)
		int updateUser(UserDTO user, int principalId); // 외부에서 변경할 값을 넣어야 한다. 
		
		int deleteUser(int id); // 인증 - 세션검사 
		
	// 사용자 아이디 값으로 유저 정보 검색
	
	
}
