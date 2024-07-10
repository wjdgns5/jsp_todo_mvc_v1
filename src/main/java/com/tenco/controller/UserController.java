package com.tenco.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.tenco.model.UserDAO;
import com.tenco.model.UserDAOImpl;
import com.tenco.model.UserDTO;

// 주소 설계 
// http://localhost:8080/mvc/user
// http://localhost:8080/mvc/xxx <-- xxx 하위까지 다 받아진다.
@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public UserController() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	userDAO = new UserDAOImpl();
    	
    }
    
    // GET 방식으로 들어올 때
    // http://localhost:8080/mvc/user/signUp
    // http://localhost:8080/mvc/user/signIn
    
    // 로그인 기능 요청 (자원에 요청 -- GET 방식 예외적인 처리_ 보안때문에)
    // post 요청 시 - 로그인 기능 구현, 회원가입 기능 구현
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// user/signIn --> 로그인 페이지
		// user/siginUp --> 회원가입 페이지 
		
		String action = request.getPathInfo();
		System.out.println("action : " + action);
		
		switch (action) {
		case "/signIn":
			// 로그인 페이지로 보내는 동작 처리
			// /WEB-INF 는 보안폴더
			request.getRequestDispatcher("/WEB-INF/views/signIn.jsp").forward(request, response);
			break;
		case "/signUp":
			// 회원 가입 페이지로 보내는 동작 처리
			request.getRequestDispatcher("/WEB-INF/views/signUp.jsp").forward(request, response);
			break;
		
		
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}
	
	
	// /WEB-INF 는 보안폴더
	// http://localhost:8080/mvc/views/todoForm.jsp --> 서블릿 거치지 않고 바로 jsp로 간다.
	// WEB-INF 안에 views 폴더를 넣으면 주소값을 넣어도 접속할 수 없다.
	
	// http://localhost:8080/mvc/user/signUp
    // http://localhost:8080/mvc/user/signIn
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getPathInfo();
		System.out.println("action : " + action);
		
		switch (action) {
		case "/signIn":
			signIn(request, response); // ctr + 1 번 클릭 후 메서드 생성
			// 로그인 페이지로 보내는 동작 처리
			// /WEB-INF 는 보안폴더
			break;
		case "/signUp":
			// 회원 가입 페이지로 보내는 동작 처리
			signUp(request, response); // ctr + 1 번 클릭 후 메서드 생성
			break;
			
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
		
	}
	
	// 로그인 처리 기능
	/* @param request
	 * @param response
	*/
	private void signIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// URL, 인증검사, 유효성 검사, 서비스 로직 DAO --> 전달, 뷰를 호출
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// 유효성 검사
		if(username == null || password.trim().isEmpty()) {
			response.sendRedirect("signIn?message=invaild");
			return; 
		}
		
		
		UserDTO user = userDAO.getUserByUsername(username);
		// null 이 떨어지면 --> 회원가입이 되어있지 않는 사용자이다.
		// 빠른 평가 기법
		if(user != null && user.getPassword().equals(password)) {
			
			
			// 세션 생성
			HttpSession session = request.getSession();
			// 세션 키 - 값 지정 (유저정보 모두를 넣음)
			session.setAttribute("principal", user);
			
			response.sendRedirect("/mvc/todo/todoForm");
			//
			// TODO -  로그인 --> todoFrom 화면 이동 처리 todoForm 화면 이동 기능 추가 예정
			// response.sendRedirect("");
			System.out.println("로그인 처리 완료");
		} else {
			response.sendRedirect("signIn?message=invalid");
		}
		
		// 비밀번호 비교 --> dto.getPassword(); 
		
	}

	/*
	 * 회원 가입 기능
	 * @param request
	 * @param response
	 */
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 인증 검사 필요 없는 기능
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		// 방어적 코드 작성 (username)
		if(username == null || username.trim().isEmpty()) {
			request.setAttribute("errorMessage", "사용자 이름을 입력하시오.");
			request.getRequestDispatcher("/WEB-INF/views/signUp.jsp").forward(request, response);
			return;
		}
		
		// result.jsp?message
		
		// 방어적 코드 (password) - 생략
		
		// 방어적 코드 (email) - 생략
		
		UserDTO userDTO = UserDTO.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
		
	//	int resultRowCount = 0;
		int resultRowCount = userDAO.addUser(userDTO);
		System.out.println("resultRowCount : " + resultRowCount);
		
		if(resultRowCount == 1) {
			response.sendRedirect("signIn?message=success");
		} else {
			System.out.println("11111111111111111111111"); 
			response.sendRedirect("signUp?message=error"); // 상대경로
		}
		
		
	}

}
