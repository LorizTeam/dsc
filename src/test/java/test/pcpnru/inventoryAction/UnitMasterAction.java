package test.pcpnru.inventoryAction;

import test.pcpnru.inventoryModel.UnitMasterForm;
import test.pcpnru.inventoryData.UnitMasterDB;

import java.io.IOException;
import java.util.List;

import org.junit.*;

public class UnitMasterAction {

	static String unit_name = "Test UnitMaster";
	String update_unit_name = "Test UpdateUnitMaster";
	static String create_by = "00001";
	String update_by = "00002";

	@Test
	public void TestAddUnit() throws IOException, Exception {
		String unit_id = AddUnit();
		Assert.assertTrue(new UnitMasterDB().Get_UnitList(unit_id, unit_name).size() > 0);
	}

	@Test
	public void TestDeleteUnit() throws IOException, Exception {
		String Unit_id = AddUnit();
		UnitMasterDB umdb = new UnitMasterDB();
		umdb.DeleteUnitMaster(Unit_id);
		Assert.assertTrue(umdb.Get_UnitList(Unit_id, "").size() == 0);
	}

	@Test
	public void TestUpdateUnit() throws IOException, Exception {
		String Unit_id = AddUnit();
		UnitMasterDB umdb = new UnitMasterDB();
		umdb.UpdateUnitMaster(Unit_id, update_unit_name, update_by);
		List<UnitMasterForm> ResultList = umdb.Get_UnitList(Unit_id, "");
		for (UnitMasterForm ummodel : ResultList) {
			Assert.assertEquals(Unit_id, ummodel.getUnit_id());
			Assert.assertEquals(update_unit_name, ummodel.getUnit_name());
			Assert.assertEquals(update_by, ummodel.getUpdate_by());
		}
	}

	@Test
	public void GetUnitEmpty_protype_id() throws IOException, Exception {
		String Unit_id = "";
		UnitMasterDB umdb = new UnitMasterDB();
		List<UnitMasterForm> ResultList = umdb.Get_UnitList(Unit_id, "");
		Assert.assertTrue(ResultList.size() > 0);
	}

	@Test
	public void GetUnitNull_protype_id() throws IOException, Exception {
		String Unit_id = null;
		UnitMasterDB umdb = new UnitMasterDB();
		List<UnitMasterForm> ResultList = umdb.Get_UnitList(null, null);
		Assert.assertTrue(ResultList.size() > 0);
	}

	public static String AddUnit() throws IOException, Exception {
		UnitMasterDB umdb = new UnitMasterDB();
		String unit_id = umdb.GetHighest_AddUnitID();
		unit_id = umdb.PlusOneID_FormatID(unit_id);
		umdb.AddUnitMaster(unit_id, unit_name, create_by);
		return unit_id;
	}

}