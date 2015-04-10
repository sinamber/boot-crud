package com.sinamber.app.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinamber.app.model.User;

public interface UserMapper {
	public int add(@Param("vo") User user);

	@Select("select * from user")
	public List<User> listAll();

	public List<User> list();
}
