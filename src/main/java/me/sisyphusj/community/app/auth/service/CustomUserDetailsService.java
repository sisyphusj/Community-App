package me.sisyphusj.community.app.auth.service;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.auth.domain.AuthVO;
import me.sisyphusj.community.app.auth.mapper.AuthMapper;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final AuthMapper authMapper;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return authMapper.selectAuthByUsername(username)
			.map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
	}

	private UserDetails createUserDetails(AuthVO auth) {

		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(USER);

		return new User(
			String.valueOf(auth.getUserId()),
			auth.getPassword(),
			Collections.singleton(grantedAuthority)
		);
	}
}
