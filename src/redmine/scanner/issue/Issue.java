package redmine.scanner.issue;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Issue {

	@XmlElement(name = "id")
	private int id;

	@XmlElement(name = "subject")
	private String subject;

	@XmlElement(name = "tracker")
	private Tracker tracker;

	@XmlElement(name = "status")
	private Status status;

	@XmlElement(name = "done_ratio")
	private String doneRatio;

	private static class Tracker {
		@XmlAttribute(name = "name")
		private String name;

		String getName() {
			return name;
		}
	}

	private static class Status {
		@XmlAttribute(name = "name")
		private String name;

		String getName() {
			return name;
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	public String getTracker() {
		return tracker.getName();
	}

	public String getStatus() {
		return status.getName();
	}

	public String getDoneRatio() {
		return doneRatio;
	}

}
