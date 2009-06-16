# GitHub #

- Flexible..
- How does Devilry use it?
- Nice features:
	- gists
	- automatic html view of common markup languages.
		- We have choosen to use Markdown for readme and other *simple*
		  documents (like *this* file).
	- change files and commit the changes in the webinterface.
	- personal dashboard - easy to keep track of work on the project.


# Technologies #


- Maven
- Git
- JUnit
- OpenEJB
- Apache Geronimo (a full JavaEE server):
	- Servlets and JSP (tomcat or jetty)
	- EJB (openEJB)
	- Java Persistence API (OpenJPA (can use EclipseLink and Hibernate as well))
	- and much more.


# Editors #   	

- Works ok with both Eclipse and NetBeans, but both have some problems.
	- Eclipse get confused sometimes and you have to re-import.
	- Netbeans version 6.7RC2 has great Maven support, but it is slooooow and
	  crashes sometimes.
	- Both have git plugins, but the one in NetBeans seems to be unstable.
- We use Eclipse **3.5** for modeling. Just download the modeling tools edition,
  and install it separately from your *normal* eclipse install, and it works with
  very few problems. Note that the modeling editor is quite slow compared to
  commercial editors like Visual Paradigm, but it is the only opensource
  UML editor with full UML 2.1 support that we have found.
  

# Version control workflow #

- SVN style?
	- Scales poorly (i believe)
- Kernel style? (chosen)
	- Should work well for us with some tuning.
	- I believe this model is better especially with new/un-trusted developers. 


# Code style #

- Checkstyle - checks javadoc and code style.
- Some tuning from the official SUN guidelines.
	- We keep tab-indent with default width of 4 spaces.
	- Margin: 80


# Issue tracking #

- All new features should:
	1. Start a issue on the development repo at github.
	2. Implements the feature in its own branch.


# Where are we? #

- Core stabilizing --> still some work to be done on:
	- Authorization.
	- Client API (object oriented wrapper).
- We have made a proof-of-concept webclient (it worked without any problems)
- We have a *command-line interface*, but all work is suspended while we develop 
  the *client API*.
- How to handle delivery status?


# Hierarchy #

- See the figure in doc/modelling/classes/entities.uml.


# Authorization #

- No protection on:
    - getName() and getDisplayName()
    - getParent()
    - getStartDate() and getEndDate()
    - getDeadline()
- Is this a problem?


# Plugins #

- EJB3 timer services? 


# ID datatype #

- Keep long?
- Change to Object?


# Notes #

- Change Course to Subject.
