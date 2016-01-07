package scrumbo.de.entity;

import java.util.List;

public class CurrentUserStory {
	
	public static int						userstoryID;
	public static List<DefinitionOfDone>	dod;
	public static int						status;
											
	public CurrentUserStory(int userstoryID) {
		this.userstoryID = userstoryID;
	}
	
	public CurrentUserStory() {
	
	}
	
	public static int getUserstoryID() {
		return userstoryID;
	}
	
	public static void setUserstoryID(int userstoryID) {
		CurrentUserStory.userstoryID = userstoryID;
	}
	
	public static List<DefinitionOfDone> getDod() {
		return dod;
	}
	
	public static void setDod(List<DefinitionOfDone> dod) {
		CurrentUserStory.dod = dod;
	}
	
	public static int getStatus() {
		return status;
	}
	
	public static void setStatus(int status) {
		CurrentUserStory.status = status;
	}
	
}
