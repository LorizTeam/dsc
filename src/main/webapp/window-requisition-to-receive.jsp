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
		<title>รายละเอียด รายจ่าย</title>
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
		 <h3 class="align-center">รายละเอียด รายได้ ที่นำรายจ่ายมาใช้</h3>
		 <form id="windowreceive" action="windowRockingBudgetApprove.action" method="post">
		 
		 <div class="example" data-text="รายละเอียด"> 
	         <div class="grid"> 
	         	<div class="row cells12 ">
	         		<div class="cell colspan6"> 
	         		โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="rockingBudgetForm.project_code" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
							   <option value="">กรุณาเลือกโครงการ</option>
							   <%
							   
							   	List projectMasterList = (List) request.getAttribute("projectMasterList");
							    
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next(); 
						        				
						        	%>
						        			<option selected value="<%=pjModel.getProject_code()%> - <%=pjModel.getYear()%>" >
							       			 	<%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%> - ปี <%=pjModel.getYear() %>
							       			</option>
						        	<%
						        			
			      						} 
									}
								%>
					   		</select>
					   		<s:hidden id="year" name="rockingBudgetForm.year" /> 
						</div>
	         		</div>
	         		<div class="cell colspan6"> 
			        	รายได้
				        <div class="input-control text full-size">
				        	<% List groupcostCodeList = (List) request.getAttribute("groupcostCodeList");
			    				String gcostcode = (String) request.getAttribute("gcostcode");
			    			if (groupcostCodeList != null) {
			    				Iterator Iterate = groupcostCodeList.iterator();
			    				%>
			    				<select name="rockingBudgetForm.gcostcode" id="gcostcode">
			    					<option selected value="">-- please Select --</option>
			    				<%
			        			while(Iterate.hasNext()){
			        				GroupCostCodeMasterModel gcc1Info = (GroupCostCodeMasterModel) Iterate.next(); 
					        				
					        	%> 
					        	<option selected value="<%=gcc1Info.getCostCode()%>" ><%=gcc1Info.getCostName()%></option> 
					        	<% 	
		      						} 
			    				%>
			    				</select>
							<%	}  %>
						     
						</div>
					</div>
	         	</div> 
			 </div>
			 
			 <s:hidden name="groupcostcodemastermodel.type_gcostcode" id="type_gcostcode" value="1"/>
		   
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_approveRockingBudget" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>เลขที่</th>
                    <th>รายจ่าย</th>
                    <th>จำนวน</th> 
                    <th>จำนวนเงิน</th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List GrpReqisToReceiveList = (List) request.getAttribute("GrpReqisToReceiveList");
                 
        		int x = 1, y = 0;
        		if(GrpReqisToReceiveList != null){
        			
        			Iterator amIterate = GrpReqisToReceiveList.iterator();
        			while(amIterate.hasNext()){
        				GrpGCostCodeMasterModel anInfo = (GrpGCostCodeMasterModel) amIterate.next();  
        		%>
        			<tr>
	        			<td align="center"><%=x%></td>
	        			<td align="left"><%=anInfo.getGrp_gcostname()%></td>  
	                    <td align="right"><%=anInfo.getQty()%></td>   
	                    <td align="right"><%=anInfo.getAmounttotal()%></td>
                	</tr>
        		<%		
        		x++;
        			}
        			
        		}else{
        		%>
        			<tr>  
                    <td colspan="4" align="center">ไม่พบข้อมูล</td>   
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
