package test.pcpnru.masterAction;

import org.junit.*;
import org.junit.Assert;

import pcpnru.utilities.DateUtil;

public class TestDateUtils {
	DateUtil dateutil = new DateUtil();

	@Test
	public void TestCurMonth() {
		Assert.assertEquals("04", dateutil.curMonth());
	}

	@Test
	public void TestCurYear() {
		Assert.assertEquals("2016", dateutil.curYear());
	}
}
