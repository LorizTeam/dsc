<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	boolean chkAuthenLockProject = false;

	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "007";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}
		
		chkAuthenLockProject = capDB.getCheckAuthenLockProject(username);
	} 
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>ร่างรายละเอียดโครงการ</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet"> 
	 	<link href="css/select2.css" rel="stylesheet">
	 	<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		<link href="css/sweetalert.css" rel="stylesheet" />
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/docs.js"></script>
        <script src="js/jquery.dataTables.min.js"></script>
  		<script src="js/select2.js"></script>
  		<script src="js/angular.min.js"></script>
  		<script src="js/sweetalert.min.js"></script>
	</head>

	<body>
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">ร่างรายละเอียดโครงการ</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form action="projecthd.action" method="post">
         <div class="grid">
		  	<div class="row cells12">
		        <div class="cell colspan5"> 
		        	รหัส-ชื่อ โครงการ
			        <div class="input-control text full-size">
					    <select id="project_code" name="project_code" required>
					    	<option value="">-- โปรดเลือก --</option>
					    	<%
					    	ProjectMasterDB projM = new ProjectMasterDB();
					    	Iterator projectmasterIterate = projM.GetProjectMasterList("", "").iterator();
					    	while(projectmasterIterate.hasNext()){
					    		ProjectMasterForm projectmasterForm = (ProjectMasterForm) projectmasterIterate.next();
					    	%>
					    	<option value="<%=projectmasterForm.getProjectCode()%>"><%=projectmasterForm.getProjectCode()%> - <%=projectmasterForm.getProjectName()%></option>
					    	
					    	<%
					    	}
					    	%>
					    </select>
					</div>
				</div>
		        <div class="cell colspan1"> 
		        	เป้าหมาย
			        <div class="input-control text full-size" dir="rtl"> 
					    <s:textfield type="text" name="projectmodel.target" id="target" onblur="CommaFormatted();" required=""/>
					</div>
				</div>
				<div class="cell colspan1"> 
		        	เปอร์เซนต์
			        <div class="input-control text full-size" dir="rtl">
			        
					    <s:textfield type="number" name="projectmodel.percen" id="percen" maxlength="2" required=""/>
					</div>
				</div>
		        <div class="cell colspan1"> 
		        	ปี
			        <div class="input-control text full-size">
					    <s:textfield type="text" name="projectmodel.year" id="year" maxLength="4" required=""/>
					</div>
				</div>
				<div class="cell colspan3"><br>
			        	<button class="button success" type="submit" name="submit">จัดทำงบประมาณ</button> 
			        	<button class="button primary" type="submit"  name="update">แก้ไขงบประมาณ</button>
			    </div>
		    </div>
		     
		 </div>
		 </form>  
		</div>  
		 
        <div class="example" data-text="รายการ" ng-app="" ng-init="">
            <table id="table_project" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>
                	<%if(chkAuthenLockProject==true){ %> 
                	<th>ล็อคโครงการ</th>
                	<%} %>
                    <th>รหัส-โครงการ</th>
                    <th>ชื่อ-โครงการ</th>
                    <th>ประจำปี</th>
                    <th>เป้าหมาย-โครงการ</th>  
                    <th>เปอร์เซนต์-โครงการ</th>  
                    <th></th>  
                </tr>
                </thead> 
                  
                <tbody>
                <%
                ProjectData pjdata = new ProjectData();
                Iterator pjIterate = pjdata.GetProjectHDList().iterator();
                while(pjIterate.hasNext()){
                	ProjectModel pjmodel = (ProjectModel) pjIterate.next();
               	%>
               	<%if(chkAuthenLockProject==true){ %> 
               	<tr> 
               		<%if(pjmodel.getFreeze().equals("Y")){%>
               			<td align="center">
               			<a id="print" style="padding-top: 3px;" href="javascript:freeze('Y','<%=pjmodel.getProject_code()%>','<%=pjmodel.getYear() %>');" class="button rounded"><span class="mif-lock mif-lg" style="color: darkGreen;"></span></a>
               			</td>
               		<%}else{ %>
               			<td align="center">
               			<a id="print" style="padding-top: 3px;" href="javascript:freeze('N','<%=pjmodel.getProject_code()%>','<%=pjmodel.getYear() %>');" class="button rounded"><span class="mif-unlock mif-lg" style="color: red;"></span></a>
               			</td>
               		<%} %> 
               		<%} %>
                    <td class="tdproject_code" align="center"><%=pjmodel.getProject_code()%></td>
                    <td align="left"><%=pjmodel.getProject_name() %></td>
                    <td class="tdyear" align="center"><%=pjmodel.getYear() %></td>
                    <td class="tdtarget" align="right" width="8%">{{<%=pjmodel.getTarget() %> | currency:"฿"}}</td> 
                    <td class="tdpercen" align="right" width="8%"><%=pjmodel.getPercen()+" %" %></td>
                    <td class="align-center" ><a href="projectdt.jsp?projectcode=<%=pjmodel.getProject_code()%>&year=<%=pjmodel.getYear()%>"  class="toolbar-button"><span class="mif-pencil"></span></a></td>  
                </tr>               	
               	<%
                }
                %>
                
                </tbody>
            </table>
        </div> 
        
        <!-- End of example table -->  
        
        
   	<script>
   	 function freeze(status_freeze, projectcode,year){
     	  
   		if(status_freeze=='N'){
 		swal({  title: "ยืนยันการล็อคโครงการ ?",   
 				text: "หากคุณต้องการล็อคโครงการให้กดปุ่มยืนยัน !",   
 				type: "warning",   
 				showCancelButton: true,   
 				confirmButtonColor: "#DD6B55",   
 				confirmButtonText: "ยืนยัน, ฉันต้องการล็อคโครงการ !",   
 				cancelButtonText: "ไม่, ฉันไม่ต้องการล็อคโครงการ !",   
 				closeOnConfirm: false,   
 				closeOnCancel: false,
 				showLoaderOnConfirm: true
 			}, 
 			
 		function (isConfirm){
 			 
 		  	if (isConfirm) {
 			setTimeout(function(){
 				var load = window.open("/pcpnru/projectHdFreeze.action?freeze=Y&projectcode="+projectcode+"&year="+year
 						,'_self');
 			swal("ล็อคโครงการสำเร็จแล้ว!", "สามารถปลดล็อคโครงการได้ หากมีข้อผิดพลาด !", "success");
 			} 
 			 , 1000);
  
 			}else {    
 			 swal("ยกเลิกการล็อคโครงการ", "คุณสามารถล็อคโครงการได้อีกครั้งหลังจากปิดหน้าต่างนี้ !", "error");   
 			}
 		});
 		  
 		}else{
 			
 			swal({  title: "ยืนยันการปลดล็อคโครงการ ?",   
 				text: "หากคุณต้องการปลดล็อคโครงการให้กดปุ่มยืนยัน !",   
 				type: "warning",   
 				showCancelButton: true,   
 				confirmButtonColor: "#DD6B55",   
 				confirmButtonText: "ยืนยัน, ฉันต้องการปลดล็อคโครงการ !",   
 				cancelButtonText: "ไม่, ฉันไม่ต้องการปลดล็อคโครงการ !",   
 				closeOnConfirm: false,   
 				closeOnCancel: false,
 				showLoaderOnConfirm: true
 			}, 
 			
 		function (isConfirm){
 			 
 		  	if (isConfirm) {
 			setTimeout(function(){
 				var load = window.open("/pcpnru/projectHdFreeze.action?freeze=N&projectcode="+projectcode+"&year="+year
 						,'_self');
 			swal("ปลดล็อคโครงการสำเร็จแล้ว!", "สามารถล็อคโครงการได้อีกครั้ง !", "success");
 			} 
 			 , 1000);
  
 			}else {    
 			 swal("ยกเลิกการปลดล็อคโครงการ", "คุณสามารถปลดล็อคโครงการได้อีกครั้งหลังจากปิดหน้าต่างนี้ !", "error");   
 			}
 		});
 			
 		}
   		
 	}
 </script>
  <script>
	   		$('#percen').blur(function () {   
	   		var value = $('#percen').val();
	   		$('#percen').val(value.toString().substr(0,2));
	   		});
   		function CommaFormatted() {
   			var target = $("#target").val(); 
   			var t1 = "";
   			if(target == "NaN"){
   				t1 = "0";
   			}else if(target == ""){
   				t1 = "0";
   			}
   			else{
   				target = target.replace(/,/g,"");
   		    var t1 = parseFloat(target).toLocaleString("en-US");
   			} 
   			$("#target").val(t1);
   		}
	   		$(function(){
		       var selectproject_code =  $("#project_code").select2();
		        
		        var target = "";
		        var table = $('#table_project').DataTable( { 
	              	scrollY: '50.5vh', 
	              	scrollX: true,
	              	scrollCollapse: true,
	                ordering: false,
	                "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
	            } );
		        $('#table_project tbody').on( 'click', 'tr', function () { 
	    	        if ( $(this).hasClass('selected') ) {
	    	            $(this).removeClass('selected');
	    	            $("#target").val("");
	    	            $("#percen").val("");
	    	            $("#year").val("");
	    	            selectproject_code.val("").trigger("change");
	    	        }else{
	    	            table.$('tr.selected').removeClass('selected');
	    	            $(this).addClass('selected');
	    	            var $index = $(this).index();
	    	            
	    	            $("#year").val($(".tdyear").eq($index).text());
	    	            
	    	            var target = $(".tdtarget").eq($index).text();
	    	            target = target.replace(/฿/g,"").replace(/,/g,"");
	    	            target = parseFloat(target).toLocaleString("en-US");
	    	            $("#target").val(target); 
	    	            
	    	            var percen = $(".tdpercen").eq($index).text();
	    	            percen = percen.replace(/ %/g,"");
	    	            $("#percen").val(percen); 
	    	            
	    	            selectproject_code.val($(".tdproject_code").eq($index).text()).trigger("change");
	    	        }
		    	});
	   		});
    	</script>
	</body>
</html>
