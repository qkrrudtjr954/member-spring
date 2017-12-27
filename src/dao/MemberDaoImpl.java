package dao;

import dto.MemberDto;

public interface MemberDaoImpl {
	
	public boolean getId(String id);
	public MemberDto search(String id, char[] pwd);
	public boolean delMember(int id, char[] pwd);
	public boolean addMember(MemberDto dto);
	
}
