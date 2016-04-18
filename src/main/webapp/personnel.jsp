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
		String page_code = "015";
		
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
		<title>จัดการ บุคคลกร</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/select2.css" rel="stylesheet">
		<link href="css/bootstrap-datepicker3.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
        <script src="js/jquery.dataTables.min.js"></script>
  		<script src="js/select2.js"></script>
  		<script src="js/bootstrap-datepicker-th.js"></script>
	</head>

	<body>
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">จัดการ  บุคคลกร</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form action="personnelMaster.action" method="post">
	         <div class="grid">
	         	<div class="row cells12">
	         		<div class="cell colspan1"> </div>
	         		<div class="cell colspan5"> 
	         		 โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="project_code" required="">
							   <option value="" >กรุณาเลือกโครงการ</option>
							   <%
							   	PersonnelMasterDB personnelModel = new PersonnelMasterDB();
							   	List projectMasterList = personnelModel.getListProject_User_Personnel("");
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			PersonnelMasterModel pjModel = (PersonnelMasterModel) iterPj.next();
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
					<div class="cell colspan1"> 
			        	รหัส บุคลากร
				        <div class="input-control text full-size">
						    <s:textfield name="personnelMasterModel.personnel_id" id="personnelid" required=""/> 
						</div>
					</div> 
			        <div class="cell colspan2"> 
			        	ชื่อ บุคลากร
				        <div class="input-control text full-size">
						    <s:textfield name="personnelMasterModel.personnel_name" id="personnelname" required=""/>
						</div>
					</div>
					<div class="cell colspan2"> 
			        	นามสกุล บุคลากร
				        <div class="input-control text full-size">
						    <s:textfield name="personnelMasterModel.personnel_lastname" id="personnellastname" required=""/>
						</div>
					</div>
	         	</div>
			  	<div class="row cells12">
			  		<div class="cell colspan1"> </div> 
			        <div class="cell colspan2"> 
			        	ตำแหน่ง
			        	<div class="input-control text full-size">
					        <select id="position" name="position" required="">
							    	<option value="">โปรดเลือก</option>
							    	<option value="01">ผู้บริหาร</option> 
							    	<option value="02">บัญชี</option>
							    	<option value="03">พนักงานทั่วไป</option>
							</select>
						</div>
					</div>
					<div class="cell colspan4"> 
			        	 ประเภทสิทธิ์ เข้าใช้งานระบบ
				        <div class="input-control text full-size">
						    <select id="authen_type" name="authen_type" required="">
							   <option value="" >กรุณาเลือกประเภทสิทธิ์เข้าใช้งานระบบ</option>
							   <%
							   	List Authen = null;
							   	AuthenMasterDB authenMasterDB = new AuthenMasterDB();
							   	Authen = authenMasterDB.getListAuthen(""); 
				        		if (Authen != null) {
					        		for (Iterator iterA = Authen.iterator(); iterA.hasNext();) {
					        			AuthenMasterModel aModel = (AuthenMasterModel) iterA.next();
			      				%>  
				      			<option value="<%=aModel.getAuthen_type()%>" >
				       			 	<%=aModel.getAuthen_type()%> - <%=aModel.getAuthen_type_name()%>
				       			</option>
								<%		} 
									}
								%>
					   		</select>
						</div>
					</div> 
					<div class="cell colspan2"> 
			        	วันที่เริ่มงาน
				        <div class="input-control text full-size">
						    <s:textfield name="personnelMasterModel.dow" id="dow" required=""/>
						</div>
					</div> 
					<div class="cell colspan2"> 
			        	วันเกิด
				        <div class="input-control text full-size">
						    <s:textfield name="personnelMasterModel.dob" id="dob" required=""/>
						</div>
					</div>
			    </div>
			    <div class="row cells12"> 
			    	<div class="cell colspan1"> </div>
			  		<div class="cell colspan2"> 
			        	เบอร์โทรศัพท์
				        <div class="input-control text full-size">
						    <s:textfield name="personnelMasterModel.telephone" id="telephone" maxlength="10" required=""/>
						</div>
					</div>
			        <div class="cell colspan8"> 
			        	ที่อยู่
				        <div class="input-control text full-size">
						    <s:textfield name="personnelMasterModel.address" id="address" required=""/>
						</div>
					</div> 
			    </div>
			    <div>
			    <div class="cell align-center"><br>
						  <button class="button success" name="add">สร้างชื่อบุคคลากร</button> 
						  <button class="button primary" name="update">แก้ไขบุคคลากร</button> 
						  <button class="button danger" name="delete">ลบชื่อบุคคลากร</button> 
				</div>
			    </div>
			 </div> 
		 </form>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_personnel" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>ลำดับ</th>
                	<th>รหัส -ชื่อโครงการ</th>
                    <th>รหัส-ชื่อ บุคลากร</th>
                    <th>ตำแหน่ง</th>
                    <th>สิทธิ์การใช้งาน</th> 
                    <th>วันที่เริ่มงาน</th> 
                    <th>เบอร์โทรศัพท์</th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List grouPersonnelMasterList = null;
                PersonnelMasterDB pn = new PersonnelMasterDB();
                grouPersonnelMasterList = pn.GetPersonnelList("", "", "", "", "");
        		int x = 1;
        		if(grouPersonnelMasterList != null){
        			
        			Iterator pnIterate = grouPersonnelMasterList.iterator();
        			while(pnIterate.hasNext()){
        				PersonnelMasterModel pmInfo = (PersonnelMasterModel) pnIterate.next();  
        		%>
        			<tr>
        			<td class="tdhidden" align="center"><%=x%><input type="hidden" id="addresshd" value="<%=pmInfo.getAddress()%>">
        			<input type="hidden" id="atn" value="<%=pmInfo.getAuthen_type()%>"><input type="hidden" id="dobhd" value="<%=pmInfo.getDob()%>"></td>
        			<td class="tdprojectCode" align="left"><%=pmInfo.getProject_code()%> - <%=pmInfo.getProject_name()%></td>  
                    <td class="tdpersonnel" align="left"><%=pmInfo.getPersonnel_id()%> - <%=pmInfo.getPersonnel_name()%> <%=pmInfo.getPersonnel_lastname()%></td>
                    <td class="tdposition" align="left"><%=pmInfo.getPosition()%></td>
                    <td align="left"><%=pmInfo.getAuthen_type_name()%></td> 
                    <td class="tddow" align="left"><%=pmInfo.getDow()%></td>
                    <td class="tdtelephone" align="left"><%=pmInfo.getTelephone()%></td>  
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
        $(function(){
        	var select2projectcode = $("#project_code").select2();
        	
        	$("#dow").datepicker({
            	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
            	
            });
        	$("#dob").datepicker({
            	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
            	
            });
        	
        	var table = $('#table_personnel').DataTable( { 
              	scrollY: '28.5vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[8, 25, 50, 100, -1], [8, 25, 50, 100, "All"]] 
            } );
            $('#table_personnel tbody').on( 'click', 'tr', function () { 
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	            select2projectcode.val("").trigger("change");
    	            $("#personnelid").val("");
    	            $("#personnelname").val("");
    	            $("#personnellastname").val("");
    	            $("#authen_type").val("");
    	            $("#project_code").val("");
    	            $("#dow").val("");
    	            $("#dob").val("");
    	            $("#telephone").val("");
    	            $("#address").val(""); 
    	            $("#position").val(""); 
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	            var $index = $(this).index();
    	            
    	           	var forsplit = $(".tdprojectCode").eq($index).text().split(" - ");
    	           	select2projectcode.val(forsplit[0]).trigger("change");
    	           	 
    	           	var personnelSplit = $(".tdpersonnel").eq($index).text().split(" ");
    	          // 	alert(personnelSplit[2]);
    	           	$("#personnelid").val(personnelSplit[0]);
    	           	$("#personnelname").val(personnelSplit[2]);
    	           	$("#personnellastname").val(personnelSplit[3]);  
    	           	
    	            $("#authen_type").val($(".tdhidden > #atn").eq($index).val());
    	            $("#dow").val($(".tddow").eq($index).text());
    	            $("#dob").val($(".tdhidden > #dobhd").eq($index).val());
    	            $("#telephone").val($(".tdtelephone").eq($index).text()); 
    	            $("#position").val($(".tdposition").eq($index).text());
    	            $("#address").val($(".tdhidden > #addresshd").eq($index).val());
    	        }
    	    });
        });
    	</script>
	</body>
</html>
