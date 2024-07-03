package me.sisyphusj.community.app.auth.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.auth.domain.AuthVO;

@Mapper
public interface AuthMapper {
	Optional<AuthVO> selectAuthByUsername(String username);
}
