package test.pcpnru.masterAction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.*;
import org.junit.Assert;

import pcpnru.utilities.DateUtil;
import pcpnru.utilities.Validate;
import test.pcpnru.masterData.TestRecordApproveDB;
import test.pcpnru.masterModel.TestRecordApproveModel;

public class TestViewDetailPR {

	String docno = "00003";
	String year = "2016";

	@Test
	public void GetValueByDocno() throws IOException, Exception {
		Map MapResult = new TestRecordApproveDB().GetAllValueHeader_byDocno(this.docno, this.year);
		Assert.assertTrue(!MapResult.isEmpty());
	}

	@Test
	public void GetValueByEmptyDocno() throws IOException, Exception {
		Map MapResult = new TestRecordApproveDB().GetAllValueHeader_byDocno("", this.year);
		Assert.assertTrue(MapResult.isEmpty());
	}

	@Test
	public void GetValueByEmptySpaceDocno() throws IOException, Exception {
		Map MapResult = new TestRecordApproveDB().GetAllValueHeader_byDocno(" ", this.year);
		Assert.assertTrue(MapResult.isEmpty());
	}

	@Test
	public void CheckResultAfterGet() throws IOException, Exception {
		Map MapResult = new TestRecordApproveDB().GetAllValueHeader_byDocno(this.docno, this.year);
		Assert.assertEquals(true, !MapResult.get("docno").equals(""));
		Assert.assertEquals(true, !MapResult.get("year").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_hd").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_t").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_date").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_title").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_rian").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_des1").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_des2").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_des3").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_cen").equals(""));
		Assert.assertEquals(true, !MapResult.get("record_approve_dep").equals(""));
		Assert.assertEquals(true, !MapResult.get("thaidate_report").equals(""));
		Assert.assertEquals(true, !MapResult.get("create_by").toString().equals(""));
	}

	@Test
	public void CheckResultNullValueAfterGet_ByNulldocno() throws IOException, Exception {
		Map MapResult = new TestRecordApproveDB().GetAllValueHeader_byDocno(null, this.year);
		Assert.assertEquals(null, MapResult.get("docno"));
		Assert.assertEquals(null, MapResult.get("year"));
		Assert.assertEquals(null, MapResult.get("record_approve_hd"));
		Assert.assertEquals(null, MapResult.get("record_approve_t"));
		Assert.assertEquals(null, MapResult.get("record_approve_date"));
		Assert.assertEquals(null, MapResult.get("record_approve_title"));
		Assert.assertEquals(null, MapResult.get("record_approve_rian"));
		Assert.assertEquals(null, MapResult.get("record_approve_des1"));
		Assert.assertEquals(null, MapResult.get("record_approve_des2"));
		Assert.assertEquals(null, MapResult.get("record_approve_des3"));
		Assert.assertEquals(null, MapResult.get("record_approve_cen"));
		Assert.assertEquals(null, MapResult.get("record_approve_dep"));
		Assert.assertEquals(null, MapResult.get("thaidate_report"));
		Assert.assertEquals(null, MapResult.get("create_by"));
	}

	@Test
	public void PutValueToModel_andCheck() throws IOException, Exception {
		TestRecordApproveModel TRAM = new TestRecordApproveModel();
		Map MapResult = new TestRecordApproveDB().GetAllValueHeader_byDocno(this.docno, this.year);
		TRAM.setDocno(MapResult.get("docno").toString());
		Assert.assertEquals("00003", TRAM.getDocno());
	}
}
