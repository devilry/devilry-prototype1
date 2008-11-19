package org.devilry.sandbox.ejb;
import javax.ejb.Stateless;


@Stateless
public class ConfigImpl implements ConfigLocal, ConfigRemote {

	public int getMaxFileSize() {
		return 5;
	}

	public String getSiteName() {
		return "Demo site";
	}
}
