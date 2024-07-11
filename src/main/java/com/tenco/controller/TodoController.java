package com.tenco.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
		
		// 로그인 한 사용자만 접근을 허용하도록 설계
		HttpSession session = request.getSession();
		UserDTO principal = (UserDTO)session.getAttribute("principal"); // 다운캐스팅
		// 인증검사
		if(principal == null) {
			// 로그인을 안한 상태
			response.sendRedirect("/mvc/user/signIn?message=invalid");
			return;
		} 
		
		String action = request.getPathInfo();
		System.out.println("action 확인 : " +action);
		
		switch(action) {
		case "/todoForm":
			todoFormPage(request, response); // ctr + 1 번 클릭 후 메서드 생성
			break;
		
		case "/list":
			System.out.println("응답 이후 확인 ~~~~~");
			todoListPage(request, response, principal.getId()); // ctr + 1 번 클릭 후 메서드 생성
			break;
			
		case "/detail":
			todoDetailPage(request, response, principal.getId() ); // ctr + 1 번 클릭 후 메서드 생성
			// alt + shift + r : 이름수정
			break;
			
		case "/delete":
			// TODO 수정 예정  1번에 principalId 넣기 
			 deleteTodo(request, response, principal.getId()); // ctr + 1 번 클릭 후 메서드 생성
			break;
			
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404를 출력
			break;
		}
	}
	
	/**
	 * todo 삭제 기능
	 * @param request
	 * @param response
	 * @param principalId
	 * @throws IOException
	 */
	
	
	private void deleteTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		System.out.println("222222222222222222222   :   " +  request.getParameter("id"));
		try {
			int todoId = Integer.parseInt(request.getParameter("id"));
			 todoDAO.deleteTodo(todoId, principalId);
		} catch (Exception e) {
			System.out.println("xxxxxxxxxxxx");
			e.printStackTrace();
			//response.sendRedirect(request.getContextPath() + "/todo/list?error=invalid");
		}
		System.out.println("yyyyyyyyyyyyyyy");
		response.sendRedirect(request.getContextPath() + "/todo/list");
	}

	// 상세보기 화면
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void todoDetailPage(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		// detail?id=8;
		try {
			// todo - pk - 1, 3, 5 (야스오)
			// todo - pk - 2, 4, 6 (홍길동)
			int todoId = Integer.parseInt(request.getParameter("id"));
			TodoDTO dto = todoDAO.getTodoById(todoId);
			
			if(dto.getUserId() == principalId) {
				// 상세보기 화면으로 이동
				request.setAttribute("todo", dto);
				request.getRequestDispatcher("/WEB-INF/views/todoDetail.jsp").forward(request, response);
			} else {
				// 권한이 없습니다 or 잘못된 접근입니다.
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script> alert('권한이 없습니다.'); history.back(); </script>");
				// history.back(); 뒤로 돌아가기 
			}
			// dto(userId)
			
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/todo/list?error=invaild");
		}	
	}

	// http://localhost:8080/mvc/todo/list
	/**
	 *  사용자별 todo 리스트 화면 이동
	 * @param request
	 * @param response
	 * @param principalId
	 * @throws IOException
	 * @throws ServletException
	 */
	private void todoListPage(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException, ServletException {
		
		// request.getPathInfo()  --> URL 요청에 있어 데이터 추출
		// request.getParameter() --> URL 요청에 있어 데이터 추출
		// request.getAttribute() --> 뷰를 내릴 속성에 값을 담아서 뷰로 내릴 때 사용
		
		 List<TodoDTO> list = todoDAO.getTodosByUserId(principalId);
		 request.setAttribute("list", list);
		
		 System.out.println("99999999999999999");
		// todoList.jsp 페이지로 내부에서 이동 처리
		request.getRequestDispatcher("/WEB-INF/views/todoList.jsp").forward(request, response);
		
		
	}

/*
 *  todo 작성 페이지 이동
 *  @param 
 */
	// http://localhost:8080/mvc/user/signIn
	private void todoFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		request.getRequestDispatcher("/WEB-INF/views/todoForm.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// todo 추가 예정
		HttpSession session = request.getSession(); // 사용자가 로그인 했는지 안했는지 확인
		UserDTO principal = (UserDTO)session.getAttribute("principal");
		if(principal == null) {
			response.sendRedirect(request.getContextPath() + "/user/siginIn?error=invaild");
			return;
		}
		
		String action = request.getPathInfo();
		System.out.println("action 확인 : " +action);
		switch(action) {
		case "/add":
			// TODO 수정 예정  1번에 principalId 넣기 
			addTodo(request, response, principal.getId()); // ctr + 1 번 클릭 후 메서드 생성
			break;
			
		case "/update":
			// TODO 수정 예정  1번에 principalId 넣기 
			updateTodo(request, response, principal.getId()); // ctr + 1 번 클릭 후 메서드 생성
			break;
			
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404를 출력
			break;
		}
		
	}
	/**
	 *  todo 수정 기능
	 * @param request
	 * @param response
	 * @param principalId - 세션 ID 값
	 * @throws IOException
	 */
	private void updateTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		
		try {
			int todoId = Integer.parseInt(request.getParameter("id"));
		//	System.out.println("todoId : " + todoId);
			
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String dueDate = request.getParameter("dueDate");
			
			// CheckBox는 여러개 선택 가능한 태그 : String[] 배열로 선언 했음 
			// 이번에 CheckBox 는 하나만 사용 중
			// 체크박스가 선택되지 않았으면 NULL 을 반환하고 체크가 되어있다면 "on" 문자열로 넘어온다.
			System.out.println("3333333 : "+request.getParameter("completed"));
			boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));
			System.out.println("completed : " + completed);
			// on 문자열이 대문자인지 소문자인지 상관없이 받아서 true false 확인
//			System.out.println("completed : " +completed);
			
			TodoDTO dto = TodoDTO.builder()
					.id(todoId)
					.userId(principalId)
					.title(title)
					.description(description)
					.dueDate(dueDate)
					.completed(String.valueOf(completed))
					.build();
			
		todoDAO.updateTodo(dto, principalId);
		
		} catch (Exception e) {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('잘못된 요청입니다.'); history.back(); </script>");
		}
		// list 화면 재 요청 처리
		response.sendRedirect(request.getContextPath() +"/todo/list");
		
	}

	/*
	 * 세션별 사용자 ToDo 등록 
	 * @param request
	 * @param response
	 * @param principalId : 세션에 담겨 있는 UserId 값
	 */
	private void addTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String dueDate = request.getParameter("dueDate");
		
		// CheckBox는 여러개 선택 가능한 태그 : String[] 배열로 선언 했음 
		// 이번에 CheckBox 는 하나만 사용 중
		// 체크박스가 선택되지 않았으면 NULL 을 반환하고 체크가 되어있다면 "on" 문자열로 넘어온다.
		boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));
		// on 문자열이 대문자인지 소문자인지 상관없이 받아서 true false 확인
//		System.out.println("completed : " +completed);
		
		TodoDTO dto = TodoDTO.builder()
				.userId(principalId)
				.title(title)
				.description(description)
				.dueDate(dueDate)
				.completed(String.valueOf(completed))
				.build();
				
		todoDAO.addTodo(dto, principalId);
		
		response.sendRedirect(request.getContextPath() + "/todo/list");	
		
	}

}
