<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>อนุมัติ ใบขออนุมัติเซื้อ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet"> 
		<link href="css/select2.css" rel="stylesheet">
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/select2.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
	</head>
	
	<body>
		<div><%@include file="topmenu.jsp" %></div>
		<h3 class="align-center">อนุมัติ ใบขออนุมัติเซื้อ</h3>
		<form action="prApprove" method="post">
			<div class="example" data-text="ค้นหาข้อมูล"> 
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
		         		 <div class="cell colspan4"></div>
						<div class="cell colspan2">
							<button class="button primary" name="search" type="submit"> <span class="mif-search"></span> ค้นหา</button>
						</div>
						<div class="cell colspan2">
							<button class="button success" name="save" type="submit"> <span class="mif-checkmark"></span> บันทึกการอนุมัติ</button>
						</div>
						<div class="cell colspan4"></div>		
				 	</div>
				</div>
				<s:hidden name="recordApproveModel.fromwindow" id="fromwindow" value="true"/>
			</div>
			<div class="example" data-text="รายการ PR">
				<div class="cell colspan12">
					<table id="pr_table" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th><label class="input-control small-check checkbox"> 
		                		<input type="checkbox" id="checkAll" data-show="indeterminate" />
		                		<span class="check"></span> 
		                        </label> เลือกทั้งหมด</th>
								<th>รหัส PR</th>
								<th>เรื่อง</th>
								<th>ผู้อนุมัติ</th>
								<th>ผู้ทำรายการ</th>
								<th>ปี</th>
								<th>วันที่ทำรายการ</th>
								<th>รายละเอียด</th>
							</tr>
						</thead>
						<tbody>
							<%
								List<RecordApproveModel> ListResultPRSearch = (List) request.getAttribute("ListResultPRSearch");
								if(new Validate().CheckListNotNull(ListResultPRSearch)){
									for(RecordApproveModel RAM:ListResultPRSearch){
							%>
										<tr>
											<td align="center">
											<%
												if(RAM.getApprove_status().equals("CC")){
											%>
												<label class="input-control small-check checkbox"> 
						                			<input type="checkbox" class="chkrow" name="chkrow" value="<%=RAM.getDocno() %>-<%=RAM.getYear() %>" data-show="indeterminate" disabled />
						                		<span class="check"></span> 
						                        </label>
						                        <input type="hidden" name="approveStatus" value="CC" /><input type="text" value=" ยกเลิกรายการ" size="8" readonly="readonly" />
											<%
												}else{
											%>
												<label class="input-control small-check checkbox"> 
						                			<input type="checkbox" class="chkrow" name="chkrow" value="<%=RAM.getDocno() %>-<%=RAM.getYear() %>" data-show="indeterminate" />
						                		<span class="check"></span> 
						                        </label>
						                        
						                        <select name="approveStatus" id="approveStatus" class="approveStatus">
							                        <option <%if(RAM.getApprove_status().equals("AP")){ %>selected <%} %> value="AP">อนุมัติ</option>
										        	<option <%if(RAM.getApprove_status().equals("WA")){ %>selected <%} %> value="WA">รอการอนุมัติ</option>
										        	<option value="CC">ยกเลิกรายการ</option>
									        	</select>
									        	
											<%	
												
												}
											%>
												
											</td>
											<td><%=RAM.getDocno() %>  </td>
											<td><%=RAM.getRecord_approve_title() %></td>
											<td><%=RAM.getRecord_approve_cen() %></td>
											<td><%=RAM.getCreate_name() %></td>
											<td><%=RAM.getYear() %></td>
											<td><%=RAM.getRecord_approve_date() %></td>
											<td><button class="button primary" type="button" onclick="getDetail('<%=RAM.getDocno() %>','<%=RAM.getYear() %>')" id="viewDetail"> <span class="mif-search"></span></button></td>
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
		</form>
		<script>
		function getDetail(docno,year) {
   			var load = window.open('/pcpnru/window_viewDetail?docno='+docno+'&year='+year,'pr',
   			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
		$(function(){
			var table = $("#pr_table").DataTable( { 
              	scrollY: '50vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[10, 25, 50, 100, -1],[10, 25, 50, 100, "All"] ] 
            } );
			
			
			$("#checkAll").change(function () {
       		    $("input:checkbox").prop('checked', $(this).prop("checked"));
       		});
			$("#record_approve_date").datepicker({
				format: "dd-mm-yyyy",todayBtn: "linked",todayHighlight: true,clearBtn: true
		    });
			$("#record_approve_month").datepicker({
			    format: "mm",
			    minViewMode: 1,
			    maxViewMode: 1,clearBtn: true
		    });
			$("#record_approve_year").datepicker({
			    format: "yyyy",
			    minViewMode: 2,
			    maxViewMode: 2,clearBtn: true
		    });
			$('#pr_table tbody').on( 'click', 'tr', function () { 
    	        //if ( $(this).hasClass('selected') ) {
    	        	var $index = $(this).index();
    	        	var data_chkrow = $(".chkrow").eq($index).val().substr(0,10);
    	        	$(".chkrow").eq($index).val(data_chkrow+"-"+$index);
    	        	//    $(this).removeClass('selected');
    	        //   var data_chkrow = $(".chkrow").eq($index).val();
    	        //   $(".chkrow").eq($index).val(data_chkrow+"-"+$(".approveStatus").eq($index).val());
    	        //   alert($(".chkrow").eq($index).val() );
    	           // alert($("input[type='checkbox']").eq($index).val());
    	            
    	        //}
    	       // else {
    	       // 	table.$('tr.selected').removeClass('selected');
    	       //     $(this).addClass('selected');
    	       //     var $index = $(this).index();
    	       // }
    	    });
		
		});
		
		</script>
	</body>
</html>
