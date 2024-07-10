package com.tenco.model;

import java.util.List;

public interface TodoDAO {
	
	// 저장 기능 
	void addTodo(TodoDTO dto, int principalId);
	TodoDTO getTodoById(int id);
	
	// 사용자 아이디 기준으로 출력 todoList를 뽑는다.
	List<TodoDTO> getTodosByUserId(int userId);
	
	// 전체 리스트 출력
	List<TodoDTO> getALLTodos();
	
	// 사용자 글 업데이트
	void updateTodo(TodoDTO dto, int principalId);
	
	// 사용자 글 삭제
	void deleteTodo(int id, int principalId);

}
