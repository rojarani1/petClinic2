petClinic2
==========

Pet Clinic using Spring 4, Spring Data JPA, and JSP heavily refactored and documented (with lots of my opinion but not necessarily correct)


What I've done
==========
I've accomplished my original goals of securing the application with spring security. I have a few more security related changes that I'd like to do but for now I'm going to move on and make the app look pretty using bootstrap 3.

Here are the tasks that I accomplished from my original goals:

2) We can add spring security into the mix. Spring security will do 2 things for us. It'll let us log the user names into the log files so if a specific user is having trouble we can filter the log down to just the lines relating them them. And we can also enable auditing. We can capture which user and when they modify a record on the database. Spring security also allows for session management

5) Add an exception interceptor so that we get notified when an exception occurs before the help center even gets a call (I use this in all my web apps)
