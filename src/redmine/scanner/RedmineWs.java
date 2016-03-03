package redmine.scanner;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import redmine.scanner.issue.Issue;
import redmine.scanner.membership.Memberships;
import redmine.scanner.project.Projects;
import redmine.scanner.timeentry.TimeEntries;
import redmine.scanner.user.Users;

/**
 * Interfacing to Redmine REST WS API.
 * <p>
 * The XML response from REST API are converted to Java objects using JAXB.
 * These objects are defined in separate packages with one public class in each
 * package. Other classes in the packages are used by only the public class in
 * the package.
 * <p>
 * The Redmine WS will return only a max of 25 entries per WS call. Using the
 * "limit" parameter it can be extended to a max of 100. If you need to get >100
 * entries you need to make multiple WS calls, each time increasing the
 * "offset".
 * 
 * @author Anil Kurian
 */
public class RedmineWs {

	private WebResource redmineWebResource;
	
	RedmineWs() throws RedmineScannerException {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter(ScannerConfig.WS_USER, ScannerConfig.PASSWORD));
		redmineWebResource = client.resource(getBaseURI());
	}

	/**
	 * Get all the projects in Redmine
	 * Hoping there not more than 100.
	 * @return
	 */
	Projects getAllRedmineProjects() {
		return redmineWebResource.path("projects.xml").queryParam("limit", "100").get(Projects.class);
	}

	/**
	 * Get all the memberships for a project
	 * 
	 * @param projectId
	 * @return
	 */
	Memberships getMemberships(Integer projectId) {
		return redmineWebResource.path(
				"projects/" + projectId + "/memberships.xml").get(
				Memberships.class);
	}

	/**
	 * Get all users in Redmine, assuming <100 users.
	 * 
	 * @return
	 */
	Users getAllUsers() {
		return redmineWebResource.path("users.xml").queryParam("limit", "100")
				.get(Users.class);
	}

	/**
	 * Get the details of a particular issue
	 * 
	 * @param id
	 * @return
	 */
	Issue getIssue(Integer id) {
		return redmineWebResource.path("issues/" + id + ".xml")
				.get(Issue.class);
	}

	/**
	 * Get the latest 300 time entries in Redmine, latest by logged time.
	 * Assuming not more than 300 log entries in a day. This is required till
	 * this WS API supports passing a date as parameter to it.
	 * <p>
	 * Like <code>GET /time_entries.xml?logged_on=2012-03-01</code>
	 * 
	 * @see http://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries
	 * @see http://www.redmine.org/projects/redmine/wiki/Rest_Issues
	 * 
	 * @return Latest 300 time entries in Redmine.
	 */
	TimeEntries readTimeEntries() {

		TimeEntries timeEntries = redmineWebResource.path("time_entries.xml")
				.queryParam("limit", "100").get(TimeEntries.class);
		TimeEntries next100Entries = redmineWebResource
				.path("time_entries.xml").queryParam("limit", "100")
				.queryParam("offset", "100").get(TimeEntries.class);
		TimeEntries nextToNext100Entries = redmineWebResource
				.path("time_entries.xml").queryParam("limit", "100")
				.queryParam("offset", "200").get(TimeEntries.class);
		
		timeEntries.addTimeEntries(next100Entries.getTimeEntries());
		timeEntries.addTimeEntries(nextToNext100Entries.getTimeEntries());
		return timeEntries;
	}


	
	static String getRedmineUrl() {
		return ScannerConfig.REDMINE_URL;
	}

	private URI getBaseURI() throws RedmineScannerException {
		String redmineUrl = getRedmineUrl();
		if(null == redmineUrl || redmineUrl.isEmpty()) {
			throw new RedmineScannerException("Redmine URL is empty");
		}
		return UriBuilder.fromUri(redmineUrl).build();
	}

}
