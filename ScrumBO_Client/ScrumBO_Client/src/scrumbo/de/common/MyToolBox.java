package scrumbo.de.common;

import javafx.scene.control.Tooltip;

public class MyToolBox {
	
	private Tooltip	tooltipBurndownChart		= new Tooltip(
			"Die Fortschrittsanalyse eines Projektes erfolgt bei Scrum innerhalb der Sprints anhand\n"
					+ "eines Burndown Charts. Das Burndown Chart gibt Auskunft �ber die noch zu leistende Arbeit ab dem aktuellen Tag. Auf der\n"
					+ "y-Achse wird der gesch�tzte verbleibende Aufwand aller Aufgaben des Sprints in Tagen angezeigt und auf der\n"
					+ "x-Achse die Anzahl der Arbeitstage.");
					
	private Tooltip	tooltipImpedimentBacklog	= new Tooltip(
			"Ein Impediment Backlog besteht aus den Eintragungen von Hindernissen, die das\n"
					+ "Entwicklungsteam an effektiver Arbeit hindern inklusive Datum des Auftretens und Datum der Behebung.");
					
	private Tooltip	tooltipProductBacklog		= new Tooltip(
			"Ein Product Backlog besteht aus User Stories, die vom Product Owner priorisiert werden.\n"
					+ "Die Aufw�nde der User Stories werden vom Entwicklungsteam gesch�tzt, beispielsweise in einer Sch�tzklausur.\n"
					+ "Eine User Story muss innerhalb eines Sprints realisierbar sein.\n"
					+ "Das Product Backlog ist nicht vollst�ndig und ver�ndert sich im Laufe des Projekts.\n"
					+ "Die Anforderungen k�nnen vom Kunden nach Bedarf ver�ndert werden. Es wird �blich priorisiert.\n"
					+ "Es d�rfen also mehr als eine User Story die Priorit�t 1 erhalten.\n"
					+ "Der Product Owner legt die Reihenfolge der User Stories fest. Die hoch priorisierten User Stories sollten, falls\n"
					+ "m�glich zuerst abgearbeitet werden. Daher wird im Sprint Planning Meeting nicht �ber die Reihenfolge der Abarbeitung\n"
					+ "der User Stories diskutiert sondern nur �ber die Anzahl.");
					
	private Tooltip	tooltipSprintBacklog		= new Tooltip(
			"Ein Sprint Backlog wird im Laufe des Sprints ver�ndert und entsteht beim Sprint Planung.\n"
					+ "Es enth�lt die User Stories, die in dem aktuellen Sprint bearbeitet werden.\n"
					+ "User Stories werden bez�glich ihrer technischen Anforderungen untersucht und in Tasks aufgeteilt,\n"
					+ "deren Aufwand (jetzt genauer) gesch�tzt wird. In einem Sprint sollen die Entwickler die Tasks\n"
					+ "zu den hoch priorisierten User Stories des Sprints zuerst abarbeiten.\n"
					+ "Gibt es mehr als eine User Story mit derselben Priorisierung, wird keine weitere Angabe\n"
					+ "zur Reihenfolge gemacht. Sind Tasks einer User Story in Bearbeitung und noch weitere\n"
					+ "Tasks dieser User Story vorhanden, so soll ein Entwickler, der ein neues Task zweck\n"
					+ "Bearbeitung aussucht, erst ein Task der aktuellen bearbeiteten User Story aussuchen.");
					
	private Tooltip	tooltipDoD					= new Tooltip(
			"Eine Definition of Done ist die Einigung eines agilen Teams darauf,\n"
					+ "was getan werden muss, damit ein Feature als fertig angesehen wird.");
					
	private Tooltip	tooltipWelcome				= new Tooltip(
			"Herzlich Willkommen zum ScrumBO! \nDiese Applikation erm�glicht es Ihnen Projektmanagement \nnach Scrum zu betreiben.");
			
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
