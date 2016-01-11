/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.entity;

import java.util.ArrayList;

/**
 *
 * @author Serdar
 */
public class CurrentUser {
	
	public static int					userId;
	public static String				prename;
	public static String				lastname;
	public static String				email;
	public static String				password;
	public static int					roleId;
	public static String				role;
	public static ArrayList<Project>	projects;
	public static Boolean				isSM	= false;
	public static Boolean				isPO	= false;
	public static Boolean				isDev	= false;
												
	public CurrentUser(User benutzer) {
		CurrentUser.userId = benutzer.getId();
		CurrentUser.prename = benutzer.getVorname();
		CurrentUser.lastname = benutzer.getNachname();
		CurrentUser.email = benutzer.getEmail();
		CurrentUser.password = benutzer.getPasswort();
	}
	
	public CurrentUser() {
	}
	
}
