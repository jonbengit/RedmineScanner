package redmine.scanner.timeentry;

import javax.xml.bind.annotation.XmlElement;

public class TimeEntry {

	@XmlElement(name = "id")
	private int id;

	@XmlElement(name = "issue")
	private Issue issue;

	@XmlElement(name = "comments")
	private String comments;

	@XmlElement(name = "spent_on")
	private String spentOn;

	@XmlElement(name = "hours")
	private String hours;

	@XmlElement(name = "project")
	private Project project;

	@XmlElement(name = "user")
	private User user;

	@XmlElement(name = "activity")
	private Activity activity;

	int getId() {
		return id;
	}

	Issue getIssue() {
		return issue;
	}

	String getComments() {
		return comments;
	}

	String getSpentOn() {
		return spentOn;
	}

	String getHours() {
		return hours;
	}

	Project getProject() {
		return project;
	}

	User getUser() {
		return user;
	}

	int getProjectId() {
		return project.getId();
	}

	int getUserId() {
		return user.getId();
	}

	public int getIssueId() {
		return issue.getId();
	}

	boolean equals(int userId) {
		return (userId == getUserId());
	}

	boolean equals(int projectId, String spentOn) {
		return (projectId == getProjectId() && spentOn.equals(getSpentOn()));
	}

	Activity getActivity() {
		return activity;
	}

	@Override
	public String toString() {
		return "\nTimeEntry [id=" + id + ", issue=" + issue + ", comments="
				+ comments + ", spentOn=" + spentOn + ", hours=" + hours
				+ ", project=" + project + ", user=" + user + "]";
	}

	public String getDetails() {
		return hours + " Hours on " + activity.getName()
				+ " Issue <a href='http://redmine.sanovi.com:3000/issues/"
				+ issue.getId() + "'>" + issue.getId() + "</a>" + "."
				+ comments;
	}

	public String getDetails(String issueTitle) {
		if (issueTitle.length() > 50) {
			issueTitle = issueTitle.substring(0, 50) + "...";
		}
		return hours + " Hours on " + activity.getName() + " Issue "
				+ issue.getId()
				+ " : <a href='http://redmine.sanovi.com:3000/issues/"
				+ issue.getId() + "'>" + issueTitle + "</a>" + ". " + comments;
	}
}