package main;

import db.DBConnection;
import db.OracleConnection;
import delegator.Delegator;

public class mainClass {
	public static void main(String[] args) {
		
		DBConnection DBConnector = new OracleConnection();
		DBConnector.initConnect();
		
		Delegator delegator = Delegator.getInstance();
		
		delegator.memController.Login();
	}
}
