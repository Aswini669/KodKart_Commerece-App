package com.kodewala.kodkart.services;

import java.sql.Connection;

import com.kodewala.kodkart.entities.Users;

public interface UserService {

	boolean register(Connection con, Users user);

	int login(Connection con, String email, String password);

}
