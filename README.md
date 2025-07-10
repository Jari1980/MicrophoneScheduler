This application is created for Teaterstickorna as a part of Lexicon Växjö fullstackdeveloper course.
The main goal of this application is to provide a easy to use microphone scheduler.

Users can have any of three roles, Admin create and manage theater productions, users and roles. Director overview of productions and ability to make modifications. Actor get microphoneschedule for production, ability to comment any scene which can be seen by director and admin, also ability to check fellow actors schedules.

Application will seed a superAdmin user with password 1234. For first time use, create a new admin user, log in as superAdmin and promote the new user to admin whereafter the seeded superAdmin can be deleted.

In order to run this you need to create environment variable
name it JWT_SECRET and set a password which will be used for token.

Application is configured for mySql.
