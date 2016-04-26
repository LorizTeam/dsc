<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.utilities.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%
String project_code = "";
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		project_code = session.getAttribute("project_code").toString();

		boolean chkAuthen = false;
		String page_code = "017";
		
		chkAuthen = new CheckAuthenPageDB().getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
	}
%>
<% 	String pr_docno = "";
	if(request.getAttribute("pr_docno")!=null) pr_docno = (String) request.getAttribute("pr_docno"); 
	String pr_docdate = "";
	if(request.getAttribute("pr_docdate")!=null) pr_docdate = (String) request.getAttribute("pr_docdate");
	String po_docno = "";
	if(request.getAttribute("po_docno")!=null) po_docno = (String) request.getAttribute("po_docno");
	String docdate = "";
	if(request.getAttribute("docdate")!=null) docdate = (String) request.getAttribute("docdate");
	String vender = "";
	if(request.getAttribute("vender")!=null) vender = (String) request.getAttribute("vender");
	String credit_day = "";
	if(request.getAttribute("credit_day")!=null) credit_day = (String) request.getAttribute("credit_day");
	String mulct_day = "";
	if(request.getAttribute("mulct_day")!=null) mulct_day = (String) request.getAttribute("mulct_day");
	String quotation_number = "";
	if(request.getAttribute("quotation_number")!=null) quotation_number = (String) request.getAttribute("quotation_number");
	String quotation_date = "";
	if(request.getAttribute("quotation_date")!=null) quotation_date = (String) request.getAttribute("quotation_date");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>บันทึกข้อความขออนุมัติดำเนินการ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet"> 
		<link href="css/sweetalert.css" rel="stylesheet" />
		<link href="css/lightgallery.css" rel="stylesheet" />
		<link href="css/bootstrap-datepicker3.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
  		<script src="js/bootstrap-datepicker-th.js"></script>
  		<script src="js/sweetalert.min.js"></script>
		<script src="js/lightgallery.js"></script>
		<script src="js/lg-fullscreen.js"></script>
		<script src="js/lg-thumbnail.js"></script>
		<script src="js/lg-zoom.js"></script>
		<script src="js/lg-hash.js"></script>
		<script src="js/lg-pager.js"></script>
	</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>สร้างใบ PO</title>
</head>
<body>
	<div><%@include file="window-topmenu.jsp" %></div>
	<h3 class="align-center">ทำรายการสั่งซื้อ/สั่งจ้าง</h3>
	<form >
		  
		<div class="example" data-text="ข้อมูลรายละเอียด PO"> 
	         <div class="grid">
	         	<div class="row cells12">
	         		<div class="cell colspan3"> </div> 
					<div class="cell colspan3 "> 
			        	รหัส PR
				        <div class="input-control text full-size"  data-role="input">
						    <input type="text" value="<%=pr_docno%>" readonly="readonly" /> 
						</div>
					</div>
					<div class="cell colspan3"> 
						วันที่สร้าง PR
				        <div class="input-control text full-size"  data-role="input">
						    <input type="text" value="<%=pr_docdate%>" readonly="readonly" /> 
						</div>
					</div>
					<div class="cell colspan3"> </div>
			 	</div>
			 	<div class="row cells12">
					<div class="cell colspan12"> 
			 		
			 			<table id="table_po" class="cell-border hover display compact nowrap" cellspacing="0" width="100%" >
			 				<thead>
			 					<tr>
			 						<th>ลำดับ</th>
			 						<th>รายการ</th>
			 						<th>จำนวน</th>
			 						<th>ราคาต่อหน่วย</th>
			 						<th>จำนวนเงิน (บาท)</th>
			 						<th>หมายเหตุ</th>
			 					</tr>
			 				</thead>
			 				<tbody>
			 					 <%	 List PRList = null;
			 						if(request.getAttribute("PRList")!=null) PRList = (List) request.getAttribute("PRList"); 
			 					  
			 					 	if (PRList != null) { 
										int x = 0;
										for (Iterator iter = PRList.iterator(); iter.hasNext();) {
										x++; 
										PurchaseOrderModel poMaster = (PurchaseOrderModel) iter.next(); 
								%>
			 					<tr>
			 						<td><div class="input-control text full-size"  data-role="input"><input type="text" name="itemno" id="itemno" value="<%=poMaster.getItemno()%>"  size="2" dir="rtl" readonly="readonly"  /></div></td>
			 						<td><div class="input-control text full-size"  data-role="input"><input type="text" name="description" id="description" value="<%=poMaster.getDescription()%>" size="25"  readonly="readonly" /></div></td>
			 						<td><div class="input-control text full-size"  data-role="input"><input type="text" name="qty" id="qty" value="<%=poMaster.getQty()%>" size="8" dir="rtl" /></div></td>
			 						<td><div class="input-control text full-size"  data-role="input"><input type="text" name="amount" id="amount" value="<%=poMaster.getAmount()%>" size="8" dir="rtl" required="required" /></div></td>
			 						<td><div class="input-control text full-size"  data-role="input"><input type="text" name="amounttotal" id="amounttotal" value="<%=poMaster.getAmounttotal()%>" size="10" dir="rtl" readonly="readonly" /></div></td>
			 						<td><div class="input-control text full-size"  data-role="input"><input type="text" name="remark" id="remark" value="<%=poMaster.getRemark()%>" size="10" /></div></td> 
			 					</tr>
			 					<% 	} %>
                
                				<%}  %> 
			 				</tbody>
			 			</table>
			 		</div>
			 	</div> 
			 	 
			 	<div class="row cells12">
	         		<div class="cell colspan3"> </div> 
					<div class="cell colspan3 "> 
			        	รหัส PO
				        <div class="input-control text full-size"  data-role="input">
				        	<input type="text" value="<%=po_docno%>" readonly="readonly" /> 
						</div>
					</div>
					<div class="cell colspan3"> 
						วันที่ทำรายการ PO
						<div class="input-control text full-size"> 
						    <input type="text" value="<%=docdate%>" readonly="readonly">
						</div>
					</div>
					<div class="cell colspan3"> </div>
			 	</div>
			 	<div class="row cells12">
	         		<div class="cell colspan3"> </div> 
	         		<div class="cell colspan3"> 
						ประเภท
						<%  String type = "";
							if(request.getAttribute("type")!=null){
							type = (String) request.getAttribute("type"); 
						}
						%>
						<label class="input-control radio">
						    <input type="radio" name="pomodel.type" <%if(type.equals("P")){%>checked="checked"<%}%> value="P">
						    <span class="check"></span>
						    <span class="caption">จัดซื้อ</span>
						</label>
						<label class="input-control radio">
						    <input type="radio" name="pomodel.type" <%if(type.equals("H")){%>checked="checked"<%}%> value="H">
						    <span class="check"></span>
						    <span class="caption">จัดจ้าง</span>
						</label>
					</div>
					<div class="cell colspan3 "> 
			        	เรียนผู้ขาย
				        <div class="input-control text full-size"  data-role="input">
						    <input type="text" value="<%=vender%>" readonly="readonly" /> 
						</div>
					</div>
					<div class="cell colspan3"> </div>
			 	</div>
			 	<div class="row cells12">
	         		<div class="cell colspan3"> </div> 
	         		<div class="cell colspan3"> 
						จะส่งมอบงานจ้างหรือสิ่งของภายใน
						<div class="input-control text full-size"  data-role="input"> 
						    <input type="text" value="<%=credit_day%>" readonly="readonly" /> 
						</div>
						วัน
					</div>
					<div class="cell colspan3 "> 
			        	ค่าปรับวันละ
				        <div class="input-control text full-size"  data-role="input"> 
						    <input type="text" value="<%=mulct_day%>" readonly="readonly" /> 
						</div>
						หากส่งของช้า
					</div>
					<div class="cell colspan3"> </div>
			 	</div>
			</div>
		</div>
		<div class="example" data-text="ใบเสนอราคา"> 
	         <div class="grid">
	         	<div class="row cells12">
	         		<div class="cell colspan3"> </div> 
					<div class="cell colspan3 "> 
			        	อ้างอิงหมายเลขใบเสนอราคา
				        <div class="input-control text full-size"  data-role="input"> 
						    <input type="text" value="<%=quotation_number%>" readonly="readonly" /> 
						</div>
					</div>
					<div class="cell colspan3"> 
						ลงวันที่ของใบเสนอราคา
						<div class="input-control text full-size"> 
						    <input type="text" value="<%=quotation_date%>" readonly="readonly" /> 
						</div>
					</div>
					<div class="cell colspan3"> </div>
			 	</div>
			</div>
		</div> 
		<div class="example" data-text="ใบเสนอราคา"> 
	         <div class="grid">
		<div class="row cells12"> 
				<div class="cell colspan3"> </div>
	         		<div class="cell colspan4"> 
			        	แนบไฟล์รูปภาพ
				        <div class="input-control text full-size">
						    <s:file name="toBeUploaded" id="toBeUploaded"/> 
						    <s:hidden name="pomodel.alertmsg" id="alertmsg"/>
						</div>
					</div>
	         	</div>
	         	<div class="accordion" data-role="accordion">
			    	<div class="frame">
			            <div class="heading">รูปภาพไฟล์แนบ</div>
			            <div class="content">
			            	<div id="lightgallery">
			         	<%
			         		if(request.getAttribute("ResultImageList") != null){
			         			List ResultImageList = (List) request.getAttribute("ResultImageList");
			         			for(Iterator imageIter = ResultImageList.iterator();imageIter.hasNext();){
			         				PurchaseOrderModel poModel = (PurchaseOrderModel) imageIter.next();
			         	%>		
			         			
									<a href="<%=poModel.getImg_path()%>">
								      <img src="<%=poModel.getImg_path()%>" style="width: 10%; height: 10%" />
								  	</a>
								
			         	<%
			         			}
			         		}
			         	%>
			         		</div>
			            </div>
			        </div>
         		</div>
		</div>
		</div> 
	</form>
<script type="text/javascript">
function getpr() {
	var load = window.open('/dsc/po_openwindowsPR','pr',
	             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
}
function getvendor() {
		var load = window.open('/dsc/windows_entrancvendor','pr',
		             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
	}
$(function(){
	$("#delete_vendor").click(function(){
		$("#vendor_id").val("");
		$("#vendor_name").val("");
	});
	
	if($("#alertmsg").val() != ""){
		swal("Error",$("#alertmsg").val() , "error");
	}
	
	$('#lightgallery').lightGallery();
	
	var table = $('#table_po').DataTable( {
    	scrollY:        '47.5vh', 
    	scrollX: true,
    	scrollCollapse: true,
      	ordering: false,
      	"lengthMenu": [[-1], [ "All" ]] 
  	});
	
	$("#savehd").click(function(){
		 
		$("#pre_loadpr").val("-");
	});
	
	$("#pull_detailpr").click(function(){
		var po_docdate 			= $("#po_docdate").val();
		var vender 				= $("#vender").val();
		var quotation_number	= $("#quotation_number").val();
		var quotation_date 		= $("#quotation_date").val(); 
		var mulct_day			= $("#mulct_day").val(); 
		var credit_day			= $("#credit_day").val(); 
		
		if(po_docdate == '') 		$("#po_docdate").val("-");  
		if(vender == '') 			$("#vender").val("-");
		if(quotation_number == '') 	$("#quotation_number").val("-");
		if(quotation_date == '') 	$("#quotation_date").val("-");
		if(mulct_day == '') 		$("#mulct_day").val("0");
		if(credit_day == '') 		$("#credit_day").val("0");
	});
	$("#delete_detailpr").click(function(){
		var po_docdate 			= $("#po_docdate").val();
		var vender 				= $("#vender").val();
		var quotation_number	= $("#quotation_number").val();
		var quotation_date 		= $("#quotation_date").val(); 
		var mulct_day			= $("#mulct_day").val(); 
		var credit_day			= $("#credit_day").val(); 
		
		if(po_docdate == '') 		$("#po_docdate").val("-");  
		if(vender == '') 			$("#vender").val("-");
		if(quotation_number == '') 	$("#quotation_number").val("-");
		if(quotation_date == '') 	$("#quotation_date").val("-");
		if(mulct_day == '') 		$("#mulct_day").val("0");
		if(credit_day == '') 		$("#credit_day").val("0");
	});
	$("#pr_docdate").datepicker({
    	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
    });
	$("#po_docdate").datepicker({
    	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
    });
	$("#quotation_date").datepicker({
    	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
    });
	$("#delete").click(function(){
    	$("#pre_loadpr").val("");
    });
	
});
</script>
</body>
</html>