<%@page import="pcpnru.utilities.DateUtil"%>
<%@page import="com.mysql.jdbc.IterateBlock"%>
<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.masterData.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "006";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
	} 

	DateUtil dateUtil = new DateUtil(); 
%> 
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
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script> 
	    <script src="js/jquery.dataTables.min.js"></script>
  		<script src="js/select2.js"></script>
	    
	</head> 
	<body ng-app="controllerCalculator" ng-controller="SettingsController">
		 <div><%@include file="window-topmenu.jsp" %></div>
		 <h3 class="align-center">สร้าง รายการค่าใช้จ่ายของกลุ่ม รายได้</h3> 
		 <form id="fgrpCostcode" action="grpGcostcodeMaster.action" method="post">
		 
		 <div class="example" data-text="กลุ่มรายได้ ที่มาจากรายจ่าย">
            <table id="table_grp_costcode" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>เลขที่</th>
                	<th>รหัส -ชื่อโครงการ</th>
                    <th>รหัส-ชื่อ กลุ่มรายได้</th> 
                    <th>จำนวนรวม</th> 
                    <th>ราคารวม</th> 
                    <th> </th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List GrpCostCodeList,GrpCostCodeList1; 
        		
        		if(request.getAttribute("GrpCostCodeList")!=null){
        			GrpCostCodeList = (List) request.getAttribute("GrpCostCodeList");
			    }else{ 
			    	GrpGcostcodeMasterDB gccM = new GrpGcostcodeMasterDB();
			    	GrpCostCodeList1 = gccM.GetGrpCostCodeList("", dateUtil.curTHYear());
			    	GrpCostCodeList = GrpCostCodeList1;
			    } 
        		
        		int a = 1;
        		if(GrpCostCodeList != null){
        			Iterator costcodeIterate = GrpCostCodeList.iterator();
        			while(costcodeIterate.hasNext()){
        				GrpGCostCodeMasterModel gccInfo = (GrpGCostCodeMasterModel) costcodeIterate.next(); 
        				String pj = gccInfo.getProject_code();
        				String yr = gccInfo.getGrp_costyear();
        				String gc = gccInfo.getGrp_gcostcode();
        		%>
        			<tr>
	        			<td align="center"><a onclick="Opener('<%=gccInfo.getAmounttotal()%>','<%=gccInfo.getGrp_gcostcode()%>')"><span class="mif-checkmark" ></span></a></td>
	        			<td class="tdprojectCode" align="left"><%=gccInfo.getProject_code()%> - <%=gccInfo.getProject_name()%></td>  
	                    <td class="tdgrp_gcostcode" align="left"><%=gccInfo.getGrp_gcostcode()%> - <%=gccInfo.getGrp_gcostname()%></td> 
	                    <td align="right"><%=gccInfo.getQty()%></td>
	                    <td class="tdstandardprice" align="right"><%=gccInfo.getAmounttotal()%></td> 
	                    <td align="center"><a href="grpGcostcodeMaster.action?projecthd=<%=pj%>&yearhd=<%=yr%>&grp_gcostcodehd=<%=gc%>"><span class="mif-cross deletebt"></span></a> </td>
                	</tr>
        		<%		
        		a++;
        			} 
        		} 
        		%>
        			 
                </tbody>
            </table>
        </div> <!-- End of example table -->
		 
		 <div class="example" data-text="รายละเอียด"> 
		 <div class="grid">
	         	<div class="row cells12">
	         		<div class="cell colspan5">
	         		 โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="projectCode" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ" >
							   <option value="" >กรุณาเลือกโครงการ</option>
							   <%
							   String projectcode = "";
								   if(request.getAttribute("projectcode")!=null){
									   projectcode = (String)request.getAttribute("projectcode");
								   }
							     
							   	List projectMasterList = null;
							    
							    ProjectMasterDB projM = new ProjectMasterDB();
							    projectMasterList = projM.getListProject_Join_Projecthead("", "","",""); 
							     
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next();
			      				%>  
				      			<option <% if(pjModel.getProject_code().equals(projectcode)){ %>selected <%} %> value="<%=pjModel.getProject_code()%>" ><%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%></option>
								<%		} 
									}
								%>
					   		</select>
						</div>
					</div>
					<div class="cell colspan4">  
		        	ชื่อกลุ่มรายได้
			        <div class="input-control text full-size">
					    <input type="text" name="grpgcostcodemastermodel.grp_gcostname" required=""  />
					    
					</div>
					</div>
					 <div class="cell colspan1">  
					  
		        	ปี
			        <div class="input-control text full-size">
					    <input type="text" id="year" name="grpgcostcodemastermodel.grp_costyear" value="<%=dateUtil.curTHYear()%>"  />
					</div>
				</div>
					<div class="cell colspan2 align-left"><br>
						  <button class="button success" name="add">สร้างชื่อรายการค่าใช้จ่าย</button>
					</div>
	         	</div> 
			 
			</div>
		   
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_costcode_c" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th><label class="input-control small-check checkbox"> 
                		<input type="checkbox" id="checkAll" data-show="indeterminate" />
                		<span class="check"></span> 
                        </label> เลือกทั้งหมด</th>
                	<th>รหัส -ชื่อโครงการ</th>
                    <th>รหัส-ชื่อ รายจ่าย</th> 
                    <th>จำนวน</th> 
                    <th>ราคาต่อหน่วย</th>  
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List CostCodeList_C,CostCodeList_C1; 
        		
        		if(request.getAttribute("CostCodeList_C")!=null){
        			CostCodeList_C = (List) request.getAttribute("CostCodeList_C");
			    }else{ 
			    	GrpGcostcodeMasterDB gccM = new GrpGcostcodeMasterDB();
			    	CostCodeList_C1 = gccM.GetCostCodeList_C("", dateUtil.curTHYear());
				   	CostCodeList_C = CostCodeList_C1;
			    } 
        		
        		int x = 1, y = 0;
        		if(CostCodeList_C != null){
        			Iterator costcodeIterate = CostCodeList_C.iterator();
        			while(costcodeIterate.hasNext()){
        				GrpGCostCodeMasterModel gccInfo = (GrpGCostCodeMasterModel) costcodeIterate.next();
        		%>
        			<tr>
	        			<td align="center"><input type="checkbox" name="archk" value="<%=y%>" /> <%=x%></td>
	        			<td class="" align="left"><%=gccInfo.getProject_code()%> - <%=gccInfo.getProject_name()%></td>  
	                    <td class="" align="left"><%=gccInfo.getGcostcode()%> - <%=gccInfo.getGcostcode_name()%>
	                    	<input type="hidden" name="argcostcode" value="<%=gccInfo.getGcostcode()%>" />
	                    </td> 
	                    <td dir="rtl"><input type="number" step="0.01" id="qty" name="qrqty" size="10" ></td>
	                    <td class="tdstandardprice" align="right"><%=gccInfo.getAmount_c()%>
	                    	<input type="hidden" name="aramount_c" value="<%=gccInfo.getAmount_c()%>" />
	                    </td> 
                	</tr>
        		<%		
        		x++;y++;
        			} 
        		} 
        		%>
        			 
                </tbody>
            </table>
        </div> <!-- End of example table -->
        </form> 
          
   		<script>
   		 
        function Opener(amttotal,grp_gcostcode) {
            
            window.opener.document.getElementById ("fundprice").value = amttotal;  
            window.opener.document.getElementById ("grp_gcostcode").value = grp_gcostcode;
            window.close();
        } 
   		
   		function getGcostcode(projectcode, year) {
			var load = window.open('/pcpnru/window-gcostcode-receive.jsp?projectcode='+projectcode+'&year='+year+' ','receive',
			             'scrollbars=yes,menubar=no,height=600,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
		}
   		
   	 $(function(){
        	$("#checkAll").change(function () {
       		    $("input:checkbox").prop('checked', $(this).prop("checked"));
       		});
        	 
        	
        	$("#project_code").change(function () {
        		$('#fgrpCostcode').submit();
        	});
         
       		$("#project_code").select2(); 
    	   	 
       		 
       		$('#table_grp_costcode').DataTable( { 
       			scrollY: '18vh',
       			scrollX: true, 
       			scrollCollapse: true,
                ordering: false,
                "lengthMenu": [[5, 25, 50, 100, -1], [5, 25, 50, 100, "All"]] 
            } );
       		
       		$('#table_costcode_c').DataTable( { 
       			scrollY: '33.8vh',
       			scrollX: true, 
       			scrollCollapse: true,
                ordering: false,
                "lengthMenu": [[7, 25, 50, 100, -1], [7, 25, 50, 100, "All"]] 
            } );
       		
   	});	
        	
        	
        	/*	$('#table_costcode_c').dataTable( {
			scrollY: 300,
			"orderable": false,
			"targets": 0,
			"lengthMenu": [ 100, 75, 50, 25, 10 ]
	 	} );  */
        
    	</script>
	</body>
</html>
