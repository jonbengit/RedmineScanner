package redmine.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This call reads following env variables which can be set for running the
 * Redmine Scanner.
 * 
 * <li>NO_PROJECT = list of projects for which we don't need to track <li>
 * NO_EMAILS = list of persons for which e-mails need not to be sent <li>
 * NO_UPDATES = list of persons for which hours/updates need not be tracked
 * 
 * @author Anil Kurian
 */
public class Filter {

	public static List<String> NO_UPDATES = new ArrayList<String>();

	public static List<String> NO_EMAILS = new ArrayList<String>();

	public static List<String> NO_PROJECTS = new ArrayList<String>();

	static {

		String noProjects = System.getenv("NO_PROJECTS");
		if (noProjects != null) {
			StringTokenizer tokenizer = new StringTokenizer(noProjects, ",");
			while (tokenizer.hasMoreTokens()) {
				NO_PROJECTS.add(tokenizer.nextToken());
			}
			System.out.println("***** Not scanning projects " + NO_PROJECTS);
		}

		String noUpdates = System.getenv("NO_UPDATES");
		if (noUpdates != null) {
			StringTokenizer tokenizer = new StringTokenizer(noUpdates, ",");
			while (tokenizer.hasMoreTokens()) {
				NO_UPDATES.add(tokenizer.nextToken());
			}
		}
		System.out.println("***** Not scanning " + NO_UPDATES);

		String noEmails = System.getenv("NO_EMAILS");
		if (noEmails != null) {
			StringTokenizer tokenizer = new StringTokenizer(noEmails, ",");
			while (tokenizer.hasMoreTokens()) {
				NO_EMAILS.add(tokenizer.nextToken());
			}
		}
		System.out.println("***** Not emailing " + NO_EMAILS);

	}

}
