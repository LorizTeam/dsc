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
		String page_code = "018";
		
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
		<title>จัดการ การใช้งานในแต่ละส่วน</title>
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
		 <h3 class="align-center">จัดการ การใช้งานในแต่ละส่วน</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form action="pageMaster.action" method="post">
	         <div class="grid">
	         	<div class="row cells12">  
					<div class="cell colspan2"> 
			        	รหัส  การใช้งานในแต่ละส่วน
				        <div class="input-control text full-size">
						    <s:textfield name="pageMasterModel.page_code" id="pagecode" readonly="true"/> 
						</div>
					</div> 
					<div class="cell colspan3"> 
			        	กลุ่ม
			        	<div class="input-control text full-size">
			        		<select id="pagegroupcode" name="pageMasterModel.pagegroup_code" required="">
			        		<option value="" >กรุณาเลือก</option>
					        <%
						        List getListGroupPage = null;
					        	PageGroupMasterDB pgm = new PageGroupMasterDB();
				                getListGroupPage = pgm.getListGroupPage("");
				        		if (getListGroupPage != null) {
					        		for (Iterator pmIterate = getListGroupPage.iterator(); pmIterate.hasNext();) {
					        			PageGroupMasterModel pgInfo = (PageGroupMasterModel) pmIterate.next(); 
			      				%>  
				      			<option value="<%=pgInfo.getPagegroup_code()%>" >
				       			 	<%=pgInfo.getPagegroup_code()%> - <%=pgInfo.getPagegroup_name()%>
				       			</option>
								<%		} 
									}
								%>
								</select>
						</div>
					</div>
			        <div class="cell colspan4"> 
			        	ชื่อ  การใช้งานในแต่ละส่วน
				        <div class="input-control text full-size">
						    <s:textfield name="pageMasterModel.page_name" id="pagename" required=""/>
						</div>
					</div> 
					<div class="cell align-left colspan3"><br>
						  <button class="button success" name="add">สร้าง</button> 
						  <button class="button primary" name="update">แก้ไข</button> 
						  <button class="button danger" name="delete">ลบ</button> 
				</div>
	         	 
			    </div>
			 </div> 
		 </form>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_page" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th>ลำดับ</th>
                	<th>รหัส - กลุ่ม</th>
                    <th>ชื่อ - กลุ่ม</th>
                	<th>รหัส - การใช้งานในแต่ละส่วน</th>
                    <th>ชื่อ - การใช้งานในแต่ละส่วน</th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List getListPage = null;
                PageMasterDB pm = new PageMasterDB();
                getListPage = pm.getListPage("","");
        		int x = 1;
        		if(getListPage != null){
        			
        			Iterator pmIterate = getListPage.iterator();
        			while(pmIterate.hasNext()){
        				PageMasterModel pgInfo = (PageMasterModel) pmIterate.next();  
        		%>
        			<tr>
        			<td class="tdhidden" align="center"><%=x%></td>
        			<td class="tdpagegroupcode" align="left"><%=pgInfo.getPagegroup_code()%></td>  
                    <td class="tdpagegroupname" align="left"><%=pgInfo.getPagegroup_name()%></td>
        			<td class="tdpagecode" align="left"><%=pgInfo.getPage_code()%></td>  
                    <td class="tdpagename" align="left"><%=pgInfo.getPage_name()%></td> 
                	</tr>
        		<%		
        		x++;
        			}
        			
        		}else{
        		%>
        			<tr>  
                    <td colspan="3" align="center">ไม่พบข้อมูล</td>   
                	</tr>
        		<%
        		}
                %>
                </tbody>
            </table>
        </div> <!-- End of example table -->  
         
   		<script>
        $(function(){
         
        	var table = $('#table_page').DataTable( { 
              	scrollY: '50vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[14, 25, 50, 100, -1], [14, 25, 50, 100, "All"]] 
            } );
        	
            $('#table_page tbody').on( 'click', 'tr', function () { 
    	        if ( $(this).hasClass('selected') ) {
    	            $(this).removeClass('selected');
    	            $("#pagegroupcode").val("");
    	            $("#pagecode").val("");
    	            $("#pagename").val(""); 
    	        }
    	        else {
    	            table.$('tr.selected').removeClass('selected');
    	            $(this).addClass('selected');
    	            var $index = $(this).index();
    	            
    	            $("#pagegroupcode").val($(".tdpagegroupcode").eq($index).text()); 
    	            $("#pagecode").val($(".tdpagecode").eq($index).text()); 
    	            $("#pagename").val($(".tdpagename").eq($index).text()); 
    	        }
    	    });
        });
    	</script>
	</body>
</html>
