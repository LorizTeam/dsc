<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "019";
		
		chkAuthen = new CheckAuthenPageDB().getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
	}
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
		<link href="css/bootstrap-datepicker3.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
  		<script src="js/bootstrap-datepicker-th.js"></script>
	</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PR</title>
</head>
<body>
<s:set name="fromwindow" value="recordApproveModel.fromwindow"/>
<s:if test="%{#fromwindow == 'true'}">
	<div><%@include file="window-topmenu.jsp" %></div>
</s:if>
<s:else>
	<div><%@include file="topmenu.jsp" %></div>
</s:else>

<h3 class="align-center">รายการใบ PR</h3>
<form action="searchPR" method="post">
	<div class="example" data-text="กรองข้อมูลที่ต้องการ"> 
	
         <div class="grid">
         	<div class="row cells12">
	       		<div class="cell colspan3"></div>
				<div class="cell colspan3"> 
		        	รหัส PR
			        <div class="input-control text full-size"  data-role="input">
					    <s:textfield name="recordApproveModel.docno" id="docno" />
					</div>
				</div>
				<div class="cell colspan3"> 
		        	เรื่อง
			        <div class="input-control text full-size"  data-role="input">
					    <s:textfield name="recordApproveModel.record_approve_title" id="record_approve_title" />
					</div>
				</div>
				<div class="cell colspan3"></div>
		 	</div>
		 	<div class="row cells12">
         		 <div class="cell colspan4">
					วัน
					<div class="input-control text full-size"  data-role="input">
         				<s:textfield name="recordApproveModel.record_approve_date" id="record_approve_date" />
         			</div>
				</div>
				<div class="cell colspan4">
					เดือน
					<div class="input-control text full-size"  data-role="input">
         				<s:textfield name="recordApproveModel.record_approve_month" id="record_approve_month" />
         			</div>
         		</div>
				<div class="cell colspan4">
					ปี
					<div class="input-control text full-size"  data-role="input">
         				<s:textfield name="recordApproveModel.year" id="year" />
         			</div>
				</div>				
		 	</div>
		 	<div class="row cells12">
         		 <div class="cell colspan5"></div>
				<div class="cell colspan1">
					<button class="button primary" name="search" type="submit"> <span class="mif-search"></span></button>
				</div>
				<div class="cell colspan1">
					<button class="button success" name="create" type="submit"> <span class="mif-plus"></span></button>
				</div>
				<div class="cell colspan5"></div>		
		 	</div>
		</div>
		<s:hidden name="recordApproveModel.fromwindow" id="fromwindow"/>
	</div>
	<div class="example" data-text="รายการ PR">
		<div class="grid">
			<div class="row cells12">
				<div class="cell colspan12">
					<table id="dataTable" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>เลือกรายการ</th>
								<th>สถานะ</th>
								<th>รหัส PR</th>
								<th>ผู้ทำรายการ</th>
								<th>ปี</th>
								<th>วันที่ทำรายการ</th>
								<th>รายละเอียด</th>
							</tr>
						</thead>
						<tbody>
							<%
								List ListResultPRSearch = (List) request.getAttribute("ListResultPRSearch");
								if(new Validate().CheckListNotNull(ListResultPRSearch)){
									Iterator ListIter = ListResultPRSearch.iterator();
									while(ListIter.hasNext()){
										RecordApproveModel RAM = (RecordApproveModel) ListIter.next();
							%>
										<tr>
											<td align="center"><a onclick="Opener('<%=RAM.getDocno() %>','<%=RAM.getRecord_approve_date() %>')"><span class="mif-checkmark" ></span></a></td>
											<td align="center">
											<%
												if(RAM.getApprove_status().equals("CC")){
											%>
												ยกเลิก
											<%
												}else if(RAM.getApprove_status().equals("WA")){
											%>
												รอการอนุมัติ
											<%		
												}else if(RAM.getApprove_status().equals("AP")){
											%>
												อนุมัติแล้ว
											<%		
												}
											%>
											</td>
											<td><%=RAM.getDocno() %></td>
											<td><%=RAM.getCreate_by() %></td>
											<td><%=RAM.getYear() %></td>
											<td><%=RAM.getRecord_approve_date() %></td>
											<td><button class="button primary" type="submit" name="viewdetail" onclick="viewDetail('<%=RAM.getDocno() %>','<%=RAM.getYear() %>')"> <span class="mif-search"></span></button></td>
										</tr>
							<%
									}
								}else{
							%>
									<tr>
										<td colspan="7" align="center">Data not found</td>
									</tr>
							<%
								}
							%>
						</tbody>
					</table>
				</div>
			</div>		
		</div>
	</div>
</form>
<script>
$(function(){
	$("#dataTable").DataTable( { 
      	scrollY: '50vh', 
      	scrollX: true,
      	scrollCollapse: true, 
        ordering: false,
        "lengthMenu": [[10, 25, 50, 100, -1],[10, 25, 50, 100, "All"] ] 
    } );
	$("#record_approve_date").datepicker({
		format: "dd-mm-yyyy",todayBtn: "linked",todayHighlight: true,clearBtn: true
    });
	$("#record_approve_month").datepicker({
	    format: "mm",
	    minViewMode: 1,
	    maxViewMode: 1,clearBtn: true
    });
	$("#year").datepicker({
	    format: "yyyy",
	    minViewMode: 2,
	    maxViewMode: 2,clearBtn: true
    });

});
function Opener(pre_loadpr,date_loadpr) {
    
    window.opener.document.getElementById ("pre_loadpr").value = pre_loadpr;
    window.opener.document.getElementById ("pre_loadpr1").value = pre_loadpr;
    window.opener.document.getElementById ("date_loadpr").value = date_loadpr;
    window.close();
}
function viewDetail(docno,year){
	document.getElementById("docno").value=docno;
	document.getElementById("year").value=year;
}
</script>
</body>
</html>