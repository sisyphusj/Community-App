package me.sisyphusj.community.app.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.user.domain.UserVO;

@Mapper
public interface UserMapper {

	void insertUser(UserVO userVO);

	int selectCountByUsername(String username);

	int selectCountByName(String name);
}
