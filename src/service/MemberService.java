package service;

import dao.MemberDao;
import dao.MemberDaoImpl;
import dto.MemberDto;

public class MemberService implements MemberServiceImpl{

	MemberDaoImpl memDao = new MemberDao();
	
	@Override
	public boolean getId(String id) {
		// TODO Auto-generated method stub
		return memDao.getId(id);
	}

	@Override
	public MemberDto search(String id, char[] pwd) {
		// TODO Auto-generated method stub
		return memDao.search(id, pwd);
	}

	@Override
	public boolean delMember(int id, char[] pwd) {
		// TODO Auto-generated method stub
		return memDao.delMember(id, pwd);
	}

	@Override
	public boolean addMember(MemberDto dto) {
		// TODO Auto-generated method stub
		return memDao.addMember(dto);
	}


}
