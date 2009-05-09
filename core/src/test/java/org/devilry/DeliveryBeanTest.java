package org.devilry;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.devilry.core.DeliveryCandidateNode;
import org.devilry.core.DeliveryRemote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeliveryBeanTest {
	Context ctx;
	DeliveryRemote bean;

    public DeliveryBeanTest() {
    }

    @Before
    public void setUp() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");
		ctx = new InitialContext(p);
		Object ref = ctx.lookup("DeliveryBeanRemote");
		bean = (DeliveryRemote) ref;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void hello() {
		DeliveryCandidateNode n = new DeliveryCandidateNode();
		bean.add(n);
	}
}