package service;

import java.util.List;

import dao.CommentDao;
import dao.CommentDaoImpl;
import dto.CommentDto;

public class CommentService implements CommentServiceImpl {

	CommentDaoImpl comDao = new CommentDao();
	
	@Override
	public boolean insert(String content, int seq) {
		// TODO Auto-generated method stub
		return comDao.insert(content, seq);
	}

	@Override
	public List<CommentDto> getComments(int seq) {
		// TODO Auto-generated method stub
		return comDao.getComments(seq);
	}

	@Override
	public boolean delete(int seq) {
		// TODO Auto-generated method stub
		return comDao.delete(seq);
	}

}
