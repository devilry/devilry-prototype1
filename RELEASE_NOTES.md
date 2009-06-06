Release notes for M5 (the latest release)
=========================================


Stabilize the core api
----------------------

We have stabilized the core api to a point where we feel there will be very
litte additional changes required except for the one related to the next section.


Add JAAS security to the DAO classes
------------------------------------

We have implemented everything required for a working authorization scheme.
Authentication works, and we have a relationship between *security principal* on
the javaEE server and *identity* in our core. Every entity in the core has
relationships to the required user-types, and the DAO classes supports
all required operations concerning users. The only thing missing is the
implementation of an default authorization-interceptor, and this is
targeted for the next milestone.


Make a CLI with JAAS support which supports all operations exposed by the core DAO classes
------------------------------------------------------------------------------------------

The client support authentication, and it exposes most of the functionality
required by a student. We decided that we needed another layer of abstraction
for clients, one that does not require knowledge of javaEE, so we chose to
work on this client-api instead of a fully functional CLI. The client
api is well underway, and we have targeted it for our next milestone.


Other stuff
-----------

We have implemented a *proof of concept* webclient using servlets and jsp.
The webclient and the *command line client* do not compile right now because of
changes to the core api. None of them will be fixed before the client api is
finished.
