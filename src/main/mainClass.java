package main;

import delegator.Delegator;

public class mainClass {
	public static void main(String[] args) {
		Delegator delegator = Delegator.getInstance();
		
		delegator.memController.Login();
	}
}
