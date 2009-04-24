Setup and install glassfish
===========================

Download glassfish V2.1 and install it to a location of your choosing. Configure 
the Maven build files for Devilry to use your newly installed glassfish by 
adding the following to your settings.xml::

	<activeProfiles>
		<activeProfile>Devilry default</activeProfile>
	</activeProfiles>

	<profiles>
		<profile>
			<id>Devilry default</id>
            <properties>
				<glassfish.home>${env.HOME}/prog/glassfish</glassfish.home>
			</properties>
		</profile>
	</profiles>

Or just copy ``doc/settings.xml``.

On Linux and OSX, settings.xml is located in ``$HOME/.m2/settings.xml``.
In the config above we have installed glassfish in ``$HOME/prog/glassfish/``, so 
you will have to adjust the path to reflect where you installed glassfish.
You can read more about maven settings files here:

    http://maven.apache.org/settings.html



Compile and run with glassfish
==============================

1. Start glassfish::

    ~$ ant start-server

2. Build all modules::

    ~$ ant install

3. Deploy the bean to glassfish::

    ~$ ant deploy

    you will probably get some SQL exceptions. As long as you get
    "Command deploy executed successfully ...." within these warnings, 
    everything is OK.

4. Run the example client (noninterractive client)::

   ~$ ant ExampleClient 

5. Run the remote client::

    ~$ ant RemoteClient

Run "ant" without any arguments for more commands.
Hei
