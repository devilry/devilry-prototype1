About milestone core-prototype2
===============================

The initial goal for this milestone was to:

* Basic node tree.
* Create bean for accepting assignments.
* Server-side bean for handling assignments.
* Update the CLI to use JAAS.


Basic node tree
---------------

In this milestone release we have added our node structure which has been in
development for some time. There are still some issues, but the frontend 
works and we will continue to work on the backend.

This issue has been reported here [Devilry Issue Tracker][1].

[1]: http://github.com/espenak/devilry/issues#issue/1:


Create bean for accepting assignments
-------------------------------------

We have not only finished a bean which can accept assignments, we have
connected the node-tree with assignment delivery and made a fully working
delivery hierarchy of entity nodes and DAO (data access object) beans.
This puts us far closer to the next milestone than planned.


Server-side bean for handling assignments
-----------------------------------------

This is covered by the previous section.


Not in the milestone plan
-------------------------

We have made a working junit4 test-environment with Apache OpenEJB. The ease of
development with openejb over glassfish (better error messages, embeddable and
very configurable) has lead us to reconsider using glassfish as our javaEE
server. We will probably make a core which is independent of the javaEE server,
but as things look today we will most likely develop the core for *Apache
OpenEJB* and *Apache Geronimo* (which uses openejb).


Update the CLI to use JAAS
--------------------------

Part of the requirements for this milestone was to have a working CLI which
had authentification using JAAS. But since this functionality is supposed to
build on the Node-tree (UserNode and GroupNode) this has yet to be implemented.

We have testcases for JAAS in our [devilry-sandbox][2].

[2]: http://github.com/espenak/devilry-sandbox/tree/eda5253cebd0f11f0b3a1d7c282f6e3868a474c4/ejb/nodemgr-bean


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


 
'mvn test' in the root directory to compile and test the devilry core.


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