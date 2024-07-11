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
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addTodo(TodoDTO dto, int principalId) {
		// sql 작업할 때
		String sql = " insert into todos(user_id, title, description, due_date, completed)" + "values(?, ?, ?, ?, ?) ";

		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false); // 자동 커밋 x 내가 직접 커밋한다.

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, principalId);
				pstmt.setString(2, dto.getTitle());
				pstmt.setString(3, dto.getDescription());
				pstmt.setString(4, dto.getDueDate()); // TodoDTO의 util을 sql로 변경
				// if(dto.getCompleted() == "true") {}
				pstmt.setInt(5, dto.getCompleted() == "true" ? 1 : 0);
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

		try (Connection conn = dataSource.getConnection();) {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
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

		String sql = " SELECT * FROM todos WHERE user_id = ? ";
		List<TodoDTO> todos = new ArrayList<TodoDTO>();

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);

			try (ResultSet rs = pstmt.executeQuery();) {

				while (rs.next()) {
					TodoDTO dto = new TodoDTO(); // 메모리에 객체가 올라가야 사용할 수 있다.
					dto.setId(rs.getInt("id")); // db의 속성에 맞게 적어야 한다.
					dto.setUserId(rs.getInt("user_id"));
					dto.setTitle(rs.getString("title"));
					dto.setDescription(rs.getString("description"));
					dto.setDueDate(rs.getString("due_date"));
					dto.setCompleted(rs.getString("completed"));
					todos.add(dto);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return todos;
	} // end of getTodosByUserId()

	@Override
	public List<TodoDTO> getALLTodos() {
		// List는 인터페이스 이다.
		// List는 자신에게 맞는 구현클래스를 사용할수 있기에 ex) arrayList나 다른 리스트 등을 쓴다.
		String sql = " SELECT * FROM todos ";
		List<TodoDTO> todos = new ArrayList<TodoDTO>();

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			try (ResultSet rs = pstmt.executeQuery();) {

				while (rs.next()) {
					TodoDTO dto = new TodoDTO(); // 메모리에 객체가 올라가야 사용할 수 있다.
					dto.setId(rs.getInt("id")); // db의 속성에 맞게 적어야 한다.
					dto.setUserId(rs.getInt("user_id"));
					dto.setTitle(rs.getString("title"));
					dto.setDescription(rs.getString("description"));
					dto.setDueDate(rs.getString("due_date"));
					dto.setCompleted(rs.getString("completed"));
					todos.add(dto);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return todos;
	} // end of getALLTodos()

	@Override
	public void updateTodo(TodoDTO dto, int principalId) {

		// SELECT <-- 있지 없지 확인 과정 (필요)


		String sql = " UPDATE todos SET title = ?, description = ? " + " 				, due_date = ?, completed = ? "
				+ "    WHERE id = ? and user_id = ? ";
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getDescription());
				pstmt.setString(3, dto.getDueDate());
				pstmt.setInt(4, dto.getCompleted() == "true" ? 1 : 0);
				pstmt.setInt(5, dto.getId());
				pstmt.setInt(6, dto.getUserId());
				pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // end of updateTodo()

	@Override
	public void deleteTodo(int id, int principalId) {

		String sql = "delete from todos where id = ? and user_id = ? ";

		try (Connection conn = dataSource.getConnection();) {
			conn.setAutoCommit(false);

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setInt(1, id);
				pstmt.setInt(2, principalId);
				pstmt.executeUpdate();

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // end of deleteTodo()

}
