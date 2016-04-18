<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "002";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
	} 
%>
<%
	UnitMasterDB unitM = new UnitMasterDB();
	List<UnitMasterForm> unitMasterList = unitM.Get_UnitList("","");
%> 
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>สร้าง หน่วยนับ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
	 	<link href="css/sweetalert.css" rel="stylesheet" />

	</head>

	<body>
		<s:set name="fromwindow" value="unitMaster.fromwindow"/>
		
		<s:if test="%{#fromwindow=='true'}">
			<div><%@include file="window-topmenu.jsp" %></div>
		</s:if>
		<s:else>
			<div><%@include file="topmenu.jsp" %></div>
		</s:else>
		 <h3 class="align-center">หน่วยนับ</h3>
		 <form action="unitMaster.action" method="post">
		 <div class="grid" >
		 		<div class="example" data-text="Add">
			        <div class="row cells12 ">
				 		<div class="cell colspan1"> </div>
				 		<div class="cell colspan4">
				 			รหัสหน่วยนับ
				 			<div class="input-control text"> 
				 				<s:textfield name="unitMaster.unit_id" id="unit_id" readonly="true"/>
				 			</div>
				 		</div>
				 		<div class="cell colspan4"> 
				 			ชื่อหน่วยนับ
				 			<div class="input-control text">
				 				<s:textfield name="unitMaster.unit_name" id="unit_name" required=""/>
				 			</div>
				 			
				 			<button type="submit" class="button success" name="add" id="add"><span class="mif-plus mif-lg fg-white"></span></button>
				 			<button type="submit" class="button primary" name="update" id="update"><span class="mif-checkmark mif-lg fg-white"></span></button>
				 		</div>
				 		
				 	</div>
			    </div>  
		
	        <div class="example" data-text="รายการ" >
	            <table id="table_unit" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
	                <thead>
	                <tr> 
	                	<th>ลบข้อมูล <input type="checkbox" id="checkall"></th>
	                	<th width="10%">รหัสหน่วยนับ</th>
	                    <th width="90%">หน่วยนับ</th>  
	                </tr>
	                </thead> 
	                  
	                <tbody>
	                <%	if (unitMasterList != null) {
							int x = 1;
							for (UnitMasterForm unitjMaster:unitMasterList) {
					%>
	                <tr> 
	                	<td><input type="checkbox" name="delunit" id="delunit"  value="<%=unitjMaster.getUnit_id() %>"> </td>
						<s:if test="%{#fromwindow=='true'}">
	                    	<td align="center" width="10%" class="unit_id"> <a href="#" onclick="getUnit('<%=unitjMaster.getUnit_id()%>','<%=unitjMaster.getUnit_name()%>')"><%=unitjMaster.getUnit_id()%></a></td>
	                    </s:if>
						<s:else>
							<td align="center" width="10%" class="unit_id"><%=unitjMaster.getUnit_id()%></td>
						</s:else>
	                    <td class="unit_name" align="left" width="90%"><%=unitjMaster.getUnit_name()%></td> 
	                </tr>	  
	                <% 	x++;
	                
						} %>
	                
	                <%} else { %> 
	                	<tr> 
	                    <td colspan="3">ไม่พบข้อมูล</td> 
	                	</tr> 
	                <%	} %>
	                </tbody>
	            </table> 
	            <div class="row cells12 ">
			 		<div class="cell colspan4"> </div>
			 		<div class="cell colspan4"> 
			 			<button type="submit" class="button danger" name="delete" id="delete"><span class="mif-minus mif-lg fg-white"></span></button>
			 		</div>
			 		
			 	</div> 
	        </div> <!-- End of example table -->
        	
        </div>
        <s:hidden name="unitMaster.alertmsg" id="alertmsg"/>
     	</form>
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/docs.js"></script>
	    <script src="js/prettify/run_prettify.js"></script>
	    <script src="js/ga.js"></script> 
 		<script src="js/jquery.dataTables.min.js"></script>
 		<script src="js/sweetalert.min.js"></script>
   		<script>
   		function getUnit(unit_id,unit_name){
   			window.opener.document.getElementById("unit_id").value= unit_id;
			window.opener.document.getElementById("unit_name").value= unit_name;
			window.close();
   		}
        $(document).ready(function() {
       	if($("#alertmsg").val() != ""){
       		swal("Error",$("#alertmsg").val() , "error");
       	}
    	var table = $('#table_unit').DataTable( {
          	scrollY:  '36.5vh',
          	scrollX: true,
          	scrollCollapse: true,
          	ordering: false,
          	"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
      	} ); 

		$("#checkall").click(function(){
			if($(this).prop("checked")){
				$('[name="delunit"]').prop("checked",true);
			}else{
				$('[name="delunit"]').prop("checked",false);
			}
		});
		$("#delete").click(function(){
			$("#unit_name").val("-");
		});
    	$('#table_unit tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	            $("#unit_id").val("");
	            $("#unit_name").val("");
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            var $index = $(this).index();
	            $("#unit_id").val($(".unit_id").eq($index).text());
	            $("#unit_name").val($(".unit_name").eq($index).text());
	        }
	    });
	} );
    	</script>
	</body>
</html>
