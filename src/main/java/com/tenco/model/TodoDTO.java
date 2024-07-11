package com.tenco.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TodoDTO {
	
	private int	id;
	private int userId;
	private String title;
	private String description;
	private String dueDate;
	private String completed; // 0 과 1 이 담기는데 실제로는 "0" "1" 로 담긴다.
	
	// completed 를 데이터 변환 메서드를 만들어 보자.
	public String completedToString() {
		return this.completed.equals("1") ? "true" : "false" ;
	}
	

}


