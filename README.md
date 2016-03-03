# RedmineScanner
Talks to Redmine over REST API & reads following 
* All projects available in Redmine, 
* Latest 300 time entries (assuming not more than 300 in a day) 
* All users in Redmine. 

Co-relates the time-entries for each projects with users in the project and prepares a report for each project. 
Finds out the email IDs of the members in the project and send the report for the project to all of them. 

This will be very useful for any teams which uses Redmine for their Project Management as it gives a nice tabular summary report of everything happened on a project in a day. Configure this as a Jenkins cron job and you have the report in your inbox everyday morning. 
