package scrumbo.de.common;

import javafx.scene.control.Tooltip;

public class MyToolBox {
	
	private Tooltip	tooltipBurndownChart		= new Tooltip(
			"Die Fortschrittsanalyse eines Projektes erfolgt bei Scrum innerhalb der Sprints anhand\n"
					+ "eines Burndown Charts. Das Burndown Chart gibt Auskunft über die noch zu leistende Arbeit ab dem aktuellen Tag. Auf der\n"
					+ "y-Achse wird der geschätzte verbleibende Aufwand aller Aufgaben des Sprints in Tagen angezeigt und auf der\n"
					+ "x-Achse die Anzahl der Arbeitstage.");
					
	private Tooltip	tooltipImpedimentBacklog	= new Tooltip(
			"Ein Impediment Backlog besteht aus den Eintragungen von Hindernissen, die das\n"
					+ "Entwicklungsteam an effektiver Arbeit hindern inklusive Datum des Auftretens und Datum der Behebung.");
					
	private Tooltip	tooltipProductBacklog		= new Tooltip(
			"Ein Product Backlog besteht aus User Stories, die vom Product Owner priorisiert werden.\n"
					+ "Die Aufwände der User Stories werden vom Entwicklungsteam geschätzt, beispielsweise in einer Schätzklausur.\n"
					+ "Eine User Story muss innerhalb eines Sprints realisierbar sein.\n"
					+ "Das Product Backlog ist nicht vollständig und verändert sich im Laufe des Projekts.\n"
					+ "Die Anforderungen können vom Kunden nach Bedarf verändert werden. Es wird üblich priorisiert.\n"
					+ "Es dürfen also mehr als eine User Story die Priorität 1 erhalten.\n"
					+ "Der Product Owner legt die Reihenfolge der User Stories fest. Die hoch priorisierten User Stories sollten, falls\n"
					+ "möglich zuerst abgearbeitet werden. Daher wird im Sprint Planning Meeting nicht über die Reihenfolge der Abarbeitung\n"
					+ "der User Stories diskutiert sondern nur über die Anzahl.");
					
	private Tooltip	tooltipSprintBacklog		= new Tooltip(
			"Ein Sprint Backlog wird im Laufe des Sprints verändert und entsteht beim Sprint Planung.\n"
					+ "Es enthält die User Stories, die in dem aktuellen Sprint bearbeitet werden.\n"
					+ "User Stories werden bezüglich ihrer technischen Anforderungen untersucht und in Tasks aufgeteilt,\n"
					+ "deren Aufwand (jetzt genauer) geschätzt wird. In einem Sprint sollen die Entwickler die Tasks\n"
					+ "zu den hoch priorisierten User Stories des Sprints zuerst abarbeiten.\n"
					+ "Gibt es mehr als eine User Story mit derselben Priorisierung, wird keine weitere Angabe\n"
					+ "zur Reihenfolge gemacht. Sind Tasks einer User Story in Bearbeitung und noch weitere\n"
					+ "Tasks dieser User Story vorhanden, so soll ein Entwickler, der ein neues Task zweck\n"
					+ "Bearbeitung aussucht, erst ein Task der aktuellen bearbeiteten User Story aussuchen.");
					
	private Tooltip	tooltipDoD					= new Tooltip(
			"Eine Definition of Done ist die Einigung eines agilen Teams darauf,\n"
					+ "was getan werden muss, damit ein Feature als fertig angesehen wird.");
					
	private Tooltip	tooltipWelcome				= new Tooltip(
			"Herzlich Willkommen zum ScrumBO! \nDiese Applikation ermöglicht es Ihnen Projektmanagement \nnach Scrum zu betreiben.");
			
	public MyToolBox() {
	
	}
	
	public Tooltip getTooltipBurndownChart() {
		return tooltipBurndownChart;
	}
	
	public Tooltip getTooltipImpedimentBacklog() {
		return tooltipImpedimentBacklog;
	}
	
	public Tooltip getTooltipProductBacklog() {
		return tooltipProductBacklog;
	}
	
	public Tooltip getTooltipSprintBacklog() {
		return tooltipSprintBacklog;
	}
	
	public Tooltip getTooltipDoD() {
		return tooltipDoD;
	}
	
	public Tooltip getTooltipWelcome() {
		return tooltipWelcome;
	}
	
}
