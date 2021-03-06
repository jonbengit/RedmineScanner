package redmine.scanner.timeentry;

import javax.xml.bind.annotation.XmlAttribute;

class Activity {

	@XmlAttribute(name = "id")
	private int id;

	@XmlAttribute(name = "name")
	private String name;

	int getId() {
		return id;
	}

	String getName() {
		return name == null ? "" : name;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + "]";
	}

}