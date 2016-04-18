<%@page import="com.mysql.jdbc.IterateBlock"%>
<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.masterModel.*" %>
<%@ page import="pcpnru.masterData.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	String username = "", project_code = "";
	
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "014";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}else{
			project_code = capDB.getProjectCode(username);
		}
	} 
%>
<!DOCTYPE html>
<html lang="en" >
	<head>
		<title>อนุมัติ การโยกงบกลาง</title>
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
	    <script src="js/select2.js"></script>
        <script src="js/jquery.dataTables.min.js"></script> 
	</head>

	<body >
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">อนุมัติ การโยกงบกลาง</h3>
		 <form id="rbaAction" action="centralBudgetApprove.action" method="post">
		 <div class="example" data-text="รายละเอียด"> 
	         <div class="grid">
	         	<div class="row cells12">  
					<div class="cell colspan3"> 
			        	สถานะ
				        <div class="input-control text full-size">
				        	<% 	String ap_status = "";
				        		if(request.getAttribute("ap_status") != null) ap_status = (String) request.getAttribute("ap_status"); %>
						    <select id="project_code" name="centralBudgetForm.approve_status" required="required">
						    	<option value="">โปรดเลือก</option>
							   <option <% if(ap_status.equals("AP")) { %> selected <% } %> value="AP">อนุมัติ</option>
							   <option <% if(ap_status.equals("WA")) { %> selected <% } %> value="WA">รอการอนุมัติ</option>
							   <option <% if(ap_status.equals("CC")) { %> selected <% } %> value="CC">ยกเลิกรายการ</option>
					   		</select>
					   		<s:hidden id="year" name="centralBudgetForm.year" /> 
						</div>
					</div>  
					<div class="cell colspan3" align="center"><br>
						<button class="button success" type="submit" name="save" >บันทึกการโยกงบกลาง</button>
						 
					</div>   
			    </div>
			 </div>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_authen" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr>  
                	<th><label class="input-control small-check checkbox"> 
                		<input type="checkbox" id="checkAll" data-show="indeterminate" />
                		<span class="check"></span> 
                        </label> เลือกทั้งหมด</th>
                	<th>เลขที่เอกสาร</th>
                	<th>โครงการ</th> 
                    <th>ค่าใช้จ่าย</th> 
                    <th>วันที่</th> 
                    <th> </th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List CentralBudgetApproveList = (List) request.getAttribute("CentralBudgetApproveList");
                 
        		int x = 1, y=0;
        		if(CentralBudgetApproveList != null){
        			
        			Iterator amIterate = CentralBudgetApproveList.iterator();
        			while(amIterate.hasNext()){
        				CentralBudgetForm anInfo = (CentralBudgetForm) amIterate.next();  
        		%>
        			<tr>
	        			<td class="tdhidden" align="center"><div  class="input-control">
	        				 
	        				<% if(anInfo.getApprove_status().equals("CC")){%>
	        						<label class="input-control small-check checkbox"> 
			                			<input class="archk" type="checkbox" name="archk" value="<%=anInfo.getDocno()%>-<%=anInfo.getYear()%>" data-show="indeterminate" disabled />
			                		<span class="check"></span> 
			                        </label>
		        					<input type="hidden" name="approveStatus" value="CC" /><input type="text" value=" ยกเลิกรายการ" size="8" readonly="readonly" />
	        				<% }else{ %>
	        						<label class="input-control small-check checkbox"> 
			                			<input class="archk" type="checkbox" name="archk" value="<%=anInfo.getDocno()%>-<%=anInfo.getYear()%>" data-show="indeterminate" />
			                		<span class="check"></span> 
			                        </label>
						    		<select name="approveStatus" > 
							        	<option <% if(anInfo.getApprove_status().equals("AP")){%> selected <%} %> value="AP">อนุมัติ</option>
							        	<option <% if(anInfo.getApprove_status().equals("WA")){%> selected <%} %> value="WA">รอการอนุมัติ</option>
							        	<option value="CC">ยกเลิกรายการ</option> 
						    	 	</select> 
							    <%
							    }	 
							  %>  
						    <input type="hidden" name="arDocno" value="<%=anInfo.getDocno()%>" /> 
						</div></td>
	        			<td class="tdant" align="left"><%=anInfo.getDocno()%></td>  
	        			<td class="tdanpj" align="left"><%=anInfo.getProject_name()%></td>
	                    <td class="tdantn" align="left"><%=anInfo.getGcostname()%></td>  
	                    <td class="tdantn" align="left"><%=anInfo.getDocdate()%></td>  
	                    <td align="center"><button class="button mini-button" type="button" onclick="javascript:getRockingBudgetApprove('<%=anInfo.getDocno()%>','<%=anInfo.getProject_code()%>',
	                    '<%=anInfo.getYear()%>','<%=anInfo.getGcostcode()%>');"> <span class="mif-search"></span></button></td>
                	</tr>
        		<%		
        		x++;y++;
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
         </form>
         
   		<script>
   		function getRockingBudgetApprove(tdocno, tprojectcode, tyear, tgcostcode) {
			var load = window.open('/pcpnru/windowCentralBudgetApprove.action?docno='+tdocno+'&project_code='+tprojectcode+'&year='+tyear+'&gcostcode='+tgcostcode+' ','',
			             'scrollbars=yes,menubar=no,height=600,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
		}
   		
        $(function(){
        	$("#gcostcode").change(function () {
        		
        		$("#rbaAction").submit();
        	});
			$("#project_code").change(function () {
				$("#gcostcode").val("");
        		$("#rbaAction").submit();
        	});
        	
        	$("#project_code").select2(); 
			$("#gcostcode").select2();
        	
			$("#checkAll").change(function () {
       		    $("input:checkbox").prop('checked', $(this).prop("checked"));
       		});
			
        	var table = $('#table_authen').DataTable( { 
              	scrollY: '50vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[14, 25, 50, 100, -1], [14, 25, 50, 100, "All"]] 
            } );
        	
        	$('#table_authen tbody').on( 'click', 'tr', function () { 
    	        //if ( $(this).hasClass('selected') ) {
    	        	 
    	        	var $index = $(this).index();
    	        	var data_chkrow = $(".archk").eq($index).val().substr(0,10);
    	        	$(".archk").eq($index).val(data_chkrow+"-"+$index);
    	     
    	    });
        	
        });
    	</script>
	</body>
</html>
