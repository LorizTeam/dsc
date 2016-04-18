package test.pcpnru.masterAction;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.pcpnru.inventoryAction.BrandAction;
import test.pcpnru.inventoryAction.ProductAction;
import test.pcpnru.inventoryAction.ProductGroupAction;
import test.pcpnru.inventoryAction.ProductTypeAction;

@RunWith(Suite.class)
@SuiteClasses({ TestRecordApproveAction.class, TestViewDetailPR.class, TestDateUtils.class, TestAddDataPR.class,
		TestVenderAction.class, BrandAction.class, ProductGroupAction.class, ProductTypeAction.class,
		ProductAction.class })
public class RunAllTest {

}
