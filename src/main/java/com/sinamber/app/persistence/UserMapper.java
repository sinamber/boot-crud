package com.sinamber.app.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinamber.app.model.User;

/*
 * This Mapper contain XML and Annotation,just for demo.
 * In production project,choose pure XML or Annotation. :)
 */
public interface UserMapper {
	public List<User> list();

	public int add(@Param("vo") User user);

	public int update(@Param("vo") User user);

	@Select("SELECT id,name FROM user WHERE id = #{id}")
	public User get(@Param("id") long id);

	@Delete("DELETE FROM user WHERE id = #{id}")
	public int delete(@Param("id") long id);
}
