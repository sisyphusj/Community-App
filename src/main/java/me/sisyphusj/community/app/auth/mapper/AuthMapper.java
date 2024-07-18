package me.sisyphusj.community.app.auth.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.auth.domain.AuthVO;
import me.sisyphusj.community.app.auth.domain.OAuthInsertVO;
import me.sisyphusj.community.app.auth.domain.OAuthSelectVO;
import me.sisyphusj.community.app.auth.domain.SignupVO;

@Mapper
public interface AuthMapper {

	void insertAuth(SignupVO signupVO);

	void insertOAuth(OAuthInsertVO oAuthInsertVO);

	Optional<AuthVO> selectAuthByUsername(String username);

	Optional<OAuthSelectVO> selectOAuthByUsername(String username);

	int selectCountByUsername(String username);

}
