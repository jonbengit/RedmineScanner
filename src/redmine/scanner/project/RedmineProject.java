package redmine.scanner.project;

import javax.xml.bind.annotation.XmlElement;

class RedmineProject {

	@XmlElement(name = "id")
	private int projectId;

	@XmlElement(name = "name")
	private String projectName;

	@XmlElement(name = "identifier")
	private String identifier;

	@XmlElement(name = "description")
	private String projectDesc;

	int getProjectId() {
		return projectId;
	}

	String getProjectName() {
		return projectName;
	}

	String getProjectDesc() {
		return projectDesc;
	}

	String getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		return "RedmineProject [projectId=" + projectId + ", projectName="
				+ projectName + ", identifier=" + identifier + ", projectDesc="
				+ projectDesc + "]";
	}

}