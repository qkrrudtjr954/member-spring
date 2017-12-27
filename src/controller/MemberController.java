package controller;

import dto.MemberDto;
import service.MemberService;
import service.MemberServiceImpl;
import view.Account;
import view.Login;

public class MemberController {
	MemberServiceImpl memService = new MemberService();
	
	public void Login() {
		new Login();
	}
	
	public void Account() {
		new Account();
	}

	public MemberDto getMember(String id, char[] pwd) {
		// TODO Auto-generated method stub
		return memService.search(id, pwd);
	}
	
	public boolean idCheck(String id) {
		return memService.getId(id);
	}
	
	public boolean addMember(MemberDto dto) {
		return memService.addMember(dto);
	}

}
