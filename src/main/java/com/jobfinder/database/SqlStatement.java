package com.jobfinder.database;

public class SqlStatement {
	
	public static String GetUserInfoByNamePwd = "SELECT id,name FROM jobfinder.user where name=? and password=?;";
	
	public static String GetUserInfoByName = "SELECT id FROM finder.user where name=?;";
	
	public static String InsertUserInfo = "INSERT INTO finder.user (name,password) VALUES (?,?);";
	
	public static String UpdateUserInfoById = "UPDATE finder.user SET ";
	
}
