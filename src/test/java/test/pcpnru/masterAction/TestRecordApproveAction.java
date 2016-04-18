package test.pcpnru.masterAction;

import java.io.IOException;
import java.util.List;

import org.junit.*;

import pcpnru.utilities.DateUtil;
import pcpnru.utilities.Validate;
import test.pcpnru.masterData.TestRecordApproveDB;;

public class TestRecordApproveAction {

	String docno = "00003", title = "ขออนุมัติเบิกจ่าย ", date = "2016-04-01", month = "03", year = "2016",
			thaidate = "2559-04-01", thaidateAndformat = "01-04-2559";

	@Test
	public void Select_List_PR_CurrentMonth() throws IOException, Exception {

		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", "", this.month, "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void GetResultPR_By_CurmonthAndCurYear() throws IOException, Exception {
		DateUtil dateutil = new DateUtil();
		List PR_DT = new TestRecordApproveDB().ListRecordApproveDT("", dateutil.curMonth(), dateutil.curYear());
		Assert.assertTrue(!PR_DT.isEmpty());
	}

	@Test
	public void Select_List_PR_Docnumber() throws IOException, Exception {

		List ListPr = new TestRecordApproveDB().GetListPR_Header("-", "", "", "", "");
		Assert.assertTrue(ListPr.isEmpty());

		ListPr = new TestRecordApproveDB().GetListPR_Header(this.docno, "", "", "", "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void Select_List_PR_Title() throws IOException, Exception {

		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "***", "", "", "");
		Assert.assertTrue(ListPr.isEmpty());

		ListPr = new TestRecordApproveDB().GetListPR_Header("", this.title, "", "", "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void Select_List_PR_Date() throws IOException, Exception {

		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", "2016", "", "");
		Assert.assertTrue(ListPr.isEmpty());

		ListPr = new TestRecordApproveDB().GetListPR_Header("", "", this.thaidate, "", "");
		Assert.assertTrue(ListPr.isEmpty());

		ListPr = new TestRecordApproveDB().GetListPR_Header("", "", this.date, "", "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void Select_List_PR_Year() throws IOException, Exception {

		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", "", "", "02");
		Assert.assertTrue(ListPr.isEmpty());

		ListPr = new TestRecordApproveDB().GetListPR_Header("", "", "", "", this.year);
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void Select_List_EmptyDataInput() throws IOException, Exception {

		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", "", "", "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void Trim_title() throws IOException, Exception {
		Assert.assertEquals("ขออนุมัติเบิกจ่าย", this.title.trim());
	}

	@Test
	public void Select_List_PR_Format_EngDate_TO_THAIDate() throws IOException, Exception {

		String FormatThaidate = this.thaidate;
		String FormatEngDate = new DateUtil().CnvFROMYYYYMMDDTHyear_ToYYYYMMDDEngYear(FormatThaidate, '-');
		Assert.assertEquals("2016-04-01", FormatEngDate);
		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", FormatEngDate, "", "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void Select_List_PR_Format_Thaidate_TO_Engdate() throws IOException, Exception {

		String FormatThaidate = this.thaidateAndformat;
		String FormatEngDate = new DateUtil().CnvToYYYYMMDDEngYear(FormatThaidate, '-');
		Assert.assertEquals("2016-04-01", FormatEngDate);
		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", FormatEngDate, "", "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void Select_List_PR_Format_Thaidate_TO_Engdate_Empty() throws IOException, Exception {

		String FormatThaidate = "";
		String FormatEngDate = new DateUtil().CnvToYYYYMMDDEngYear(FormatThaidate, '-');
		Assert.assertEquals("", FormatEngDate);
		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", FormatEngDate, "", "");
		Assert.assertTrue(!ListPr.isEmpty());
	}

	@Test
	public void ValidateListNotEmpty() throws IOException, Exception {

		String FormatThaidate = "";
		String FormatEngDate = new DateUtil().CnvToYYYYMMDDEngYear(FormatThaidate, '-');
		Assert.assertEquals("", FormatEngDate);
		List ListPr = new TestRecordApproveDB().GetListPR_Header("", "", FormatEngDate, "", "");
		Assert.assertTrue(!ListPr.isEmpty());
		Assert.assertTrue(new Validate().CheckListNotNull(ListPr));
	}

	@Test
	public void CheckPattern_DocnoNumberOnly() throws IOException, Exception {

		String Docno = "00123", docnofalse = "awdwa848", docnofalsespacewhite = "988 954", docnoempty = " ";
		Assert.assertTrue(Docno.matches("^\\d+$"));
		Assert.assertTrue(Docno.matches("^\\d+$"));
		Assert.assertFalse(docnofalse.matches("^\\d+$"));
		Assert.assertFalse(docnofalsespacewhite.matches("^\\d+$"));
		Assert.assertFalse(docnoempty.matches("^\\d+$"));
	}
}
