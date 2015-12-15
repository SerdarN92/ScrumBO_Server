package scrumbo.de.entity;

import java.util.List;

public class CurrentScrumprojekt {
	
	public static int					scrumprojektID;
	public static String				projektname;
	public static List<ProductBacklog>	productbacklog;
	public static List<Impediment>		impedimentbacklog;
	
	public CurrentScrumprojekt(Scrumprojekt projekt) {
		CurrentScrumprojekt.scrumprojektID = projekt.getScrumProjektID();
		CurrentScrumprojekt.projektname = projekt.getProjektname();
	}
	
	public CurrentScrumprojekt() {
	}
	
}
