<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectModel.*" %>
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
		<link href="css/bootstrap-datepicker3.css" rel="stylesheet">
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script> 
	    <script src="js/metro.js"></script>
	    <script src="js/select2.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
        <script src="js/bootstrap-datepicker-th.js"></script>
	</head>
	
	<body>
		<div><%@include file="topmenu.jsp" %></div>
		<h3 class="align-center">อนุมัติ ใบสั่งซื้อ</h3>
		<form action="poHistory" method="post">
			<div class="example" data-text="ค้นหาข้อมูล"> 
		         <div class="grid">
		         	<div class="row cells12">
			       		<div class="cell colspan3"></div>
						<div class="cell colspan3"> 
				        	รหัส PO
					        <div class="input-control text full-size"  data-role="input">
							    <s:textfield name="pomodel.po_docno" id="po_docno" />
							    <s:hidden name="pomodel.year" id="year" />
							</div>
						</div>
						<div class="cell colspan3"> 
				        	ผู้ขาย
					        <div class="input-control text full-size"  data-role="input">
							    <s:hidden name="pomodel.vender" id="vendor_id"  />
							    <s:textfield name="pomodel.vender_name" id="vendor_name" readonly="true" />
							    <div class="button-group">
							 	<button class="button primary" type="button" onclick="getvendor()"> <span class="mif-search"></span></button>
								<button class="button danger" type="button" id="delete_vendor"><span class="mif-bin"></span></button>
								</div>
							</div>
						</div>
						<div class="cell colspan3"></div>
				 	</div>
				 	<div class="row cells12">
				 		 <div class="cell colspan3"></div>
		         		 <div class="cell colspan1">
							วัน
							<div class="input-control text full-size"  data-role="input">
		         				<s:textfield name="pomodel.po_day" id="po_day" />
		         			</div>
						</div>
						<div class="cell colspan1">
							เดือน
							<div class="input-control text full-size"  data-role="input">
		         				<s:textfield name="pomodel.po_month" id="po_month" />
		         			</div>
		         		</div>
						<div class="cell colspan1">
							ปี
							<div class="input-control text full-size"  data-role="input">
		         				<s:textfield name="pomodel.po_year" id="po_year" />
		         			</div>
						</div>	
						<div class="cell colspan1">
							ประเภท
							<div class="input-control text full-size"  data-role="input">
								<select name="pomodel.type" id="pomodel" >
								    <option value="">ทั้งหมด</option>
									<option value="P">จัดซื้อ</option>
									<option value="H">จัดจ้าง</option>
								</select>
							</div>
						</div>		
						<div class="cell colspan2">
							สถานะ
							<div class="input-control text full-size"  data-role="input">
								<select name="pomodel.approve_status" id="approve_status" >
								    <option value="">ทั้งหมด</option>
									<option value="WA">รออนุมัติ</option>
									<option value="AP">อนุมัติแล้ว</option>
									<option value="CC">ยกเลิก</option>
								</select>
							</div>
						</div>		
				 	</div>
				 	<div class="row cells12"> 
						<div class="cell colspan12" align="center">
							<button class="button primary" name="search" type="submit"> <span class="mif-search"></span> ค้นหา</button>
						</div>	
				 	</div>
				</div>
				<s:hidden name="recordApproveModel.fromwindow" id="fromwindow" value="true"/>
			</div>
			<div class="example" data-text="รายการ PR">
				<div class="cell colspan12">
					<table id="po_table" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
						<thead>
							<tr> 
								<th>รหัส PO</th>
								<th>ผู้ขาย</th> 
								<th>ประเภท</th>
								<th>สถานะ</th>
								<th>ผู้ทำรายการ</th>
								<th>ปี</th>
								<th>วันที่ทำรายการ</th>
								<th>รายละเอียด</th>
							</tr>
						</thead>
						<tbody>
							<%
								List<PurchaseOrderModel> ListResultPOSearch = (List) request.getAttribute("ListResultPOSearch");
								if(new Validate().CheckListNotNull(ListResultPOSearch)){
									for(PurchaseOrderModel RAM:ListResultPOSearch){
							%>
										<tr> 
											<td class="tddocno"><%=RAM.getPo_docno()%></td>
											<td><%=RAM.getVender()%></td>
											<%if(RAM.getType().equals("P")){%>
												<td>จัดซื้อ</td>
											<%}else{ %>
												<td>จัดจ้าง</td>
											<%} %>
											<%if(RAM.getApprove_status().equals("AP")){%>
												<td>อนุมัติแล้ว</td>
											<%}else if(RAM.getApprove_status().equals("WA")){ %>
												<td>รออนุมัติ</td>
											<%}else{ %>
												<td>ยกเลิก</td>
											<%} %>
											
											<td><%=RAM.getCreate_by()%></td>
											<td class="tdyear"><%=RAM.getYear() %></td>
											<td><%=RAM.getPo_docdate()%></td>
											<td align="center"><button class="button primary" type="submit" name="next" id="next"> <span class="mif-search"></span></button></td>
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
		 
		function getvendor() {
			var load = window.open('/dsc/windows_entrancvendor','ap_po',
			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
		}
	$(function(){
		
		$("#next").click(function(){
			$("#po_docno").val($(".tddocno").text()); 
			$("#year").val($(".tdyear").text());
		});
		
			$("#delete_vendor").click(function(){
				$("#vendor_id").val("");
				$("#vendor_name").val("");
			});
			
			var table = $("#po_table").DataTable( { 
              	scrollY: '50vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[10, 25, 50, 100, -1],[10, 25, 50, 100, "All"] ] 
            } );
			
			$("#po_day").datepicker({
				format: "dd-mm-yyyy",todayBtn: "linked",todayHighlight: true,clearBtn: true,autoclose: true
		    });
			$("#po_month").datepicker({
			    format: "mm",
			    minViewMode: 1,
			    maxViewMode: 1,clearBtn: true,autoclose: true
		    });
			$("#po_year").datepicker({
			    format: "yyyy",
			    minViewMode: 2,
			    maxViewMode: 2,clearBtn: true,autoclose: true
		    });
			$('#po_table tbody').on( 'click', 'tr', function () { 
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
