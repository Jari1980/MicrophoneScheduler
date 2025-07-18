<h1>MicrophoneScheduler - Backend</h1>

<i>This application is created for Teaterstickorna as a part of Lexicon Växjö fullstackdeveloper course.
The main goal of this application is to provide a easy to use microphone scheduler.</i>

Users can have any of three roles, Admin create and manage theater productions, users and roles. Director overview of productions and ability to make modifications. Actor get microphoneschedule for production, ability to comment any scene which can be seen by director and admin, also ability to check fellow actors schedules.

API endpoints are divided in three controllers, ActorController, AdminController and UserController. Each method in controller have a brief comment what they do and where they are used in frontend. Logic for the methods are mainly done in respective service with use of DTO's.
Custom exception handling is done with start of error 490 which are passed and used in frontend.
All repositories extends JpaRepository.

Application will seed a superAdmin user with password 1234. For first time use, create a new admin user, log in as superAdmin and promote the new user to admin whereafter the seeded superAdmin can be deleted.
Access to all http endpoint is set in SecurityConfig.java, with all acces or actor/director/administrator specific or combination of these roles.

In order to run this you need to create environment variable name it JWT_SECRET and set a password (sha256) which will be used for token. JWT token is set to expire in (1000 * 60 * 60 * 12) 12 hours, this is set it JwtUtil.java. 
This project use Lombok and the annotation processor should be obtained from project classpath (if you face error here the processor path is most likely set and you need to check "Obtain processors from project classpath" instead)
Application is configured for mySql and this need to be configured for your settings aswell.
