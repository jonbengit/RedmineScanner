package redmine.scanner;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import redmine.scanner.utils.RedmineScannerLogger;

/**
 * Represents everything that is required to send a mail for a project. Also
 * sends the email.
 * 
 * @author AnilKurian
 */
public class RedmineMailDetails {

	private String projectName;
	private String titleOfDay;
	private Float totalTimeOnThisProject;
	private String projectInfoDetails;
	private List<String> emailsIds;

	RedmineMailDetails() {

	}

	void sendMail() {
		RedmineScannerMail mail = new RedmineScannerMail();
		mail.setReceiprients(emailsIds);
		mail.setContent(projectInfoDetails);
		mail.setSubject(getEmailSubject());
		RedmineScannerLogger.getInstance().log("Sending mail for " + projectName + "... ");
		mail.sendMail();
	}

	void createFile() {
		try {
			String fileName = projectName + ".html";
			File file = new File(fileName);
			RedmineScannerLogger.getInstance().log("Creating file " + fileName + "... ");
			file.createNewFile();
			FileWriter writter = new FileWriter(file);
			writter.write("SUBJECT " + getEmailSubject());
			writter.write("<br> CONTENTS " + projectInfoDetails);
			writter.write("<br><br> RECEIPIENTS " + emailsIds);
			writter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	void setTitleOfDay(String titleOfDay) {
		this.titleOfDay = titleOfDay;
	}

	void setTotalTimeOnThisProject(Float totalTimeOnThisProject) {
		this.totalTimeOnThisProject = totalTimeOnThisProject;
	}

	void setProjectInfoDetails(String projectInfoDetails) {
		this.projectInfoDetails = projectInfoDetails;
	}

	void setEmailsIds(List<String> emailsIds) {
		this.emailsIds = emailsIds;
	}

	private String getEmailSubject() {
		return projectName + " logged  [" + totalTimeOnThisProject
				+ " Hrs] on " + titleOfDay;
	}
}