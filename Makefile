runExampleClient:
	@echo "****** Building"
	mvn install
	@echo "****** Deploying the bean"
	cd core/; mvn asadmin:redeploy; cd ../
	@echo "****** Running org.devilry.cli.RemoteClient"
	cd cli/; mvn exec:java -Djava.mainclass="org.devilry.cli.ExampleClient" -Dexec.args="test"; cd ../
