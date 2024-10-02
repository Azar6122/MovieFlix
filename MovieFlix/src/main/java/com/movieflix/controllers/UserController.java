package com.movieflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movieflix.entities.Movie;
import com.movieflix.entities.User;
import com.movieflix.services.MovieService;
import com.movieflix.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController 
{
	@Autowired
	UserService uServ;
	@Autowired
	MovieService movServ;
	
	@PostMapping("register")
	public String addUsers(@ModelAttribute User usr)
	{
		boolean status = uServ.emailExists(usr.getEmail());
		if(status==true)
		{
			return "regFail";
		}
		else
		{
			uServ.addUsers(usr);
			return"regSuccess";
		}
	}
	
	@PostMapping("login")
	public String validateUser(@RequestParam String email, @RequestParam String password, HttpSession session )
	{
		boolean loginStatus = uServ.checkUser(email, password);
		if(loginStatus == true)
		{
			session.setAttribute("email", email );
			if(email.equals("admin@gmail.com"))
			{
				return "adminHome";
			}
			else
			{
				return "home";
			}
		}
		else
		{
			return "login";
		}
	}
	
	@GetMapping("viewuser")
	public String viewUser(Model model)
	{
		List<User> userList = uServ.viewUser();
		model.addAttribute("user",userList);
		return "viewuser";
	}
	
	@GetMapping("exploremovie")
	public String exploreMovie(Model model, HttpSession session)
	{
		//Getting the email from session
		String email = (String) session.getAttribute("email");
		//Getting the user details for email
		User usr = uServ.getUser(email);
		//checking whether user is premium 
		boolean status = usr.isPremium();
		if(status == true)
		{
			//Getting the list of movies
			List<Movie> movieList = movServ.viewMovie();
			//Adding the movie details in model
			model.addAttribute("movie" ,movieList);
			//if premium, show movies
			return "viewmovieuser";
		}
		else
		{
			//if not, make payment
			return "payment";
		}
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session)
	{
		session.invalidate();
		return"login";
	}

}
