package scrumbo.de.entity;

public class CurrentSprint {
	
	public static Integer	id;
	public static Integer	sprintnummer;
	public static boolean	status;
							
	public CurrentSprint(Sprint sprint) {
		CurrentSprint.id = sprint.getId();
		CurrentSprint.sprintnummer = sprint.getSprintnummer();
		CurrentSprint.status = sprint.getStatus();
	}
	
	public CurrentSprint() {
	}
	
}
