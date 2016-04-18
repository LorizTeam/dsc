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
		<title>สร้าง รายการค่าใช้จ่ายของกลุ่ม รายได้</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/select2.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
        <script src="js/jquery.dataTables.min.js"></script>
  		<script src="js/angular.min.js"></script>
  		<script src="js/select2.js"></script>
	</head>

	<body>
		 <div><%@include file="window-topmenu.jsp" %></div>
		 <h3 class="align-center">สร้าง รายการค่าใช้จ่ายของกลุ่ม รายได้</h3>
		 <form id="windowreceive" action="windowGroupcostcodeMaster.action" method="post">
		 
		 <div class="example" data-text="รายละเอียด"> 
	         <div class="grid"> 
	         	<div class="row cells12 ">
	         		<div class="cell colspan10"> 
	         		 โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="projectCode" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ"> 
							   <%
							    String projectcode = (String) request.getParameter("projectcode");
							    if(projectcode==null) projectcode = (String) request.getAttribute("projectcode");
							   	String year = (String) request.getParameter("year");
							   	if(year==null) year = (String) request.getAttribute("year");
							   
							   	List projectMasterList1 = null;
							   	ProjectMasterDB projM = new ProjectMasterDB();
							   	projectMasterList1 = projM.getListProject_Join_Projecthead(projectcode, "","","");
							   	List projectMasterList = projectMasterList1;
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next();
			      				%>  
				      			<option value="<%=pjModel.getProject_code()%>" >
				       			 	<%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%>
				       			</option>
								<%		} 
									}
								%>
					   		</select>
						</div>
	         		</div>
	         	</div>
			  	<div class="row cells12">
			  		<div class="cell colspan2"> 
			        	รหัส รายได้
				        <div class="input-control text full-size">
						    <s:textfield name="groupcostcodemastermodel.costCode" id="costCode" readonly="true" />
						    <s:hidden name="groupcostcodemastermodel.costCodeHD" id="costCodeHD"/> 
						    <input type="hidden" name="year" value="<%=year%>">  
						</div>
					</div>
			        <div class="cell colspan4"> 
			        	  ชื่อ รายได้
				        <div class="input-control text full-size">
						    <s:textfield name="groupcostcodemastermodel.costName" id="costName" required=""/>
						</div>
					</div>
					<div class="cell colspan2"> 
			        	 ราคากลาง
				        <div class="input-control text full-size">
						    <s:textfield type="number" step="0.01" name="groupcostcodemastermodel.standardprice" id="standardprice" required=""/>
						</div>
					</div>
					<div class="cell colspan2"> 
			        	ต้นทุน
				        <div class="input-control text full-size">
				        	
						    <s:textfield type="number" step="0.01" name="groupcostcodemastermodel.fundprice" id="fundprice" required=""/>
						</div>
					</div> 
					 
			    </div>
			  <!--  <div>
			    <div class="cell align-center"><br>
						  <button class="button success" name="add">สร้างชื่อรายการค่าใช้จ่าย</button> 
						  <button class="button primary" name="update">แก้ไขชื่อรายการค่าใช้จ่าย</button> 
						  <button class="button danger" name="delete" >ลบชื่อรายการค่าใช้จ่าย</button>  
				</div>   
			    </div> -->
			 </div>
			 
			 <s:hidden name="groupcostcodemastermodel.type_gcostcode" id="type_gcostcode" value="1"/>
		   
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_project" class="dataTable striped border bordered" data-role="datatable" data-searching="true">
                <thead>
                <tr>  
                	<th> </th>
                	<th>โครงการ</th>
                    <th>รหัส-รายได้</th>
                    <th>ชื่อ-รายได้</th>
                    <th>ราคากลาง</th> 
                    <th>ต้นทุน</th>  
                    <th>วันเวลา-รายการค่าใช้จ่าย</th>
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List groupcostCodeMasterList = null;
                GroupcostcodeMasterDB ccM = new GroupcostcodeMasterDB();
        		groupcostCodeMasterList = ccM.WindowGroupCostCodeMasterReceive(projectcode, year,"", "","1");
        		int x = 1;
        		if(groupcostCodeMasterList != null){
        			
        			Iterator costcodeIterate = groupcostCodeMasterList.iterator();
        			while(costcodeIterate.hasNext()){
        				GroupCostCodeMasterModel gccInfo = (GroupCostCodeMasterModel) costcodeIterate.next();
        				
        				
        		%>
        			<tr >
        			<td align="center"><a onclick="Opener('<%=gccInfo.getCostCode()%>','<%=gccInfo.getCostName()%>')"><span class="mif-checkmark" ></span></a></td>
        			<td class="tdprojectCode" align="left"><%=gccInfo.getProject_code()%> - <%=gccInfo.getProject_name()%></td>  
                    <td class="tdcostCode" align="center"><%=gccInfo.getCostCode()%></td>
                    <td class="tdcostName" align="left"><%=gccInfo.getCostName()%></td>
                    <td class="tdstandardprice" align="center"><%=gccInfo.getStandardprice() %></td>
                    <td class="tdfundprice" align="center"><%=gccInfo.getFundprice() %></td>   
                    <td align="center"><%=gccInfo.getDateTime()%></td>  
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
        	var select2projectcode = $("#project_code").select2();
        	
        	var table = $('#table_project').dataTable();
            $('#table_project tbody').on( 'click', 'tr', function () { 
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	            select2projectcode.val("").trigger("change");
    	            $("#costCode").val("");
    	            $("#costCodeHD").val("");
    	            $("#costName").val("");
    	            $("#standardprice").val("");
    	            $("#fundprice").val("");
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	            var $index = $(this).index();
    	            
    	           	var forsplit = $(".tdprojectCode").eq($index).text().split(" - ");
    	           	select2projectcode.val(forsplit[0]).trigger("change");
    	           	
    	            $("#costCode").val($(".tdcostCode").eq($index).text());
    	            $("#costCodeHD").val($(".tdcostCode").eq($index).text());
    	            $("#costName").val($(".tdcostName").eq($index).text());
    	            $("#standardprice").val($(".tdstandardprice").eq($index).text());
    	            $("#fundprice").val($(".tdfundprice").eq($index).text());
    	        }
    	    });
            
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
            
        });
    	</script>
	</body>
</html>
