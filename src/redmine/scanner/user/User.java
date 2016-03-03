package redmine.scanner.user;

import javax.xml.bind.annotation.XmlElement;

class User {
	@XmlElement(name = "id")
	private int id;

	@XmlElement(name = "login")
	private String login;

	@XmlElement(name = "firstname")
	private String firstName;

	@XmlElement(name = "firstname")
	private String lastName;

	@XmlElement(name = "mail")
	private String mail;

	int getId() {
		return id;
	}

	String getLogin() {
		return login;
	}

	String getFirstName() {
		return firstName;
	}

	String getLastName() {
		return lastName;
	}

	String getMail() {
		return mail;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", firstName="
				+ firstName + ", lastName=" + lastName + ", mail=" + mail + "]";
	}

}