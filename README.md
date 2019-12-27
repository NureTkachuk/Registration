# Registration
## Testing Project
Spring Boot Application with Gradle building. This application is for registration and authorization for users.


# Comments

When projects starts it is possible to get to home page by path: "http://localhost:8080/". 
Here you see home page where you can choose user or admin variant of page:

![Image alt](https://github.com/AlexTkachukPC/Registration/raw/master/images/home.png)

If you try to click on user or admin, server asks you to LogIn:

![Image alt](https://github.com/AlexTkachukPC/Registration/raw/master/images/login.png)


![Image alt](https://github.com/AlexTkachukPC/Registration/raw/master/images/register.png)


After authorization you get user or admin page depends on your choose.

USER PAGE:

![Image alt](https://github.com/AlexTkachukPC/Registration/raw/master/images/userPage.png)

ADMIN PAGE:

![Image alt](https://github.com/AlexTkachukPC/Registration/raw/master/images/adminPage.png)


If you are a user what server only allows you is just see all of registered users and administators.

If you are a admin, server allows you see all users and modify their data(## EXCEPT PASSWORD!) or delete users.

![Image alt](https://github.com/AlexTkachukPC/Registration/raw/master/images/editPage.png)



## USED FRAMEWORKS:

Template: Mustache
Data: JPA, PostgreSQL, Hibernate
Build: Gradle
Main Framework: Spring Boot



# Get Started with Project

1. Download project
2. Open it with your favorite IDE
3. Go to application.properties file and set login and password from database and set database in datasource.url.
4. Enjoy project. 

# !!!You will find two persons in default. Admin (login: admin; pass: admin) and User (login: user; pass: user).


