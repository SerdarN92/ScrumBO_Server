package scrumbo.de.entity;

import java.util.List;

public class CurrentProject {
	
	public static int				projectId;
	public static String			projectname;
	public static ProductBacklog	productbacklog;
	public static List<Impediment>	impedimentbacklog;
									
	public CurrentProject(Project projekt) {
		CurrentProject.projectId = projekt.getScrumProjektID();
		CurrentProject.projectname = projekt.getProjektname();
	}
	
	public CurrentProject() {
	}
	
}
