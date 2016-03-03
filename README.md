# RedmineScanner
Talks to Redmine over REST API & reads following 
* All projects available in Redmine, 
* Latest 300 time entries (assuming not more than 300 in a day) 
* All users in Redmine. 

Co-relates the time-entries for each projects with users in the project and prepares a report for each project. 
Finds out the email IDs of the members in the project and send the report for the project to all of them. 

 This is not developed to achieve the best performance using the best possible data structure to represent the Redmine data. 
 The functionality is given more importance and can be configured in Jenkins to run once in every morning.

