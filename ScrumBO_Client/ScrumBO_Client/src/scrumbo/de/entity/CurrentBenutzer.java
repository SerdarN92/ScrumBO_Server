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
public class CurrentBenutzer {
	
	public static int						benutzerID;
	public static String					vorname;
	public static String					nachname;
	public static String					email;
	public static String					passwort;
	public static int						benutzerrollenID;
	public static String					benutzerrolle;
	public static ArrayList<Scrumprojekt>	projekte;
	public static Boolean					isSM	= false;
	
	public CurrentBenutzer(Benutzer benutzer) {
		CurrentBenutzer.benutzerID = benutzer.getId();
		CurrentBenutzer.vorname = benutzer.getVorname();
		CurrentBenutzer.nachname = benutzer.getNachname();
		CurrentBenutzer.email = benutzer.getEmail();
		CurrentBenutzer.passwort = benutzer.getPasswort();
		// CurrentBenutzer.benutzerrollenID =
		// benutzer.getBenutzerrolle().getId();
		// CurrentBenutzer.benutzerrolle =
		// benutzer.getBenutzerrolle().getBezeichnung();
		// CurrentBenutzer.projekte = benutzer.getScrumProjekte();
	}
	
	public CurrentBenutzer() {
	}
	
}
