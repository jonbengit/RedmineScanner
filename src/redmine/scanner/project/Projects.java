package redmine.scanner.project;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Projects {

	@XmlElement(name = "project")
	private List<RedmineProject> redmineProjects;

	public List<RedmineProject> getRedmineProjects() {
		return redmineProjects;
	}

	public Map<Integer, String> getProjects() {
		Map<Integer, String> projectIdentifiers = new LinkedHashMap<Integer, String>();
		for (RedmineProject redmineProject : redmineProjects) {
			projectIdentifiers.put(redmineProject.getProjectId(),
					redmineProject.getProjectName());
		}
		return projectIdentifiers;
	}

	@Override
	public String toString() {
		return "RedmineProjects [redmineProjects=" + redmineProjects + "]";
	}

}
