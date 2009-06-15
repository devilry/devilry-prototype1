# GitHub #

- Flexible..
- How does Devilry use it?
- Nice features:
	- gists
	- automatic html view of common markup languages.
		- We have choosen to use Markdown for readme and other *simple*
		  documents (like *this* file).
	- change files and commit the changes in the webinterface.


# Tools #


- Maven
- Git
- JUnit
- OpenEJB
- Apache Geronimo (a full JavaEE server):
	- Servlets and JSP (tomcat or jetty)
	- EJB (openEJB)
	- Java Persistence API (OpenJPA (can use EclipseLink and Hibernate as well))
	- and much more..
- Works ok with both Eclipse and NetBeans, but both have some problems.


# Version control workflow #


- Kernel style?
- SVN style?


# Code style #

- Checkstyle
- Some tuning from the official SUN guidelines.


# Status? #

- Core stabilizing --> still some work to be done on:
	- Authorization.
	- Client API (object oriented wrapper).
- We have made a proof-of-concept webclient (it worked without any problems)
- We have a *command-line interface*, but all work is suspended while we develop 
  the *client API*.


# Hierarchy #


- Figure (fix with users)


# Authorization #

- No protection on:
    - getName() and getDisplayName()
    - getParent()
    - getStartDate() and getEndDate()
    - getDeadline()
- Is this a problem?


# ID datatype #

- Keep long?
- Change to Object?
