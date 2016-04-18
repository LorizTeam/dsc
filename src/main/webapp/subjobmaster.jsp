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
	SubjobMasterDB subjM = new SubjobMasterDB();
	List subjobMasterList = subjM.GetSubjobMasterList("","");
%> 
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>สร้าง กิจกรรม</title>
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
		 <h3 class="align-center">สร้างชื่อกิจกรรม</h3>
		 <form action="subjobMaster" method="post">
		 <div class="example" data-text="รายละเอียด">
         <div class="grid">
		  	<div class="row cells12">
		  		<div class="cell colspan3"> 
		        	รหัส-ชื่อ กิจกรรม
			        <div class="input-control text full-size"> 
					    <s:textfield id="subjobcode" name="subjobMaster.subjobCode" readonly="true"  />
					    <s:hidden id="subjobcodehd" name="subjobMaster.subjobCodeHD" />
					</div> 
				</div>
		        <div class="cell colspan5"> 
		        	ชื่อกิจกรรม
			        <div class="input-control text full-size"> 
					    <s:textfield id="subjobname" name="subjobMaster.subjobName" required="" />
					</div>
				</div> 
				<div class="cell colspan4"><br>
					  <button class="button success" type="submit" name="add">สร้างชื่อกิจกรรม</button> 
					  <button class="button primary" type="submit" name="update">แก้ไขชื่อกิจกรรม</button> 
					  <button class="button danger" type="submit" name="delete">ลบชื่อกิจกรรม</button>
				</div> 
		    </div>
		 </div>  
		</div>  
		
        <div class="example" data-text="รายการ">
            <table id="table_subjob" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr> 
                	<th>เลขที่</th>
                    <th>รหัส-กิจกรรม</th>
                    <th>ชื่อ-กิจกรรม</th> 
                    <th>วันที่-กิจกรรม</th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%	if (subjobMasterList != null) {
						int x = 0;
						for (Iterator iter = subjobMasterList.iterator(); iter.hasNext();) {
						x++; 
						SubjobMasterForm subjMaster = (SubjobMasterForm) iter.next();
				%>
                <tr> 
                    <td align="center"><%=x%></td>
                    <td class="tdsubjobcode" align="center"><%=subjMaster.getSubjobCode()%></td>
                    <td class="tdsubjobname" align="left"><%=subjMaster.getSubjobName()%></td>  
                    <td align="center"><%=subjMaster.getDateTime()%></td>
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
     	
   		<script>
        $(document).ready(function() {
        	
    	var table = $('#table_subjob').DataTable( {
          	scrollY:  '35vh',
          	scrollX: true,
          	scrollCollapse: true,
          	ordering: false,
          	"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
      	} ); 
        	
		$('#table_subjob tbody').on( 'click', 'tr', function () { 
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	            $("#subjobcode").val("");
	            $("#subjobcodehd").val("");
	            $("#subjobname").val("");
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            var $index = $(this).index();
	            $("#subjobcode").val($(".tdsubjobcode").eq($index).text());
	            $("#subjobcodehd").val($(".tdsubjobcode").eq($index).text());
	            $("#subjobname").val($(".tdsubjobname").eq($index).text());
	        }
	    });
	} );
    	</script>
	</body>
</html>
