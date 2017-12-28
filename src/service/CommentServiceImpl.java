package service;

import java.util.List;

import dto.CommentDto;

public interface CommentServiceImpl {

	public boolean insert(String content, int seq);
	public List<CommentDto> getComments(int seq);
}
