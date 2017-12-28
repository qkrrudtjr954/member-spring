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
import dto.BbsDto;

public class BbsDao implements BbsDaoImpl {
	
	DBConnection DBConnector = new OracleConnection();

	@Override
	public boolean insert(String title, String content) {
		// TODO Auto-generated method stub
		// 접속을 가져온다.
		Connection conn = DBConnector.makeConnection();
		PreparedStatement ptmt = null;

		String id = Delegator.getInstance().getCurrent_user().getId();

		String sql = "";

		if (DBConnector.getClass().getName().equals("db.MySqlConnection")) {
			sql = " insert into bbs(id, title, content, wdate, del, readcount) " + " values('" + id + "', '" + title
					+ "', '" + content + "', now(), 0, 0); ";
		} else {
			sql = " insert into bbs " + " values(seq_bbs.nextval, '" + id + "', '" + title + "', '" + content
					+ "', sysdate, 0, 0) ";
		}

		BbsDto bbsDto = null;

		int count = 0;

		System.out.println(" * BbsDao .insert() sql : " + sql);

		try {
			ptmt = conn.prepareStatement(sql); // initializing
			count = ptmt.executeUpdate(sql); 	// .executeUpdate() : 데이터베이스를 바꾸는 작업 (insert, update, delete)
			// count는 바뀐 수

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, null);
		}

		return count > 0 ? true : false;
	}

	@Override
	public boolean delete(BbsDto bbsDto) {
		// TODO Auto-generated method stub
		String sql = "update bbs set del=1 where seq=" + bbsDto.getSeq() + "";

		PreparedStatement ptmt = null;

		Connection conn = DBConnector.makeConnection();

		int count = 0;

		System.out.println(" * BbsDao .delete() sql : " + sql);

		try {
			ptmt = conn.prepareStatement(sql);
			count = ptmt.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, null);
		}

		return count > 0 ? true : false;
	}

	@Override
	public int update(int seq, String title, String content) {
		// 접속을 가져온다.
		Connection conn = DBConnector.makeConnection();
		PreparedStatement ptmt = null;

		String sql = "update bbs set title='" + title + "', content='" + content + "' where seq=" + seq;

		BbsDto bbsDto = null;

		int count = 0;

		System.out.println(" * BbsDao .update() sql : " + sql);

		try {
			ptmt = conn.prepareStatement(sql); // initializing
			count = ptmt.executeUpdate(sql); // .executeUpdate() : 데이터베이스를 바꾸는 작업 (insert, update, delete)
			// count는 바뀐 수
			
			if(count > 0) {
				count = seq;
			}else {
				count = -1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, null);
		}
		// update 성공하면 seq, 실패하면 -1 리턴
		return count;
	}

	@Override
	public BbsDto getBbsBySeq(int seq) {
		String sql = " select * from bbs where seq=" + seq;

		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;

		BbsDto bbsDto = new BbsDto();

		System.out.println(" * BbsDao .getBbsBySeq() sql : " + sql);

		try {
			conn = DBConnector.makeConnection();
			ptmt = conn.prepareStatement(sql); // initializing
			rs = ptmt.executeQuery();

			if (rs.next()) {
				bbsDto.setSeq(rs.getInt("seq"));
				bbsDto.setId(rs.getString("id"));

				bbsDto.setTitle(rs.getString("title"));
				bbsDto.setContent(rs.getString("content"));
				bbsDto.setWdate(rs.getString("wdate"));

				bbsDto.setDel(rs.getInt("del"));
				bbsDto.setReadcount(rs.getInt("readcount"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, null);
		}

		return bbsDto;
	}

	@Override
	public List<BbsDto> search(String str) {
		String sql = " select * from bbs " + " where (id like '%" + str + "%' or " + " title like '%" + str + "%' or "
				+ " content like '%" + str + "%' ) and " + " del != 1  order by seq ";

		Connection conn = DBConnector.makeConnection();
		PreparedStatement ptmt = null;

		ResultSet rs = null;
		List<BbsDto> bbsList = new ArrayList<BbsDto>();

		System.out.println(" * BbsDao .search() sql : " + sql);

		try {
			ptmt = conn.prepareStatement(sql);
			rs = ptmt.executeQuery(sql); // query 를 실행하라 그리고 그 값을 rs에 저장해라.

			while (rs.next()) {
				BbsDto bbsDto = new BbsDto();

				bbsDto.setSeq(rs.getInt("seq"));
				bbsDto.setId(rs.getString("id"));

				bbsDto.setTitle(rs.getString("title"));
				bbsDto.setContent(rs.getString("content"));
				bbsDto.setWdate(rs.getString("wdate"));

				bbsDto.setDel(rs.getInt("del"));
				bbsDto.setReadcount(rs.getInt("readcount"));

				bbsList.add(bbsDto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, rs);
		}
		return bbsList;
	}

	@Override
	public List<BbsDto> getBbsList() {
		String sql = " select * from bbs where del != 1 order by seq desc ";
		DBConnector.initConnect();
		Connection conn = DBConnector.makeConnection();
		PreparedStatement ptmt = null;

		ResultSet rs = null;
		List<BbsDto> bbsList = new ArrayList<>();

		System.out.println(" * BbsDao getBbsList sql : " + sql);

		try {
			ptmt = conn.prepareStatement(sql);
			rs = ptmt.executeQuery(sql); // query 를 실행하라 그리고 그 값을 rs에 저장해라.

			while (rs.next()) {
				BbsDto bbs = new BbsDto();

				bbs.setSeq(rs.getInt("seq"));
				bbs.setId(rs.getString("id"));

				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
				bbs.setWdate(rs.getString("wdate"));

				bbs.setDel(rs.getInt("del"));
				bbs.setReadcount(rs.getInt("readcount"));

				bbsList.add(bbs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, rs);
		}

		return bbsList;
	}

	@Override
	public List<BbsDto> getMyBbsList() {
		String sql = " select * from bbs where del != 1 and id='" + Delegator.getInstance().getCurrent_user().getId()
				+ "'  order by seq  desc ";

		Connection conn = DBConnector.makeConnection();
		PreparedStatement ptmt = null;

		ResultSet rs = null;
		List<BbsDto> myBbsList = new ArrayList<>();

		System.out.println(" * BbsDao  getMyBbsList() sql : " + sql);

		try {
			ptmt = conn.prepareStatement(sql);
			rs = ptmt.executeQuery(sql); // query 를 실행하라 그리고 그 값을 rs에 저장해라.

			while (rs.next()) {
				BbsDto bbs = new BbsDto();

				bbs.setSeq(rs.getInt("seq"));
				bbs.setId(rs.getString("id"));

				bbs.setTitle(rs.getString("title"));
				bbs.setContent(rs.getString("content"));
				bbs.setWdate(rs.getString("wdate"));

				bbs.setDel(rs.getInt("del"));
				bbs.setReadcount(rs.getInt("readcount"));

				myBbsList.add(bbs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, rs);
		}

		return myBbsList;
	}

	@Override
	public BbsDto getLastBbsDtoByTitle(String title, String content) {
		BbsDto bbsDto = new BbsDto();

		String id = Delegator.getInstance().getCurrent_user().getId();

		Connection conn = DBConnector.makeConnection();
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		String sql;

		// String sql = "select * from bbs where id='"+id+"' and title='"+title+"' and
		// content='"+content+"'";

		if (DBConnector.getClass().getName().equals("db.MySqlConnection")) {
			sql = " select seq, id, title, content, wdate, content, del, readcount from bbs " + " where id='" + id
					+ "' and " + " title='" + title + "' and " + " content ='" + content + "' order by seq desc  ";
		} else {
			sql = " select seq, id, title, content, to_char(wdate, 'yyyy-mm-dd hh:mi:ss') as wdate, content, del, readcount from bbs "
					+ " where id='" + id + "' and " + " title='" + title + "' and " + " content ='" + content
					+ "' order by seq desc  ";
		}

		System.out.println(" * BbsDao .getLastBbsDtoByTitle sql : " + sql);

		try {
			ptmt = conn.prepareStatement(sql);
			rs = ptmt.executeQuery();

			while (rs.next()) {
				bbsDto.setSeq(rs.getInt("seq"));
				bbsDto.setId(rs.getString("id"));

				bbsDto.setTitle(rs.getString("title"));
				bbsDto.setContent(rs.getString("content"));
				bbsDto.setWdate(rs.getString("wdate"));

				bbsDto.setDel(rs.getInt("del"));
				bbsDto.setReadcount(rs.getInt("readcount"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, null);
		}

		System.out.println(bbsDto);

		return bbsDto;
	}

	@Override
	public void plusReadCount(BbsDto bbsDto) {
		String sql = " update bbs set readcount=readcount+1 where seq=" + bbsDto.getSeq() + "";

		Connection conn = DBConnector.makeConnection();
		PreparedStatement ptmt = null;

		try {
			ptmt = conn.prepareStatement(sql);
			ptmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(ptmt, conn, null);
		}

	}

}
