CORE_NAME=core-0.1

start:
	@echo "###########################################"
	@echo "Starting glassfish"
	@echo "###########################################"
	asadmin start-domain
	asadmin start-database --dbhost localhost

stop:
	@echo "###########################################"
	@echo "Stopping glassfish"
	@echo "###########################################"
	asadmin stop-database --dbhost localhost
	asadmin stop-domain

restart: stop start


install:
	@echo "###########################################"
	@echo "****** Build and install all"
	@echo "###########################################"
	mvn install

redeploy: undeploy deploy

deploy: install
	@echo "###########################################"
	@echo "****** Deploying the bean"
	@echo "###########################################"
	asadmin deploy --createtables=true core/target/$(CORE_NAME).jar

undeploy:
	@echo "###########################################"
	@echo "****** Undeploying the bean"
	@echo "###########################################"
	asadmin undeploy --droptables=true $(CORE_NAME)

runExampleClient:
	@echo "###########################################"
	@echo "****** Running org.devilry.cli.RemoteClient"
	@echo "###########################################"
	cd cli/; mvn exec:java -Djava.mainclass="org.devilry.cli.ExampleClient" -Dexec.args="test"; cd ../
