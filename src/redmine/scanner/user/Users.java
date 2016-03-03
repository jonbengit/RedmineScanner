package redmine.scanner.user;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Users {

	@XmlElement(name = "user")
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	@Override
	public String toString() {
		return "Users [users=" + users + "]";
	}

}
