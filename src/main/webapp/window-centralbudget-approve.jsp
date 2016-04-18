<%@page import="com.mysql.jdbc.IterateBlock"%>
<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.masterData.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>รายละเอียด การขออนุมัติ</title>
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
        <script src="js/jquery.dataTables.min.js"></script>
  		<script src="js/angular.min.js"></script>
  		<script src="js/select2.js"></script>
	</head>

	<body>
		 <div><%@include file="window-topmenu.jsp" %></div>
		 <h3 class="align-center">รายละเอียด ขออนุมัติ การโยกงบกลาง</h3>
		 <form id="windowreceive" action="windowCentralBudgetApprove.action" method="post"> 
		 
        <div class="example" data-text="รายการ">
            <table id="table_approveRockingBudget" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>เลขที่</th>
                	<th>โครงการ</th>
                	<th>ค่าใช้จ่าย</th>
                    <th>เงินก่อนโยก</th>
                    <th>เงินที่มีการโยกงบ</th>
                	<th>คงเหลือ</th>
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List WCentralBudgetApproveList = (List) request.getAttribute("WCentralBudgetApproveList");
                 
        		int x = 1, y = 0;
        		if(WCentralBudgetApproveList != null){
        			
        			Iterator amIterate = WCentralBudgetApproveList.iterator();
        			while(amIterate.hasNext()){
        				CentralBudgetForm anInfo = (CentralBudgetForm) amIterate.next();  
        		%>
        			<tr>
	        			<td align="center"><%=x%></td>
	        			<td align="left"><%=anInfo.getProject_name()%></td>
	        			<td align="left"><%=anInfo.getGcostname()%></td>  
	                    <td align="right"><%=anInfo.getFrombalance()%></td>  
	                    <td align="right"><%=anInfo.getRocking_budget()%></td> 
	                    <td align="right"><%=anInfo.getBalance()%></td>  
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
       </form>
       
        <script type="text/javascript">
        function Opener(tcostcode, tcostname) {
             
            window.opener.document.getElementById ("gcostcode").value = tcostcode;
            window.opener.document.getElementById ("gcostname").value = tcostname;
      
            window.close();
        }
    	</script> 
         
   		<script>
        $(function(){
        	$("#gcostcode").select2();
        	$("#project_code").select2();
        	
            
            $('.deletebt').click(function () {  
	        	 
        		var index = $(".deletebt").index(this); 
        		
        		var textp = $(".tdprojectCode").eq(index).text(); 
   				var textp1 = textp.split(" - "); 
   				textp = textp1[0];  
   				var textc = $(".tdcostCode").eq(index).text(); 
   				
        		$("#project_code").val(textp);
        		$("#costCode").val(textc); 
        		
		    	$("#windowreceive").submit(); 
    	}); 
            
            var table = $('#table_approveRockingBudget').DataTable( { 
              	scrollY: '50vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[14, 25, 50, 100, -1], [14, 25, 50, 100, "All"]] 
            } );   
            
        });
    	</script>
	</body>
</html>
