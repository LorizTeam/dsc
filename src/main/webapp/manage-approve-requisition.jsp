<%@page import="com.mysql.jdbc.IterateBlock"%>
<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.masterData.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>จัดการ ผู้มีอำนาจการอนุมัติ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
	</head>

	<body>
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">จัดการ ผู้มีอำนาจการอนุมัติ</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form action="authenMaster.action" method="post">
	         <div class="grid">
	         	<div class="row cells12">  
					<div class="cell colspan2"> 
			        	รหัส ผู้มีอำนาจการอนุมัติ
				        <div class="input-control text full-size">
						    <s:textfield name="marModel.manageapprove" id="manageapprove" readonly="true"/> 
						</div>
					</div> 
			        <div class="cell colspan4"> 
			        	ชื่อ ผู้มีอำนาจการอนุมัติ
				        <div class="input-control text full-size">
						    <s:textfield name="marModel.manageapprovename" id="manageapprovename" required=""/>
						</div>
					</div> 
					<div class="cell colspan2"> 
			        	จำนวนเงิน ที่อนุมัติได้
				        <div class="input-control text full-size">
						    <s:textfield name="marModel.budget" id="budget" required=""/>
						</div>
					</div>
					<div class="cell align-left colspan4"><br>
						  <button class="button success" name="add">สร้าง</button> 
						  <button class="button primary" name="update">แก้ไข</button> 
						  <button class="button danger" name="delete">ลบ</button> 
				</div>
	         	 
			    </div>
			 </div> 
		 </form>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_authen" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>ลำดับ</th>
                	<th>รหัส -สิทธิ์การใช้งาน</th>
                    <th>ชื่อ -สิทธิ์การใช้งาน</th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List getListAuthen = null;
                AuthenMasterDB am = new AuthenMasterDB();
                getListAuthen = am.getListAuthen("");
        		int x = 1;
        		if(getListAuthen != null){
        			
        			Iterator amIterate = getListAuthen.iterator();
        			while(amIterate.hasNext()){
        				AuthenMasterModel anInfo = (AuthenMasterModel) amIterate.next();  
        		%>
        			<tr>
        			<td class="tdhidden" align="center"><%=x%></td>
        			<td class="tdant" align="left"><%=anInfo.getAuthen_type()%></td>  
                    <td class="tdantn" align="left"><%=anInfo.getAuthen_type_name()%></td> 
                	</tr>
        		<%		
        		x++;
        			}
        			
        		}else{
        		%>
        			<tr>  
                    <td colspan="6" align="center">ไม่พบข้อมูล</td>   
                	</tr>
        		<%
        		}
                %>
                </tbody>
            </table>
        </div> <!-- End of example table -->  
         
   		<script>
        $(function(){
         
        	var table = $('#table_authen').DataTable( { 
              	scrollY: '50vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[14, 25, 50, 100, -1], [14, 25, 50, 100, "All"]] 
            } );
        	
            $('#table_authen tbody').on( 'click', 'tr', function () { 
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	            
    	            $("#authentype").val("");
    	            $("#authentypename").val(""); 
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	            var $index = $(this).index();
    	              
    	            $("#authentype").val($(".tdant").eq($index).text());
    	            $("#authentypename").val($(".tdantn").eq($index).text()); 
    	        }
    	    });
        });
    	</script>
	</body>
</html>
