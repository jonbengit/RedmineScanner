package redmine.scanner.timeentry;

import javax.xml.bind.annotation.XmlAttribute;

class Issue {
	@XmlAttribute(name = "id")
	private int id;

	@Override
	public String toString() {
		return "Issue [id=" + id + "]";
	}

	int getId() {
		return id;
	}

}