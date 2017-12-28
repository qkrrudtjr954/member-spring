package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import db.OracleConnection;
import delegator.Delegator;
import dto.CommentDto;
import dto.MemberDto;

public class CommentDao implements CommentDaoImpl {

	
	DBConnection DBConnector = new OracleConnection();
	
	@Override
	public boolean insert(String content, int seq) {
		MemberDto current_user = Delegator.getInstance().getCurrent_user();

		String sql;
		if (DBConnector.getClass().getName().equals("db.MySqlConnection")) {
			sql = " insert into comments(content, wdate, user_id, bbs_id, del)" + " values('" + content + "', now(), '"
					+ current_user.getId() + "', " + seq + ", 0)";
		} else {
			sql = " insert into comments " + " values(comment_seq.nextval, sysdate, '" + content + "', '" + current_user.getId() + "', "
					+ seq + ", 0)";
		}

		DBConnector.initConnect();
		PreparedStatement ptmt = null;
		Connection conn = null;
		int count = 0;

		System.out.println(" * CommentDao .insert() sql: " + sql);

		try {
			conn = DBConnector.makeConnection();
			ptmt = conn.prepareStatement(sql);
			count = ptmt.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (count > 0) ? true : false;
	}

	@Override
	public List<CommentDto> getComments(int seq) {
		List<CommentDto> comments = new ArrayList<>();
		String sql = " select * from comments where bbs_id=" + seq + " and del=0 order by wdate desc";

		DBConnector.initConnect();

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		System.out.println(" * CommentDao getComments sql : " + sql);

		try {
			conn = DBConnector.makeConnection();
			ptmt = conn.prepareStatement(sql);
			rs = ptmt.executeQuery(sql); // query 를 실행하라 그리고 그 값을 rs에 저장해라.

			while (rs.next()) {
				CommentDto comment = new CommentDto();

				comment.setSeq(rs.getInt("seq"));
				comment.setBbs_id(rs.getInt("bbs_id"));
				comment.setContent(rs.getString("content"));
				comment.setUser_id(rs.getString("user_id"));
				comment.setWdate(rs.getString("wdate"));
				comment.setDel(rs.getInt("del"));

				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, rs);
		}
		return comments;
	}

	@Override
	public boolean delete(int seq) {
		// TODO Auto-generated method stub
		String sql = "update comment set del=1 where seq="+seq;
		
		DBConnector.initConnect();

		Connection conn = null;
		PreparedStatement ptmt = null;
		int count = 0;
		System.out.println(" * CommentDao .delete() sql : " + sql);

		try {
			conn = DBConnector.makeConnection();
			ptmt = conn.prepareStatement(sql);
			count = ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, null);
		}
		
		return (count > 0)? true : false;
	}

}
