<%@page import="com.mysql.jdbc.IterateBlock"%>
<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.masterData.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "015";
		
		chkAuthen = new CheckAuthenPageDB().getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
	}

	RecordApproveDB ra = new RecordApproveDB();
	DateUtil dateUtil = new DateUtil();
   

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>ใบขออนุมัติซื้อ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet"> 
		<link href="css/bootstrap-datepicker3.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		<link href="css/sweetalert.css" rel="stylesheet" />
		<link href="css/lightgallery.css" rel="stylesheet" />
		<link href="css/select2.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
  		<script src="js/bootstrap-datepicker-th.js"></script>
  		<script src="js/sweetalert.min.js"></script>
  		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.13/jquery.mousewheel.min.js"></script>
		<script src="js/lightgallery.js"></script>
		<script src="js/lg-fullscreen.js"></script>
		<script src="js/lg-thumbnail.js"></script>
		<script src="js/lg-zoom.js"></script>
		<script src="js/lg-hash.js"></script>
		<script src="js/lg-pager.js"></script>
		<script src="js/select2.js"></script>
	</head>
	
	<body>
		<s:set name="fromwindow" value="recordApproveModel.fromwindow"/>
		<s:set name="approve_status" value="recordApproveModel.approve_status"/>
		<s:if test="%{#fromwindow=='true' || #fromwindow=='view'}">
			<div><%@include file="window-topmenu.jsp" %></div>
		</s:if>
		<s:else>
			<div><%@include file="topmenu.jsp" %></div>
		</s:else>
		 
		 <form action="recordApprove" id="recordApprove" method="post" enctype="multipart/form-data">
		 <s:hidden name="recordApproveModel.fromwindow"/>
		 <div class="grid" >
		 <div class="row cells12 " >
		 			<div class="cell align-center colspan2">

						<s:if test="%{#fromwindow=='true'}">
						<a class="button success" id="back" href="windowsPR"><span class="mif-lg fg-white">ย้อนกลับ</span></a>
						</s:if>
						<s:elseif test="%{#fromwindow=='po'}">
						<a class="button success" id="back" href="po_openwindowsPR"><span class="mif-lg fg-white">ย้อนกลับ</span></a>
						</s:elseif>
	         	 	</div>
	         	 	<div class="cell align-center colspan3">
	         	 	</div>
		 			<div class="cell align-left colspan5"><h3>ใบขออนุมัติซื้อ </h3></div>
		 			<div class="cell align-left colspan2"><br>

						<s:if test="%{#fromwindow!='true' && #fromwindow!='view'  && #fromwindow!='po'}">
						<a class="button success next" id="next" href="createrecordApprove"><span class="mif-lg fg-white">ทำรายการใหม่</span></a>
						</s:if>
					</div>
		</div>
		</div>
		<br>
		 
		 <div class="example" data-text="รายการ"> 
	         <div class="grid">
	         	<div class="row cells12">
	         		<div class="cell colspan1"> </div> 
					<div class="cell colspan3"> 
			        	เลือกสินค้า
				        <div class="input-control text full-size" data-role="input">
						    <s:textfield name="recordApproveModel.product_code" id="product_code" required="" /> 
						    <div class="button-group">
						    <button class="button primary" type="button" onclick="getProduct()"> <span class="mif-search"></span></button>
						    <button class="button danger" type="button" id="empty_product"><span class="mif-bin"></span></button>
						    </div>
						</div>
					</div>
					<div class="cell colspan4"> 
	         			ชื่อสินค้า
	         			<div class="input-control text full-size" data-role="input">
	         				<s:textfield name="recordApproveModel.product_name" id="product_name"/>
	         			</div>
					</div>      
	         		<div class="cell colspan1"> 
			        	จำนวน
				        <div class="input-control text full-size" dir="rtl">
						    <s:textfield type="number" name="recordApproveModel.qty" id="qty" required=""/> 
						</div>
					</div> 
	         		  
	         	 	<div class="cell align-left colspan3"><br>
	         	 		<s:if test="%{#approve_status=='CA' || #approve_status==null}">
						  <button type="submit" class="button success" name="add" id="add"><span class="mif-plus mif-lg fg-white"></span></button>
						  <button type="submit" class="button danger" name="delete" id="delete_product"><span class="mif-minus mif-lg fg-white"></span></button>  
						</s:if>
					</div>
			 	</div>  
			</div>
		</div>
		<div class="grid">	
			<div class="window ">
				<div class="row cells12 align-center  window-caption bg-cyan fg-white" >
					<div class="cell colspan1"></div>
			  		<div class="cell colspan3">รหัสสินค้า</div>
			  		<div class="cell colspan3">ชื่อสินค้า</div>
			  		<div class="cell colspan2 align-center">จำนวน</div>
			  		<div class="cell colspan2 align-center">หน่วย</div>
			  		<div class="cell colspan1"></div> 
			  	</div>
		  	</div>
		  	
		  	<div class="example ra_dt" data-text="รายละเอียด">
		  		<%
		  			if(request.getAttribute("ListRecordApproveDT") != null){
		  				List ListRecordApproveDT = (List) request.getAttribute("ListRecordApproveDT");
		  				Iterator recordApprovedtIter = ListRecordApproveDT.iterator();
		  				
		  				while(recordApprovedtIter.hasNext()){
		  					RecordApproveModel recordapprovemodel = (RecordApproveModel) recordApprovedtIter.next();
		  				%>
		  				<div class="row cells12 click" >	
				  			<div class="cell colspan1 bt" ></div>
				  			<h5 class="cell colspan3" > <input type="checkbox" name="itemno" id="itemno" value="<%=recordapprovemodel.getItemno() %>" /> <%=recordapprovemodel.getProduct_code() %></h5>
				  			<h5 class="cell colspan3" > <%=recordapprovemodel.getProduct_name() %></h5>
				  			<div class="cell colspan2 align-center"><%=recordapprovemodel.getQty() %></div>
				  			<div class="cell colspan2 align-center"><%=recordapprovemodel.getUnit_name() %></div> 
					  		<div class="cell align-right"></div>
				  		</div>
		  				<%
		  				}
		  			}
		  		
		  		%>
		  		
		  	</div>
		</div>
		 
		 <div class="example" data-text="รายละเอียด">
		 
	         <div class="grid">
	         <div class="row cells12">
	         	<div class="cell align-center colspan12"> 
	         		<p class="sub-leader fg-black"> เลขที่เอกสาร <s:property value="recordApproveModel.docno"/></p>
			        <s:hidden name="recordApproveModel.docno" id="docno" />
			        <s:hidden name="recordApproveModel.year" id="year" />
	         	</div>
	         </div>
	         <div class="row cells12">
	         	<div class="cell align-center colspan12"> 
	         		สถานะ 
		        	<s:if test="%{#approve_status=='CA' || #approve_status==null}">
					   	สร้างใบขออนุมัติซื้อ
					</s:if>
					<s:elseif test="%{#approve_status=='WA'}">
						รออนุมัติ
					</s:elseif>
					<s:elseif test="%{#approve_status=='AP'}">
						อนุมัติเรียบร้อยแล้ว
					</s:elseif>
					<s:else>
						ยกเลิกใบขออนุมัติ
					</s:else>
				</div>
	         </div>
	         	
	         	<div class="row cells12">
	         		<div class="cell colspan1"> </div>
					   
	         	
	         		<div class="cell colspan2"> 
			        	วันที่ออกใบขออนุมัติซื้อ
				        <div class="input-control text full-size">
						    <s:textfield name="recordApproveModel.record_approve_date" id="record_approve_date" required=""/>
						</div>
					</div>
					<div class="cell colspan2"> 
			        	วันที่กำหนดส่งสินค้า
				        <div class="input-control text full-size">
						    <s:textfield name="recordApproveModel.record_approve_send" id="record_approve_send" required=""/>
						</div>
					</div>   
	         	</div>
			 </div> 
		   
		
			<div class="grid">
			 	<div class="row cells12">
	         		<div class="cell colspan1"> </div>
	         		<div class="cell colspan4"> 
			        	ชื่อร้านค้าที่แนะนำ
				        <div class="input-control text full-size">
				        	<s:hidden name="recordApproveModel.vendor_id" id="vendor_id" required=""/>
						    <s:textfield name="recordApproveModel.vendor_name" id="vendor_name" readonly="true"/>
						    <div class="button-group">
						 	<button class="button primary" type="button" onclick="getvendor()"> <span class="mif-search"></span></button>
							<button class="button danger" type="button" id="delete"><span class="mif-bin"></span></button>
							</div>
						</div>
					</div>  
	         	</div>
	         	

         	</div>
		
	         <div class="grid">
			 	<div class="row cells12"> 
	         	 	<div class="cell align-center colspan2">
						<s:if test="%{#fromwindow=='true'}">
							<a class="button success" id="back" href="windowsPR"><span class="mif-lg fg-white">ย้อนกลับ</span></a>
						</s:if>
						<s:elseif test="%{#fromwindow=='po'}">
							<a class="button success" id="back" href="po_openwindowsPR"><span class="mif-lg fg-white">ย้อนกลับ</span></a>
						</s:elseif>
	         	 	</div>
	         	 	<div class="cell align-center colspan3">
	         	 	</div>  
					<div class="cell align-left colspan5"><br>
						  <s:if test="%{#approve_status=='CA'}">
							<button type="submit" class="button info" name="send_approve" onclick="sendApprove()" id="send_approve"> <span class="mif-paper-plane mif-lg fg-white"></span> ขออนุมัติ</button>
						  </s:if>
						  <s:if test="%{#approve_status=='CA' || #approve_status==null}">
						  <button type="submit" class="button success" name="save" id="save"> <span class="mif-floppy-disk mif-lg fg-white"></span> บันทึก</button>
						  <button type="button" class="button warning" onclick="getPrint()" id="print" ><span class="mif-print mif-lg fg-white"> ออกรายงาน</span></button>
						  </s:if>
					</div>
					<div class="cell align-right colspan2"><br>

						<s:if test="%{#fromwindow!='true' && #fromwindow!='view' && #fromwindow!='po'}">
						<a class="button success next" id="next" href="createrecordApprove"><span class="mif-lg fg-white">ทำรายการใหม่</span></a>
						</s:if>
					</div>
			 	</div>
			</div>
		</div> 
		 <s:hidden name="recordApproveModel.alertmsg" id="alertmsg"/>
		 <s:hidden name="recordApproveModel.saved" id="saved"/>
		 <s:hidden name="recordApproveModel.approve_status" id="approve_status"/>
        </form> 
		        
   		<script>
		function getProduct() {
   			var load = window.open('/dsc/windows_product','pr',
   			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
   		function getvendor() {
   			var load = window.open('/dsc/windows_entrancvendor','pr',
   			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
		function getPrint(){
			
			var tdocno = $("#docno").val();
			var tyear = $("#year").val();
			
    		swal({  title: "ยืนยันการพิมพ์เอกสาร ?",   
    				text: "หากคุณต้องการพิมพ์เอกสารให้กดปุ่มยืนยัน !",   
    				type: "warning",   
    				showCancelButton: true,   
    				confirmButtonColor: "#DD6B55",   
    				confirmButtonText: "ยืนยัน, ฉันต้องการพิมพ์เอกสาร !",   
    				cancelButtonText: "ไม่, ฉันไม่ต้องการพิมพ์เอกสาร !",   
    				closeOnConfirm: false,   
    				closeOnCancel: false,
    				showLoaderOnConfirm: true
    			},
    				 
    		function (isConfirm){
    		  	if (isConfirm) {
    			setTimeout(function(){
    				 
    				var load = window.open("/pcpnru/report/savetext-report.jsp?docno="+tdocno+"&year="+tyear+"" 
    						,'scrollbars=yes,menubar=no,height=600,width=800,resizable=yes,toolbar=no,location=no,status=no');
    			swal("พิมพ์เอกสารสำเร็จแล้ว!", "โปรดตรวจสอบรายละเอียดของเอกสารอีกครั้งเพื่อความถูกต้อง !", "success");
    			} 
    			 , 1000);
     
    			}else {    
    			 swal("ยกเลิกการพิมพ์เอกสาร", "คุณสามารถพิมพ์เอกสารได้อีกครั้งหลังจากปิดหน้าต่างนี้ !", "error");   
    			}
    		});
    		
	    }
        $(function(){
        	var select2unit = $("#unit").select2();
        	if($("#alertmsg").val() != ""){
        		swal("Error",$("#alertmsg").val() , "error");
        	}
        	$('#lightgallery').lightGallery();
        	$("#record_approve_send").datepicker({
            	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
            });   
        	$("#record_approve_date").datepicker({
            	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
            });   
        	$(".back").click(function(){
            	window.history.back();
            });
        	$("#delete_product").click(function(){
        		$("#product_code").val("-");
        		$("#qty").val(0);
        	});
        	$("#send_approve").click(function(){
        		
        		$("#approve_status").val("WA");
        		$("#product_code").val("-");
        		$("#qty").val(0);
        	});
        	$("#empty_product").click(function(){
        		$("#product_code").val("");
        		$("#product_name").val("");
        		$("#qty").val(0);
        	});
        	$("#save").click(function(){
        		$("#product_code").val("-");
        		$("#qty").val(0);
        	});
        	$(".next").click(function(){
        		$("#product_code").val("-");
        		$("#qty").val(0);
        	}); 
        	
        }); // close function
    	</script>
	</body>
</html>
