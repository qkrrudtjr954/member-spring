package controller;

import service.CommentService;
import service.CommentServiceImpl;

public class CommentController {
	
	CommentServiceImpl comService = new CommentService();
	
	public boolean addComment(String content, int seq) {
		return comService.insert(content, seq);
	}

	public boolean delete(int seq) {
		return comService.delete(seq);
	}

}
