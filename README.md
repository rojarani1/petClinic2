petClinic2
==========

Pet Clinic using Spring 4, Spring Data JPA, and JSP heavily refactored and documented (with lots of my opinion but not necessarily correct)

How to pull down to eclipse
==========
Here's a youtube video to show you how: http://youtu.be/bQlk8W5wBKc?hd=1

Next Steps (let me know if you all have a preference)
==========
1) Get bootstrap 3 working. Right now the project has the necessary files but doesn't take advantage of them. Responsive Web Design (RWD) is a really hot topic and bootstrap is the best at RWD. RWD allows you to write a web app and then view it on a number of devices such as a pc, tablet and phone. Each device sees a slightly different version of the app even though we use the same jsp files. 

3) We can add a multi-step form to see how they work in spring

4) We can get logback fully function. Check out jhipster. It has 2 cool pages that let you configu the log files via a web page and also monitor system resources


... that's all I can think of now.

Work in progress
==========
I've taken the petClinic2 master branch and created the petClinic2-spring-security branch and added spring security to the application. Now, I'm going to take the petClini2-spring-security branch and create a petClini2-secured-bootStrap branch and update it with bootstrap 3

Completed in petClinic2-spring-security branch
==========
2) We can add spring security into the mix. Spring security will do 2 things for us. It'll let us log the user names into the log files so if a specific user is having trouble we can filter the log down to just the lines relating them them. And we can also enable auditing. We can capture which user and when they modify a record on the database. Spring security also allows for session management

5) Add an exception interceptor so that we get notified when an exception occurs before the help center even gets a call (I use this in all my web apps)
