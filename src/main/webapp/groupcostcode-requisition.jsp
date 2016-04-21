<%@page import="com.mysql.jdbc.IterateBlock"%>
<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.masterData.*" %> 
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "004";
		
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
		<title>สร้าง รายการค่าใช้จ่ายของกลุ่ม รายจ่าย</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		<link href="css/select2.css" rel="stylesheet"> 
		 
	 	<script src="js/jquery-2.1.3.min.js"></script> 
	 	<script src="js/metro.js"></script>
	 	<script src="js/jquery.dataTables.min.js"></script> 
	    <script src="js/select2.js"></script>      
  	 		
	</head>

	<body>
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">สร้าง รายการค่าใช้จ่ายของกลุ่ม รายจ่าย</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form action="groupcostcodeMaster.action" method="post"> 
	         <div class="grid">
	         	<div class="row cells12">
	         		<div class="cell colspan6"> 
			        	 โครงการ
				        <div class="input-control text full-size"> 
						    <select id="project_code" name="projectCode" required="required" >
							   <option value="" >กรุณาเลือกโครงการ</option>
							   <%
							   	List projectMasterList1 = null;
							    String project_code = "";
							    if (request.getAttribute("project_code") != null) project_code = (String) request.getAttribute("project_code");
							    if (request.getAttribute("projectMasterList") == null) {
									ProjectMasterDB projM = new ProjectMasterDB();
									projectMasterList1 = projM.getListProject_Join_Projecthead("", "","","");
								}else{
									projectMasterList1 = (List) request.getAttribute("projectMasterList");
								}
							    
							    List projectMasterList = projectMasterList1;
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next();
			      				%>  
				      			<option <%if(pjModel.getProject_code().equals(project_code)){ %> selected <%} %>  value="<%=pjModel.getProject_code()%>" ><%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%></option>
								<%		} 
									}
								%>
					   		</select>  
						</div>
					</div>
					<div class="cell colspan2"> 
			        	 ราคาต่อหน่วย
				        <div class="input-control text full-size">
						    <s:textfield name="groupcostcodemastermodel.amount" id="amount" required=""/>
						</div>
					</div> 
	         	</div>
	         	<div class="row cells12">
	         		<div class="cell colspan12"> 
						พนักงาน
						<div class="input-control full-size" >
                            <select name="emp" class="js-example-basic-multiple" multiple="multiple" data-placeholder="เลือก พนักงาน" required="required" >
                            	<%List grouPersonnelMasterList = null;
                                PersonnelMasterDB pn = new PersonnelMasterDB();
                                grouPersonnelMasterList = pn.GetPersonnelList("", "", "", "", "");
                                for (Iterator iterEpm = grouPersonnelMasterList.iterator(); iterEpm.hasNext();) {
                                	PersonnelMasterModel pmInfo = (PersonnelMasterModel) iterEpm.next(); 
                                %>
                                	<option value="<%=pmInfo.getPersonnel_id()%>-<%=pmInfo.getManday()%>" ><%=pmInfo.getPersonnel_name()%> <%=pmInfo.getPersonnel_lastname()%> - <%=pmInfo.getManday()%></option>
                                <%		}  
								%> 
                            </select>
                        </div>
					</div>
	         	</div>
	         	
			  	<div class="row cells12">
			  		 
			        <div class="cell colspan6"> 
			        	 รายการค่าใช้จ่าย
				        <div class="input-control text full-size">
						    <s:textfield name="groupcostcodemastermodel.costName" id="costName" required=""/>
						    <s:hidden name="groupcostcodemastermodel.costCodeHD" id="costCodeHD"/>
						</div>
					</div>
					 
					 <div class="cell colspan6"><br>
						  <button class="button success" name="add">สร้างชื่อรายการค่าใช้จ่าย</button> 
						  <button class="button primary" name="update">แก้ไขชื่อรายการค่าใช้จ่าย</button> 
						  <button class="button danger" name="delete">ลบชื่อรายการค่าใช้จ่าย</button> 
					</div>
			    </div>
			
			    
			  
			 </div>
			 <s:hidden name="groupcostcodemastermodel.type_gcostcode" id="type_gcostcode" value="2"/>
		 </form>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_project_req" class="cell-border hover display compact nowrap" cellspacing="0"  width="100%">
                <thead>
                <tr>  
                	<th>ลำดับ</th>
                	<th>ชื่อ-โครงการ</th>
                    <th>รหัส-รายการค่าใช้จ่าย</th>
                    <th>ชื่อ-รายการค่าใช้จ่าย</th>
                    <th>ราคาต่อหน่วย</th> 
                </tr>
                </thead>  
                <tbody>
                <%
                List groupcostCodeMasterList = null;
                
        		int x = 1;
        		if(request.getAttribute("groupcostCodeMasterList")!=null){
        			groupcostCodeMasterList = (List) request.getAttribute("groupcostCodeMasterList");
        		}else{
        			GroupcostcodeMasterDB ccM = new GroupcostcodeMasterDB();
                	groupcostCodeMasterList = ccM.GetGroupCostCodeMasterList_Req(project_code, "", "","2");
        		}
        			
        			Iterator costcodeIterate = groupcostCodeMasterList.iterator();
        			while(costcodeIterate.hasNext()){
        				GroupCostCodeMasterModel gccInfo = (GroupCostCodeMasterModel) costcodeIterate.next(); 
        				
        		%>
        			<tr>
        			<td align="center"><%=x%></td>  
        			<td class="tdprojectCode" align="left"><%=gccInfo.getProject_code()%> - <%=gccInfo.getProject_name()%></td>
                    <td class="tdcostCode" align="center"><%=gccInfo.getCostCode()%></td>
                    <td class="tdcostName" align="left"><%=gccInfo.getCostName()%></td>
                    <td class="tdamount" align="right"><%=gccInfo.getAmount()%></td> 
                	</tr>
        		<%		
        		x++;
        			}
        			 
        		%>
                </tbody>
            </table>
        </div> <!-- End of example table -->  
        
        <script type="text/javascript">
        
        $(document).ready(function() {
            $('#table_project_req').DataTable( { 
              	scrollY:        '35vh', 
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
        });
        
        $("#project_code").select2();
        $(".js-example-basic-multiple").select2();
        </script>
        
   		<script> 
	   		$(".js-example-basic-multiple").change(function(){ 
	   			 
	   			var num = '';
	   			num = $("#amount").val(); 
	   			if(num==''){
	   				num = 0;
	   			}else{
	   				num = num+1;
	   			}
	   			var e = $("#emp").val().split("-");
	   			var t = $(".select2-selection__choice").text().split(" - ");
	   			e = e[1]; 
	   			 
	   			$("#amount").val(num);
	   		}); 
   		
   			$("#amount").blur(function (){
   			var amount = $("#amount").val(); 
   			var t1 = "";
   			if(amount == "NaN"){
   				t1 = "0";
   			}else if(amount == ""){
   				t1 = "0";
   			}
   			else{
   			amount = amount.replace(/,/g,"");
   		    var t1 = parseFloat(amount).toLocaleString("en-US");
   			} 
   		 /* 		alert(t1)
   		
   			var i = parseFloat(amount);
   			if(isNaN(i)) { i = 0.00; }
   			var minus = '';
   			if(i < 0) { minus = '-'; }
   			i = Math.abs(i);
   			i = parseInt((i + .005) * 100);
   			i = i / 100;
   			s = new String(i);
   			if(s.indexOf('.') < 0) { s += '.00'; }
   			if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
   			s = minus + s;
   			amount = s;   
   			
   			var delimiter = ","; // replace comma if desired
   			amount = amount.replace(",","");
   			
   			var a = amount.split('.',2) 
   			a[0] = a[0].replace(",","");
   			 alert(a[1]);
   			var d = a[1];
   			
   			var i = parseInt(a[0]); 
   			
   			if(isNaN(i)) { return ''; }
   			var minus = '';
   			if(i < 0) { minus = '-'; }
   			i = Math.abs(i);
   			 
   			var n = new String(i);
   			var a = [];
   			while(n.length > 3) {
   				var nn = n.substr(n.length-3);
   				a.unshift(nn);
   				n = n.substr(0,n.length-3);
   			}
   			
   			if(n.length > 0) { a.unshift(n); }
   			n = a.join(delimiter);
   		 	
   		//	if(d.length < 1) { 
   				amount = n; 
   		//	}else { 
   		//		amount = n + '.' + d; 
   		//	}
   		/*		if(d) {
   				if(d.length < 1) {
   					amount = n;
   					} else {
   					amount = n + '.' + d;
   					}
   					} else {
   					amount = n + ".00"
   					}
   			 */
   		//	amount = minus + amount;
   			//return amount;
   		//	alert(amount);  */
   			$("#amount").val(t1);
   		}); 
   			
   			
            $('#table_project_req tbody').on( 'click', 'tr', function () {
            	
            	var select2projectcode = $("#project_code").select2(); 
            	
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	            select2projectcode.val("").trigger("change");
    	         //   $("#costCode").val("");
    	            $("#costCodeHD").val("");
    	            $("#costName").val("");
    	            $("#amount").val("");
    	           
    	        }
    	        else {
    	        	var table = $('#table_project_req').dataTable();
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	            var $index = $(this).index();
    	            var forsplit = $(".tdprojectCode").eq($index).text().split(" - ");
    	           	select2projectcode.val(forsplit[0]).trigger("change");
    	           	
    	        //    $("#costCode").val($(".tdcostCode").eq($index).text());
    	            $("#costCodeHD").val($(".tdcostCode").eq($index).text());
    	            $("#costName").val($(".tdcostName").eq($index).text()); 
    	            var amt = $(".tdamount").eq($index).text();
    	            amt = amt.replace(/,/g, "");
    	            amt = parseFloat(amt).toLocaleString("en-US");
    	            $("#amount").val(amt);
    	            
    	        }
    	        
    	        
    	    });    
            
    	</script>
	</body>
</html>
