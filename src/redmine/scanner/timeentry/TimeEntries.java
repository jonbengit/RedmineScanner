package redmine.scanner.timeentry;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "time_entries")
public class TimeEntries {

	@XmlElement(name = "time_entry")
	private List<TimeEntry> timeEntries;

	public List<TimeEntry> getTimeEntries() {
		return timeEntries;
	}

	public void addTimeEntries(List<TimeEntry> newTimeEntries) {
		timeEntries.addAll(newTimeEntries);
	}

	@Override
	public String toString() {
		return "TimeEntries [timeEntries=" + timeEntries + "]";
	}
}
