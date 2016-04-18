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
		String page_code = "001";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
	} 
%>
<% 
	ProjectMasterDB projM = new ProjectMasterDB();
	List projectMasterList = projM.GetProjectMasterList("","");
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>สร้างชื่อโครงการ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
	 
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/docs.js"></script>
	    <script src="js/prettify/run_prettify.js"></script>
	    <script src="js/ga.js"></script> 
 		<script src="js/jquery.dataTables.min.js"></script>
 		
	</head>

	<body>
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">สร้างชื่อโครงการ</h3>
		 <form action="projectMaster.action" method="post">
		 <div class="example" data-text="รายละเอียด">
         <div class="grid">
		  	<div class="row cells12">
		        <div class="cell colspan3"> 
		        	รหัสโครงการ
			        <div class="input-control text full-size"> 
					    <s:textfield id="projectcode" name="projectMaster.projectCode" readonly="true" />
					    <s:hidden id="projectcodehd" name="projectMaster.projectCodeHD" />
					</div>  
				</div>
		        <div class="cell colspan5"> 
		        	ชื่อโครงการ
			        <div class="input-control text full-size"> 
					    <s:textfield id="projectname" name="projectMaster.projectName" required="" />
					</div>
				</div> 
				<div class="cell colspan4"><br>
					<button class="button success" type="submit" name="add">สร้างชื่อโครงการ</button> 
				  	<button class="button primary" type="submit" name="update">แก้ไขชื่อโครงการ</button> 
				  	<button class="button danger" type="submit" name="delete">ลบชื่อโครงการ</button> 
				</div> 
		    </div>
		 </div>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_project" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr> 
                	<th>เลขที่</th>
                    <th>รหัส-โครงการ</th>
                    <th>ชื่อ-โครงการ</th> 
                    <th>วันเวลา-โครงการ</th>
                </tr>
                </thead> 
                  
                <tbody>
                <%	if (projectMasterList != null) { 
						int x = 0;
						for (Iterator iter = projectMasterList.iterator(); iter.hasNext();) {
						x++; 
						ProjectMasterForm projMaster = (ProjectMasterForm) iter.next();
				%>
                <tr> 
                    <td align="center"><%=x%></td>
                    <td class="tdprojectcode" align="center"><%=projMaster.getProjectCode()%></td>
                    <td class="tdprojectname" align="left"><%=projMaster.getProjectName()%></td>
                    <td align="center"><%=projMaster.getDateTime()%></td>  
                </tr>	  
                <% 	} %>
                
                <%} else { %> 
                	<tr> 
                    <td colspan="3">ไม่พบข้อมูล</td> 
                	</tr> 
                <%	} %>
                </tbody>
            </table>
        </div> <!-- End of example table -->  
        </form>
 
    <script type="text/javascript">
  	$(document).ready(function() {
  		
    	var table = $('#table_project').DataTable( {
        	scrollY:        '35vh', 
        	scrollX: true,
        	scrollCollapse: true,
          	ordering: false,
          	"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
      } );  
		$('#table_project tbody').on( 'click', 'tr', function () { 
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	            $("#projectcode").val("");
	            $("#projectcodehd").val("");
	            $("#projectname").val("");
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            var $index = $(this).index();
	            $("#projectcode").val($(".tdprojectcode").eq($index).text());
	            $("#projectcodehd").val($(".tdprojectcode").eq($index).text());
	            $("#projectname").val($(".tdprojectname").eq($index).text());
	        }
	    });
	} );
  </script>
    
	</body>
</html>
