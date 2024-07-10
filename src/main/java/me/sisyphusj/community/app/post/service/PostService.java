package me.sisyphusj.community.app.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.post.domain.CreatePostReqDTO;
import me.sisyphusj.community.app.post.domain.PostThumbnailResDTO;
import me.sisyphusj.community.app.post.domain.PostVO;
import me.sisyphusj.community.app.post.mapper.PostMapper;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;

	@Transactional
	public void createdPost(CreatePostReqDTO createPostReqDTO) {
		postMapper.insertPost(PostVO.of(createPostReqDTO));
	}

	@Transactional(readOnly = true)
	public List<PostThumbnailResDTO> getPostThumbnailList(int amount, int offset) {

		return postMapper.selectPostThumbnailList(amount, offset).stream()
			.map(PostThumbnailResDTO::of)
			.toList();
	}
}
