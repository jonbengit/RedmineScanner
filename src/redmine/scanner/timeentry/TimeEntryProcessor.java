package redmine.scanner.timeentry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Anil Kurian
 */
public class TimeEntryProcessor {

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * To get the sub list of time entries from allTimeEntries which are
	 * belonging to the projectId and logged on the date
	 * 
	 * @param allTimeEntries
	 * @param projectId
	 * @param date
	 * @return
	 */
	public static List<TimeEntry> getTimeEntriesOfProject(
			List<TimeEntry> allTimeEntries, int projectId, Calendar date) {

		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

		String requiredDate = formatter.format(date.getTime());

		for (TimeEntry timeEntry : allTimeEntries) {
			if (timeEntry.equals(projectId, requiredDate)) {
				timeEntries.add(timeEntry);
			}
		}
		return Collections.unmodifiableList(timeEntries);
	}

	/**
	 * To get the sub list of time entries from allTimeEntries which are logged
	 * by userId.
	 * 
	 * @param allTimeEntries
	 * @param userId
	 * @return
	 */
	public static List<TimeEntry> getTimeEntriesOfUser(
			List<TimeEntry> allTimeEntries, int userId) {

		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

		for (TimeEntry timeEntry : allTimeEntries) {
			if (timeEntry.equals(userId)) {
				timeEntries.add(timeEntry);
			}
		}
		return Collections.unmodifiableList(timeEntries);
	}

	/**
	 * Total time logged in by all the timeEntries
	 * 
	 * @param timeEntries
	 * @return
	 */
	public static Float totalTime(List<TimeEntry> timeEntries) {

		float totalHours = 0;

		for (TimeEntry timeEntry : timeEntries) {
			String hours = timeEntry.getHours();
			try {
				totalHours = totalHours + Float.parseFloat(hours);
			} catch (Exception e) {
				System.err.println(e.getMessage() + " while parsing "
						+ timeEntry);
				e.printStackTrace();
			}
		}

		return totalHours;

	}
}
