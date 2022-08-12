package com.aureocarmelino.imperblogbackend.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@Table(name = "tb_post")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post 
{
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pkPost;
	
	@OneToOne
	@JoinColumn(name = "fk_author")
	private Author author;
	
	private String postContent;
	
	private String title;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@CreationTimestamp
	private LocalDateTime creationDate;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@UpdateTimestamp
	private LocalDateTime updateDate;
}