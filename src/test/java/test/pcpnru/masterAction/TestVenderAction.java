package test.pcpnru.masterAction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.*;
import org.junit.Assert;

import pcpnru.utilities.DateUtil;
import pcpnru.utilities.Validate;
import test.pcpnru.masterData.TestRecordApproveDB;
import test.pcpnru.masterData.TestVenderDB;
import test.pcpnru.masterModel.TestRecordApproveModel;

public class TestVenderAction {

	String vender_name = "Test Vender", update_vender_name = "Test Vender 1", create_by = "00001", update_by = "00001",
			vendor_address = "4/13 หมู่ 5", vendor_road= "พหลโยธิน", vendor_subdistrict= "คลองหนึ่ง", vendor_district= "คลองหลวง",
			vendor_province= "ปทุมธานี", vendor_postcode= "12120", vendor_mobile= "0891744898", vendor_tel= "029022097",
			vendor_tel_ext= "12";
	

	@Test
	public void addVender_Hightestplusone() throws IOException, Exception {

		TestVenderDB tvd = new TestVenderDB();
		String vender_id = tvd.GetHighest_VenderID();
		vender_id = tvd.PlusOneID_FormatID(vender_id);
		boolean resultboolean = tvd.Add_Vender(vender_id, this.vender_name, this.create_by, this.vendor_address, this.vendor_road,
				this.vendor_subdistrict, this.vendor_district, this.vendor_province, this.vendor_postcode, 
				this.vendor_mobile, this.vendor_tel ,this.vendor_tel_ext);
		Assert.assertTrue(resultboolean);
	}

	@Test
	public void deleteHightestVenderID() throws IOException, Exception {
		TestVenderDB tvd = new TestVenderDB();
		String vender_id = tvd.GetHighest_VenderID();

		vender_id = tvd.PlusOneID_FormatID(vender_id);
		boolean resultboolean = tvd.Add_Vender(vender_id, this.vender_name, this.create_by, this.vendor_address, this.vendor_road,
				this.vendor_subdistrict, this.vendor_district, this.vendor_province, this.vendor_postcode, 
				this.vendor_mobile, this.vendor_tel ,this.vendor_tel_ext);
		Assert.assertTrue(resultboolean);

		resultboolean = tvd.Delete_vender(vender_id);
		Assert.assertTrue(resultboolean);
	}

	@Test
	public void UpdateVender() throws IOException, Exception {
		TestVenderDB tvd = new TestVenderDB();
		String vender_id = tvd.GetHighest_VenderID();
		tvd.Update_Vender(vender_id, "Update VendorName", this.update_by, this.vendor_address, 
				this.vendor_road, this.vendor_subdistrict, this.vendor_district, this.vendor_province, 
				"11233", "0891755482", "029999999" ,"999");

	}

	@Test
	public void GetHighest_VenderID() throws IOException, Exception {
		String highest_id = new TestVenderDB().GetHighest_VenderID();
		Assert.assertEquals(highest_id, new TestVenderDB().GetHighest_VenderID());
	}
}
