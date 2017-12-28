package dao;

import java.util.List;

import dto.CommentDto;

public interface CommentDaoImpl {

	public boolean insert(String content, int seq);
	public List<CommentDto> getComments(int seq);
	public boolean delete(int seq);
}
