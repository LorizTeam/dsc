package test.pcpnru.masterAction;

import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.*;

import pcpnru.utilities.DateUtil;
import test.pcpnru.masterData.TestPurchaseDB;
import test.pcpnru.masterData.TestRecordApproveDB;

public class TestPurchaseOrder {
	@Test
	public void Insert_po_hd() throws Exception {

		JSONObject jsonobj = new JSONObject();
		jsonobj = new TestAddDataPR().AddHD();
		String docno = (String) jsonobj.get("docno");
		String year = (String) jsonobj.get("year");
		String create_by = (String) jsonobj.get("create_by");
		String record_approve_date = (String) jsonobj.get("record_approve_date");
		int getbyjson = (Integer) jsonobj.get("rowsupdate");
		Assert.assertTrue(getbyjson > 0);
		String description = "ปลั๊กเพาเวอร์ 63A. 380V.", qty = "5", unit = "ชุด";
		JSONObject jsonobjdetail = new JSONObject();
		jsonobjdetail = new TestAddDataPR().AddDetail(docno, year, description, qty, unit, create_by);
		Assert.assertTrue((Integer) jsonobjdetail.get("rowsupdate") > 0);

		String po_docno = new TestRecordApproveDB().SelectUpdateDocNo("po");
		String ref_pr = docno;
		String ref_prdate = record_approve_date;
		DateUtil dateutil = new DateUtil();
		String po_year = dateutil.curYear();
		String docdate = dateutil.CnvToYYYYMMDDEngYear("04-03-2559", '-');
		String type = "1";
		String po_offer = "รองศาสตราจารย์บุญเกียรติ ไทรชมภู่";
		int creditday = 15;
		double penaltype_perday = 150.50;
		String ref_quotation = "1234";
		String ref_quotationdate = dateutil.CnvToYYYYMMDDEngYear("25-02-2559", '-');
		;
		String status = "1";
		String project_code = "PCC001";
		String approve_by = "";
		int rowsupdate = new TestPurchaseDB().InsertPO_HD(po_docno, po_year, docdate, ref_pr, ref_prdate, type,
				po_offer, creditday, penaltype_perday, ref_quotation, ref_quotationdate, status, create_by,
				project_code, approve_by);
		Assert.assertTrue(rowsupdate > 0);
	}

	@Test
	public void GetHD() throws Exception {
		String po_docno = Insert_po_hdnottest();

		Map<String, String> MapResultGetHD = new TestPurchaseDB().GetPO_HD(po_docno);

		Assert.assertTrue(!MapResultGetHD.get("po_docno").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("year").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("docdate").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("ref_pr").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("ref_prdate").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("type").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("po_offer").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("creditday").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("penaltype_perday").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("ref_quotation").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("ref_quotationdate").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("status").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("create_by").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("project_code").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("approve_by").equals(""));
		Assert.assertTrue(!MapResultGetHD.get("create_datetime").equals(""));
	}

	public String Insert_po_hdnottest() throws Exception {

		JSONObject jsonobj = new JSONObject();
		jsonobj = new TestAddDataPR().AddHD();
		String docno = (String) jsonobj.get("docno");
		String year = (String) jsonobj.get("year");
		String create_by = (String) jsonobj.get("create_by");
		String record_approve_date = (String) jsonobj.get("record_approve_date");
		int getbyjson = (Integer) jsonobj.get("rowsupdate");
		Assert.assertTrue(getbyjson > 0);
		String description = "ปลั๊กเพาเวอร์ 63A. 380V.", qty = "5", unit = "ชุด";
		JSONObject jsonobjdetail = new JSONObject();
		jsonobjdetail = new TestAddDataPR().AddDetail(docno, year, description, qty, unit, create_by);
		Assert.assertTrue((Integer) jsonobjdetail.get("rowsupdate") > 0);

		String po_docno = new TestRecordApproveDB().SelectUpdateDocNo("po");
		String ref_pr = docno;
		String ref_prdate = record_approve_date;
		DateUtil dateutil = new DateUtil();
		String po_year = dateutil.curYear();
		String docdate = dateutil.CnvToYYYYMMDDEngYear("04-03-2559", '-');
		String type = "1";
		String po_offer = "รองศาสตราจารย์บุญเกียรติ ไทรชมภู่";
		int creditday = 15;
		double penaltype_perday = 150.50;
		String ref_quotation = "1234";
		String ref_quotationdate = dateutil.CnvToYYYYMMDDEngYear("25-02-2559", '-');
		;
		String status = "1";
		String project_code = "PCC001";
		String approve_by = "";
		int rowsupdate = new TestPurchaseDB().InsertPO_HD(po_docno, po_year, docdate, ref_pr, ref_prdate, type,
				po_offer, creditday, penaltype_perday, ref_quotation, ref_quotationdate, status, create_by,
				project_code, approve_by);
		return po_docno;
	}

}
