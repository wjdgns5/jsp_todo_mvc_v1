package com.tenco.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDAO;
import com.tenco.model.UserDAOImpl;
import com.tenco.model.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/test/*")
public class TestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private TodoDAO todoDAO;
	
    public TestController() {
    	super();
    }
    
    @Override
    public void init() throws ServletException {
    	userDAO = new UserDAOImpl();
    	todoDAO = new TodoDAOImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		
		switch (action) {
		case "/byId":
			// http://localhost:8080/mvc/test/byId
			
			//userDAO.getUserById(1);
//			 userDAO.getUserByUsername("홍길동");
			// List<UserDTO> list = userDAO.getAllUsers();
			
//			UserDTO dto = UserDTO.builder().password("999").email("h@naver.com").build();
//			int count = userDAO.updateUser(dto, 3);
//			System.out.println("count : " + count);
			
		//	System.out.println(userDAO.deleteUser(4));
			
			
			break;

		default:
			break;
		}
		
	// http://localhost:8080/mvc/test/t1
		
//	TodoDTO todoDTO = todoDAO.getTodoById(1);
//	System.out.println(todoDTO.toString());
		
	//		List<TodoDTO> list = todoDAO.getTodosByUserId(1);
	//		System.out.println(list.toString());
		 
//	List<TodoDTO> todoDTO = todoDAO.getALLTodos();
//	System.out.println(todoDTO.toString());
		
	//	todoDAO.updateTodo(new TodoDTO(), 1);
	//	System.out.println(todoDAO.toString());
		
//		TodoDTO dto = new TodoDTO();
//		todoDAO.updateTodo(dto, 1);
//		System.out.println(todoDAO.toString());
		
	//	 todoDAO.deleteTodo(2, 1);
	//	 System.out.println(todoDAO.toString());
		
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
