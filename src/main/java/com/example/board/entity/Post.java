package com.example.board.entity;
import java.sql.Timestamp;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500, nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String content;
	
	@Column
	private String writer;

	@Column
	private Timestamp date;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private Users users;


}
