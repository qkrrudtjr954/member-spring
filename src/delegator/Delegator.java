package delegator;

import controller.MainController;
import controller.MemberController;
import dto.MemberDto;

public class Delegator {
	private static Delegator instance = null;
	private MemberDto current_user = null;
	
	public MainController mainController = null;
	public MemberController memController = null;
	
	private Delegator() {
		memController = new MemberController();
	}
	
	public static Delegator getInstance() {
		if(instance==null) {
			instance = new Delegator();
		}
		
		return instance;
	}

	public MemberDto getCurrent_user() {
		return current_user;
	}

	public void setCurrent_user(MemberDto current_user) {
		this.current_user = current_user;
	}
	
	
	
}
