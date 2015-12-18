package scrumbo.de.entity;

public class CurrentSprint {
	
	public static Integer	id;
	public static Integer	sprintnummer;
							
	public CurrentSprint(Sprint sprint) {
		CurrentSprint.id = sprint.getId();
		CurrentSprint.sprintnummer = sprint.getSprintnummer();
	}
	
	public CurrentSprint() {
	}
	
}
