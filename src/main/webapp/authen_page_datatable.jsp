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
		String page_code = "016";
		
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
		<title>กำหนด สิทธิ์การใช้งาน</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
	</head>

	<body>
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">กำหนด สิทธิ์การใช้งาน</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form action="authenPageMaster.action" method="post">
	         <div class="grid">
	         	<div class="row cells12">  
					<div class="cell colspan3"> 
			        	 ประเภทสิทธิ์ เข้าใช้งานระบบ
				        <div class="input-control text full-size">
						    <select id="authen_type" name="authen_type" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
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
			        <div class="cell colspan3"> 
			        	ชื่อ การใช้งานในแต่ละส่วน
				        <div class="input-control text full-size">
						    <select id="page_code" name="page_code" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
							   <option value="" >กรุณาเลือกการใช้งานในแต่ละส่วน</option>
							   <%
							   	List Page = null;
							   	PageMasterDB pageMasterDB = new PageMasterDB();
							   	Page = pageMasterDB.getListPage("",""); 
				        		if (Authen != null) {
					        		for (Iterator iterP = Page.iterator(); iterP.hasNext();) {
					        			PageMasterModel pModel = (PageMasterModel) iterP.next();
			      				%>  
				      			<option value="<%=pModel.getPage_code()%>" >
				       			 	<%=pModel.getPage_code()%> - <%=pModel.getPage_name()%>
				       			</option>
								<%		} 
									}
								%>
					   		</select>
						</div>
					</div> 
					<div class="cell align-left colspan6"><br>
						  <button class="button success" name="add">สร้างชื่อสิทธิ์การใช้งาน</button> 
						  <button class="button primary" name="update">แก้ไขสิทธิ์การใช้งาน</button> 
						  <button class="button danger" name="delete">ลบชื่อสิทธิ์การใช้งาน</button> 
				</div>
	         	 
			    </div>
			 </div> 
		 </form>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_authenpage" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>ลำดับ</th>
                	<th>รหัส -สิทธิ์การใช้งาน</th>
                    <th>ชื่อ -สิทธิ์การใช้งาน</th> 
                    <th>รหัส -การใช้งานในแต่ละส่วน</th>
                    <th>ชื่อ -การใช้งานในแต่ละส่วน</th>
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List getListAuthenPage = null;
                AuthenPageMasterDB amp = new AuthenPageMasterDB();
                getListAuthenPage = amp.getListAuthenPage("", "");
        		int x = 1;
        		if(getListAuthenPage != null){
        			
        			Iterator ampIterate = getListAuthenPage.iterator();
        			while(ampIterate.hasNext()){
        				AuthenPageMasterModel ampInfo = (AuthenPageMasterModel) ampIterate.next();  
        		%>
        			<tr>
        			<td class="tdhidden" align="center"><%=x%></td>
        			<td class="tdauthen" align="left"><%=ampInfo.getAuthen_type()%></td>  
        			<td align="left"><%=ampInfo.getAuthen_name()%></td>
                    <td class="tdpage" align="left"><%=ampInfo.getPage_code()%></td> 
                    <td align="left"><%=ampInfo.getPage_name()%></td>
                	</tr>
        		<%		
        		x++;
        			}
        			
        		}else{
        		%>
        			<tr>  
                    <td colspan="5" align="center">ไม่พบข้อมูล</td>   
                	</tr>
        		<%
        		}
                %>
                </tbody>
            </table>
        </div> <!-- End of example table -->  
         
   		<script>
        $(function(){
         
        	var table = $('#table_authenpage').DataTable( { 
              	scrollY: '50vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[14, 25, 50, 100, -1], [14, 25, 50, 100, "All"]] 
            } );
        	
            $('#table_authenpage tbody').on( 'click', 'tr', function () { 
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	            
    	            $("#authen_type").val("");
    	            $("#page_code").val(""); 
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	            var $index = $(this).index();
    	              
    	            $("#authen_type").val($(".tdauthen").eq($index).text());
    	            $("#page_code").val($(".tdpage").eq($index).text()); 
    	        }
    	    });
        });
    	</script>
	</body>
</html>
