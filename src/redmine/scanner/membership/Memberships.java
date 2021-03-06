package redmine.scanner.membership;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Memberships {

	@XmlElement(name = "membership")
	private List<Membership> memberships;

	public List<Membership> getUsers() {
		return memberships;
	}

	/**
	 * @return user ids of the project
	 */
	public Map<Integer, String> getUserIds() {
		Map<Integer, String> userIds = new HashMap<Integer, String>();
		for (Membership membership : memberships) {
			userIds.put(membership.getUser().getId(), membership.getUser()
					.getName());
		}
		return userIds;
	}

	@Override
	public String toString() {
		return "Memberships [users=" + memberships + "]";
	}

}
