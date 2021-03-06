package redmine.scanner.membership;

import javax.xml.bind.annotation.XmlElement;

class Membership {

	@XmlElement(name = "user")
	private User user;

	User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Membership [user=" + user + "]";
	}

}