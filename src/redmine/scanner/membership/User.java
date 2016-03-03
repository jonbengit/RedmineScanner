package redmine.scanner.membership;

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
		return "\nUser [name=" + name + ", id=" + id + "]";
	}
}