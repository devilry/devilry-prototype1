WARNING
=======

This prototype of the devilry core has *been abandoned* because of problems with
JavaEE implementations. JavaEE is a great standard, but after much research we
decided that a JavaEE was to complex, restricted and fragmented (implentations)
for our use.

This made us consider other approaches, and we ported the core to Django
(http://djangoproject.org). Out current work in on the devilry-django
repository (http://github.com/devilry/devilry-django).


Usage
=====

Testing the core
-----------------

To compile and run the client you need:

* Linux or OSX (Windows hasn't been tested, but should work)
* Maven
* git (http://git-scm.com/)



Git read only repository:
git://github.com/espenak/devilry.git


 
`mvn test` in the root directory to compile and test the devilry core.



Testing the client
-------------------

To test the client, you need to download OpenEJB Standalone Server as well. 
Unpack and place the bin directory in the path and start or stop the server with:
'openejb start|stop'

'mvn install' to create the core package and deploy with 'openejb deploy core/target/core-0.1.jar'

run client shell with from the cli directory with 'mvn exec:java'

On first run, a default hierarchy must be created (from within the shell) 
using the command:
'addtestnodes'

Then use the commands avaialable in the shell.


Creating the website
====================

We use maven site to manage project documentation. The wiki on github is used for community
documentation like guides for special cases, setup of development environments and such.

To build the website into `target/deployable-site/`, use this command (CWD must be the source/ directory):

	~$ mvn clean site-deploy -Pno-reports


Why clean?
----------

The use of *clean* is important because without clean, the *site* target does not seem to update content.



Publish the site to devilry.github.com
======================================

One-time setup
--------------

If you have not yet checked out the *devilry.github.com* repository, do so with
(CWD must be the source/ directory):

	~$ cd ../
	~$ git clone <your clone url>

note that the repository must be in the *devilry.github.com/* directory with the same parent-dir as *this* repository.


Publish the website
-------------------

To publish the website, you must add files to the *devilry.github.com* repository, and push github. Because we
use maven to generate our website, we have to generate the site *into* the *devilry.github.com* repository and
commit+push the changes with these commands (CWD must be the source/ directory):

	~$ mvn clean site-deploy
	~$ cd ../devilry.github.com/
	~$ git add .
	~$ git commit -a -m "Updated the website."
	~$ git push
