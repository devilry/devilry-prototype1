The pom.xml assumes you have glassfish installed in $HOME/prog/glassfish.

Compile and run with glassfish
==============================

(0. start glassfish with: mvn asadmin:start-domain)
(0.1. start glassfish database with: mvn asadmin:start-database)
1. Put beans in the core/ module
2. Put command-line applications using beans in the cli/ module.
3. Compile and install into the local maven repository with:
    ~$ mvn install
4. Deploy to glassfish with:
    ~$ cd core/; mvn asadmin:deploy; cd ../
5. Undeploy with: cd core/; mvn asadmin:undeploy; cd ../
5. Redeploy with: cd core/; mvn asadmin:redeploy; cd ../


Execute one of the CLI's
========================

~$ cd cli/
~$ mvn exec:java -Djava.mainclass="org.devilry.cli.RemoteClient"
~$ mvn exec:java -Djava.mainclass="org.devilry.cli.RemoteClient" -Dexec.args="arg1 arg2 arg3"
