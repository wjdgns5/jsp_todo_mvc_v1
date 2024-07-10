package com.tenco.controller;

import java.io.IOException;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//.../mvc/todo/xxx

@WebServlet("/todo/*")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TodoDAO todoDAO;
       
    public TodoController() {
    	todoDAO = new TodoDAOImpl(); // 업 캐스팅 된 상태
    }
    
    // http://localhost:8080/mvc/todo/form
    // http://localhost:8080/mvc/todo/todoform (권장 x) (지금은 이거 사용)
    //  ex) http://localhost:8080/mvc/todo/formzzz 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("action 확인 : " +action);
		switch(action) {
		case "/todoForm":
			todoFormPage(request, response); // ctr + 1 번 클릭 후 메서드 생성
			break;
		
		case "/list":
			todoListPage(request, response); // ctr + 1 번 클릭 후 메서드 생성
	
		default:
			break;
		
		}
	}
	// http://localhost:8080/mvc/todo/list
	private void todoListPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 인증 검사
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO)session.getAttribute("principal"); // 다운캐스팅
		request.getContextPath();
		System.out.println("request.getContextPath() : " +request.getContextPath());
		
		if(principal == null) {
			response.sendRedirect( request.getContextPath() + "/user/sigIn?message=invaild");
			return;
		}
		
		// todoList.jsp 페이지로 내부에서 이동 처리
		request.getRequestDispatcher("/WEB-INF/views/todoList.jsp").forward(request, response);
		
		
	}


	// http://localhost:8080/mvc/user/signIn
	private void todoFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 로그인 한 사용자만 접근을 허용하도록 설계
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO)session.getAttribute("principal"); // 다운캐스팅
		
		// 인증검사
		if(principal == null) {
			// 로그인을 안한 상태
			response.sendRedirect("/mvc/user/signIn?message=invalid");
			return;
		} 
		
		request.getRequestDispatcher("/WEB-INF/views/todoForm.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// todo 추가 예정
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO)session.getAttribute("principal");
		// Princiap -- null 이라면 ---> 로그인 페이지로 이동 처리
		todoDAO.addTodo(new TodoDTO(), principal.getId());
	}

}
