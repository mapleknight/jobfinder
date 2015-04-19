package com.jobfinder.database;

public class SqlStatement {
	
	public static String GetUserInfoByNamePwd = "SELECT id,name FROM jobfinder.user where name=? and password=?;";
	
	public static String GetUserInfoByName = "SELECT id FROM jobfinder.user where name=?;";
	
	public static String InsertUserInfo = "INSERT INTO jobfinder.user (name,password) VALUES (?,?);";
	
	public static String UpdateUserInfoById = "UPDATE jobfinder.user SET ";
	
}
