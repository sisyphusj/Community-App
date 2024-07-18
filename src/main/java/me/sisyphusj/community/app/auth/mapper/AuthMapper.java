package me.sisyphusj.community.app.auth.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.auth.domain.AuthVO;
import me.sisyphusj.community.app.auth.domain.OAuthVO;
import me.sisyphusj.community.app.auth.domain.SignupVO;

@Mapper
public interface AuthMapper {

	void insertAuth(SignupVO signupVO);

	void insertOAuth(OAuthVO oAuthVO);

	Optional<AuthVO> selectAuthByUsername(String username);

	Optional<OAuthVO> selectOAuthByUsername(String username);

	int selectCountByUsername(String username);

}
