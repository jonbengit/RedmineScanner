package redmine.scanner.timeentry;

import javax.xml.bind.annotation.XmlAttribute;

class User {

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
		return "User [id=" + id + ", name=" + name + "]";
	}

}