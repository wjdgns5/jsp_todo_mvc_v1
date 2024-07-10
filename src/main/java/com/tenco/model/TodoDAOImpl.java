package com.tenco.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TodoDAOImpl implements TodoDAO {
	
	private DataSource dataSource;
	
	public TodoDAOImpl() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/MyDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void addTodo(TodoDTO dto, int principalId) {
		// sql 작업할 때 
		String sql = " insert into todos(user_id, title, description, due_date, completed)"
				+ "values(?, ?, ?, ?, ?) ";
		
		try(Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false); // 자동 커밋 x 내가 직접 커밋한다.
			
			try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, principalId);
				pstmt.setString(2, dto.getTitle());
				pstmt.setString(3, dto.getDescription());
				pstmt.setString(4, dto.getDueDate()); // TodoDTO의 util을 sql로 변경
				pstmt.setString(5, dto.getCompleted());
				pstmt.executeUpdate();
				conn.commit(); // 커밋
				
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public TodoDTO getTodoById(int id) {
	
	String sql = " SELECT * FROM todos WHERE id = ? ";
	TodoDTO dto = null;
	
	try(Connection conn = dataSource.getConnection();) {
			
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		try(ResultSet rs = pstmt.executeQuery()) {
			
			if(rs.next()) {
				dto = new TodoDTO();
				dto.setId(rs.getInt("id"));
				dto.setUserId(rs.getInt("user_id"));
				dto.setTitle(rs.getString("title"));
				dto.setDescription(rs.getString("description"));
				dto.setDueDate(rs.getString("due_date"));
				dto.setCompleted(rs.getString("Completed"));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
					
		return dto;
	} // end of getTodoById()
	
	// ------------------------------------------------

	@Override
	public List<TodoDTO> getTodosByUserId(int userId) {
		
		String sql = " SELECT * FROM todos WHERE id = ? ";
		
		List<TodoDTO> list = new ArrayList<>();
		TodoDTO todoDTO = new TodoDTO();
		
		try(Connection conn = dataSource.getConnection();) {
			
			try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
				
				pstmt.setInt(1, userId);
				
				conn.setAutoCommit(false); // 자동 커밋을 수동으로 변경
				
				ResultSet rs = pstmt.executeQuery(); // rs 에 쿼리 들어가있음
				
				while(rs.next()) {
					todoDTO.setId(rs.getInt("id"));
					todoDTO.setTitle(rs.getString("title"));
					todoDTO.setDescription(rs.getString("description"));
					todoDTO.setDueDate(rs.getString("due_date"));
					todoDTO.setCompleted(rs.getString("completed"));
					todoDTO.setUserId(rs.getInt("user_id"));
					list.add(todoDTO);
					conn.commit();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	} // end of getTodosByUserId()

	@Override
	public List<TodoDTO> getALLTodos() {
		
		String sql = " SELECT * FROM todos; ";
		List<TodoDTO> list = new ArrayList<>(); // 모든 게시글을 보기 위해서는 리스트를 사용한다.
		TodoDTO todoDTO = null; // null 값 오류 방지?
		
		try(Connection conn = dataSource.getConnection();) { // db 연결
			
			// preparestatemet에 db에 연결하고 sql 쿼리문을 던진다.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// pstmt 쿼리문을 executeQuery 실행한다.
			ResultSet resultSet = pstmt.executeQuery();
			System.out.println(resultSet);
			while(resultSet.next()) {
				todoDTO = new TodoDTO();
				
				todoDTO.setId(resultSet.getInt("id"));
				todoDTO.setUserId(resultSet.getInt("user_id"));
				todoDTO.setTitle(resultSet.getString("title"));
				todoDTO.setDescription(resultSet.getString("description"));
				todoDTO.setDueDate(resultSet.getString("due_date"));
				todoDTO.setCompleted(resultSet.getString("completed"));
				todoDTO.setUserId(resultSet.getInt("user_id"));
				list.add(todoDTO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	} // end of  getALLTodos()

	@Override
	public void updateTodo(TodoDTO dto, int principalId) { 
		
		String sql = " UPDATE todos SET title = ?, description = ? WHERE id = ? ";
		
		dto = new TodoDTO();
		
		try(Connection conn = dataSource.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "구름" );
			pstmt.setString(2, "날씨가 맑다" );
			pstmt.setInt(3, principalId);
			
			pstmt.executeUpdate();
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} // end of updateTodo()

	@Override
	public void deleteTodo(int id, int principalId) { 
		
		String sql = "delete from todos where id = ? and user_id = ? ";
		
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.setInt(2, principalId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	

}
