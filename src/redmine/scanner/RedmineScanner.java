package redmine.scanner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redmine.scanner.issue.Issue;
import redmine.scanner.membership.Memberships;
import redmine.scanner.project.Projects;
import redmine.scanner.timeentry.TimeEntries;
import redmine.scanner.timeentry.TimeEntry;
import redmine.scanner.timeentry.TimeEntryProcessor;
import redmine.scanner.user.UserDetailsProcessor;
import redmine.scanner.user.Users;


/**
 * Talks to Redmine over REST API, reads following <li>All projects
 * available in Redmin, <li>Latest 300 time entries (assuming not more than 300
 * in a day) <li>All users in Redmine.
 * <p>
 * <br>
 * Co-relates the time-entries for each projects with users in the project and
 * prepares a report for each project. Finds out the email IDs of the members in
 * the project and send the report for the project to all of them.
 * <p>
 * <br>
 * This is not developed to acheive the best performance using the best possible
 * data structure to represent the Redmine data. The functionality is given more
 * importance and currenlty configured in Jenkins to run once in every morning.
 * 
 * @author Anil Kurian
 * @since 22 Feb 2012
 */
public class RedmineScanner {

	private RedmineWs redmineWs = new RedmineWs();

	private Map<Integer, String> redmineProjects;

	private TimeEntries timeEntries;

	private float totalTimeOnAllProjects;

	private Calendar dayToProcess;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	private Users allUsers;

	/**
	 * if set we will e-mail+HTML otherwise generate only HTML files in CWD
	 */
	private boolean sendEmail =  Boolean.valueOf(System.getenv("EMAIL"));

	public static void main(String[] args) {
		System.out.println("Redmine Scanner is starting...");
		try {
			RedmineScanner scanner = new RedmineScanner();
			scanner.findLwd();
			scanner.readTimeEntries();
			scanner.readProjects();
			scanner.readAllUsers();
			scanner.processTimeEntries();
			System.out
					.println("\n\n== Total time on all projects logged yesterday "
							+ scanner.totalTimeOnAllProjects + " ==");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readTimeEntries() {
		timeEntries = redmineWs.readTimeEntries();
	}

	private void readProjects() {
		Projects projects = redmineWs.getAllRedmineProjects();
		redmineProjects = projects.getProjects();
		System.out.println("Redmine projects are "+redmineProjects);
	}

	private void readAllUsers() {
		allUsers = redmineWs.getAllUsers();
	}

	private void processTimeEntries() {

		Set<Integer> projectIds = redmineProjects.keySet();

		for (Integer projectId : projectIds) {
			projectProjectTimeEntries(projectId);
		}
	}

	private void projectProjectTimeEntries(Integer projectId) {
		try {

			String projectName = redmineProjects.get(projectId);

			if (Filter.NO_PROJECTS.contains(projectName)) {
				return;
			}

			RedmineMailDetails mailDetails = new RedmineMailDetails();
			mailDetails.setProjectName(projectName);

			StringBuilder projectInfo = new StringBuilder();

			String titleOfDay = getDayTitle(dayToProcess);
			mailDetails.setTitleOfDay(titleOfDay);
			projectInfo.append("<font face='Arial' style='font-size: 10pt'>");
			projectInfo.append("<b>Updates on " + titleOfDay + " for "
					+ projectName + " </b><br><br>");

			List<TimeEntry> timeEntriesOnProject = TimeEntryProcessor
					.getTimeEntriesOfProject(timeEntries.getTimeEntries(),
							projectId, dayToProcess);

			Float totalTimeOnThisProject = TimeEntryProcessor
					.totalTime(timeEntriesOnProject);

			mailDetails.setTotalTimeOnThisProject(totalTimeOnThisProject);
			projectInfo.append("Total time logged: <b>"
					+ totalTimeOnThisProject + "</b> Hrs <br>");

			addToOverallTime(totalTimeOnThisProject);

			Map<Integer, String> usersInProject = getMembersInProject(projectId);

			String membersContrib = processUserTimeEntries(projectId,
					timeEntriesOnProject, usersInProject);

			projectInfo.append(membersContrib);
			projectInfo.append("</font>");

			mailDetails.setProjectInfoDetails(projectInfo.toString());

			List<String> emailsIds = UserDetailsProcessor.getEmailsIds(
					allUsers.getUsers(), usersInProject.keySet());

			emailsIds.removeAll(Filter.NO_EMAILS);

			mailDetails.setEmailsIds(emailsIds);

			if (sendEmail) {
				mailDetails.sendMail();
			}
			mailDetails.createFile();

		} catch (Exception e) {
			e.printStackTrace();
			// lets continue with other projects
		}
	}

	private String processUserTimeEntries(Integer projectId,
			List<TimeEntry> timeEntriesOnProject,
			Map<Integer, String> usersInProject) {
		StringBuilder membersContrib = new StringBuilder();
		for (Integer userId : usersInProject.keySet()) {

			String userName = usersInProject.get(userId);

			if (Filter.NO_UPDATES.contains(userName)) {
				continue;
			}

			List<TimeEntry> timeEntriesByUser = TimeEntryProcessor
					.getTimeEntriesOfUser(timeEntriesOnProject, userId);

			Float totalTimeByUser = TimeEntryProcessor
					.totalTime(timeEntriesByUser);

			membersContrib.append(getTab()
					+ getUserNameWithLink(userName, userId) + " logged "
					+ totalTimeByUser + " Hrs <br>");
			for (TimeEntry timeEntry : timeEntriesByUser) {
				String timeEntryOnIssue = getTimeEntryOnIssue(timeEntry);
				membersContrib.append(timeEntryOnIssue);
			}

		}
		return membersContrib.toString();
	}

	private String getTimeEntryOnIssue(TimeEntry timeEntry) {
		Issue issue = redmineWs.getIssue(timeEntry.getIssueId());
		String issueTitle = issue.getSubject();
		String timeEntryOnIssue = getTab() + getTab()
				+ timeEntry.getDetails(issueTitle) + ". <font face='Arial' style='font-size: 8pt'>" + issue.getStatus()
				+ " " + issue.getTracker() + " " + issue.getDoneRatio()
				+ "% done.</font><br>";
		return timeEntryOnIssue;
	}

	private String getUserNameWithLink(String userName, Integer userId) {
		return "<a href=" + RedmineWs.getRedmineUrl() + "users/" + userId + ">"
				+ userName + "</a>";
	}

	private Map<Integer, String> getMembersInProject(Integer projectId) {
		Memberships projectMemberships = redmineWs.getMemberships(projectId);
		return projectMemberships.getUserIds();
	}

	private void addToOverallTime(Float totalTimeOnThisProject) {
		totalTimeOnAllProjects = totalTimeOnAllProjects
				+ totalTimeOnThisProject;
	}

	private String getDayTitle(Calendar day) {
		return formatter.format(day.getTime());
	}

	private void findLwd() {
		Calendar lastWorkingDay = Calendar.getInstance();
		if (Calendar.SATURDAY == lastWorkingDay.get(Calendar.DAY_OF_WEEK)
				|| Calendar.SUNDAY == lastWorkingDay.get(Calendar.DAY_OF_WEEK)) {
			System.out.println("Not running on weekends...");
			System.exit(0);
		}
		if (Calendar.MONDAY == lastWorkingDay.get(Calendar.DAY_OF_WEEK)) {
			lastWorkingDay.add(Calendar.DATE, -3); // moving to last friday
		} else {
			lastWorkingDay.add(Calendar.DATE, -1);
		}
		dayToProcess = lastWorkingDay;
	}

	private static String getTab() {
		return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}

}
