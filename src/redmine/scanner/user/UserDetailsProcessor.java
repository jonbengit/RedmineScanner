package redmine.scanner.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Anil Kurian
 */
public class UserDetailsProcessor {

	/**
	 * Get the email IDs of only userIds from allUsers
	 * @param allUsers
	 * @param userIds
	 * @return
	 */
	public static List<String> getEmailsIds(List<User> allUsers,
			Set<Integer> userIds) {
		List<String> emailIds = new ArrayList<String>();
		for (User user : allUsers) {
			if (userIds.contains(user.getId())) {
				emailIds.add(user.getMail());
			}
		}
		return emailIds;
	}
}
