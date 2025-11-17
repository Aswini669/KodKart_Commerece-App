package com.kodewala.kodkart.serviceimplementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kodewala.kodkart.entities.Users;
import com.kodewala.kodkart.services.UserService;

public class UserServiceImplementation implements UserService{

	@Override
	public boolean register(Connection con, Users user) {
		String chSql = "select * from users where email=?";
		
		String sql = "insert into users(name,email,phone,password) values (?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(chSql);
			pstmt.setString(1, user.getEmail());
			ResultSet result = pstmt.executeQuery();
			if(!result.next()) {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, user.getName());
				pstmt.setString(2, user.getEmail());
				pstmt.setString(3, user.getPhone());
				pstmt.setString(4, user.getPassword());
				int rs = pstmt.executeUpdate();
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int login(Connection con,String email, String password) {
		String sql = "select * from users where email=? AND password=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();
			if(result.next()) {
				return result.getInt(1);
			}else {
				return 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
