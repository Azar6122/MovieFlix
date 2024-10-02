package com.movieflix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movieflix.entities.User;
import com.movieflix.repositories.UserRepository;

@Service
public class UserServiceImplementation implements UserService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public String addUsers(User usr) {
		userRepo.save(usr);	
		return "User is created and saved";
	}
	
	public boolean emailExists(String email) {
		if(userRepo.findByEmail(email)==null)
		{
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean checkUser(String email, String password) {
		User usr = userRepo.findByEmail(email);
		String db_password = usr.getPassword();
		if(db_password.equals(password))
		{
			return true;
		}
		else 
		{
		return false;
		}
	}

	@Override
	public List<User> viewUser() {
		List<User> userList = userRepo.findAll();
		return userList;
	}

	@Override
	public User getUser(String email) {
		User user = userRepo.findByEmail(email);
		return user;
	}

	@Override
	public void updateUser(User user) {
		userRepo.save(user);
	}

}
