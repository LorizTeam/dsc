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
		String page_code = "005";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
	} 
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
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">สร้าง รายการค่าใช้จ่ายของกลุ่ม รายได้</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form action="groupcostcodeMaster.action" method="post">
		 <div class="grid">
	         	<div class="row cells12">
	         		<div class="cell colspan6">
	         		 โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="projectCode" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
							   <option value="" >กรุณาเลือกโครงการ</option>
							   <%
							   	List projectMasterList1 = null;
							   	ProjectMasterDB projM = new ProjectMasterDB();
							   	projectMasterList1 = projM.getListProject_Join_Projecthead("", "","","");
							   	List projectMasterList = projectMasterList1;
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next();
			      				%>  
				      			<option value="<%=pjModel.getProject_code()%>" ><%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%></option>
								<%		} 
									}
								%>
					   		</select>
						</div>
					</div>
					<div class="cell colspan6"> 
			        	 ชื่อ รายได้
				        <div class="input-control text full-size">
						    <s:textfield name="groupcostcodemastermodel.costName" id="costName" required=""/>
						    <s:hidden name="groupcostcodemastermodel.costCodeHD" id="costCodeHD"/>
						</div>
					</div>
	         	</div>
			  	<div class="row cells12"> 
					<div class="cell colspan3"> 
			        	 ราคากลาง
				        <div class="input-control text full-size">
						    <s:textfield name="groupcostcodemastermodel.standardprice" id="standardprice" required=""/>
						</div>
					</div>
					<div class="cell colspan3"> 
			        	ต้นทุน
				        <div class="input-control text full-size">
				        <input type="hidden" name="grp_gcostcode" id="grp_gcostcode"/>
					 	<s:textfield id="fundprice" name="groupcostcodemastermodel.fundprice" readonly="true" />
						<div class="button-group">
							<button class="button mini-button" type="button" onclick="deleteCC();"><span class="mif-bin"></span></button>
							<button class="button mini-button" type="button" onclick="javascript:getGcostcode('0001','2559');"> <span class="mif-search"></span></button>
				 	 	</div>
						
						</div>
					</div> 
					<div class="cell colspan6 align-left"><br>
						  <button class="button success" name="add">สร้างชื่อ รายได้</button> 
						  <button class="button primary" name="update">แก้ไขชื่อ รายได้</button> 
						  <button class="button danger" name="delete">ลบชื่อ รายได้</button> 
					</div> 
			    </div>  
			 
			 <s:hidden name="groupcostcodemastermodel.type_gcostcode" id="type_gcostcode" value="1"/>
			</div>
		 </form>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_project_rec" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>ลำดับ</th>
                	<th>โครงการ</th>
                    <th>รหัส-รายได้</th>
                    <th>ชื่อ-รายได้</th>
                    <th>ราคากลาง</th> 
                    <th>ต้นทุน</th>  
                    <th>วันเวลา-รายได้</th>
                    <th> </th>
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List groupcostCodeMasterList = null;
                GroupcostcodeMasterDB ccM = new GroupcostcodeMasterDB();
        		groupcostCodeMasterList = ccM.GetGroupCostCodeMasterList("","", "","1");
        		int x = 1;
        		if(groupcostCodeMasterList != null){
        			
        			Iterator costcodeIterate = groupcostCodeMasterList.iterator();
        			while(costcodeIterate.hasNext()){
        				GroupCostCodeMasterModel gccInfo = (GroupCostCodeMasterModel) costcodeIterate.next();
        				
        				
        		%>
        			<tr>
	        			<td align="center"><%=x%></td>
	        			<td class="tdprojectCode" align="left"><%=gccInfo.getProject_code()%> - <%=gccInfo.getProject_name()%></td>  
	                    <td class="tdcostCode" align="center"><%=gccInfo.getCostCode()%></td>
	                    <td class="tdcostName" align="left"><%=gccInfo.getCostName()%></td>
	                    <td class="tdstandardprice" align="right"><%=gccInfo.getStandardprice()%></td>
	                    <td class="tdfundprice" align="right"><%=gccInfo.getFundprice()%></td>   
	                    <td align="left"><%=gccInfo.getDateTime()%></td>
	                    <td align="center"><div class="cell colspan1 align-right">
							<button class="button mini-button" type="button" onclick="javascript:getReqiToRec('<%=gccInfo.getProject_code()%>','<%=gccInfo.getCostCode()%>','<%=gccInfo.getGrp_gcostcode()%>');"> 
							<span class="mif-search"></span></button>
						</div></td>
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
   		 
   		function getGcostcode(projectcode, year) {
			var load = window.open('/pcpnru/grp-gcostcode-receive.jsp?projectcode='+projectcode+'&year='+year+' ','receive',
			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
		}
   		function getReqiToRec(tprojectcode, tgcostcode, tgrp_gcostcode) {
   			var load = window.open('/pcpnru/windowRequisitionToReceive.action?project_code='+tprojectcode+'&gcostcode='+tgcostcode+'&grp_gcostcode='+tgrp_gcostcode+' ','',
            'scrollbars=yes,menubar=no,height=600,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
   		 
   			$("#standardprice").blur(function (){
   				 
   			var budget = $("#standardprice").val(); 
   			var t1 = "";
   			if(budget == "NaN"){
   				t1 = "0";
   			}else if(budget == ""){
   				t1 = "0";
   			}
   			else{
   				budget = budget.replace(/,/g,"");
   		    var t1 = parseFloat(budget).toLocaleString("en-US");
   			} 
   			$("#standardprice").val(t1);
   		});
   		
   		function deleteCC() {
			$("#fundprice").val(""); 
		}
   		
        $(function(){
        	var select2projectcode = $("#project_code").select2();
        	
        	var table = $('#table_project_rec').DataTable( { 
         		scrollY: '39vh',
         		scrollX: true, 
         		scrollCollapse: true,
                ordering: false,
                "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                "initComplete": function () {
                    var api = this.api();
                    api.$('td').click( function () {
                        api.search( this.innerHTML ).draw();
                    } );
                }  
            });
        	
            $('#table_project_rec tbody').on( 'click', 'tr', function () { 
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	            select2projectcode.val("").trigger("change");
    	        //    $("#costCode").val("");
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
    	           	
    	         //   $("#costCode").val($(".tdcostCode").eq($index).text());
    	            $("#costCodeHD").val($(".tdcostCode").eq($index).text());
    	            $("#costName").val($(".tdcostName").eq($index).text());
    	            
    	            var st = $(".tdstandardprice").eq($index).text();
    	       //     st = st.replace(",", "");
    	            
    	            $("#standardprice").val(st);
    	            
    	            var fp = $(".tdfundprice").eq($index).text();
    	       //     fp = fp.replace(",", "");
    	            $("#fundprice").val(fp);
    	        }
    	    });
        });
    	</script>
	</body>
</html>
