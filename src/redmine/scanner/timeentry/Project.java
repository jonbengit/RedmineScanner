package redmine.scanner.timeentry;

import javax.xml.bind.annotation.XmlAttribute;

class Project {

	@XmlAttribute(name = "id")
	private int id;

	@XmlAttribute(name = "name")
	private String name;

	int getId() {
		return id;
	}

	String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + "]";
	}

}