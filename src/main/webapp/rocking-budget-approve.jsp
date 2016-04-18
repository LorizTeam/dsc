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
		<title>อนุมัติ การโยกงบประมาณ</title>
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
		 <h3 class="align-center">อนุมัติ การโยกงบประมาณ</h3>
		 <form id="rbaAction" action="rockingBudgetApprove.action" method="post">
		 <div class="example" data-text="รายละเอียด"> 
	         <div class="grid">
	         	<div class="row cells12">  
					<div class="cell colspan6"> 
			        	โครงการ
				        <div class="input-control text full-size">
						    <select id="project_code" name="rockingBudgetForm.project_code" required="required">
							   <option value="">กรุณาเลือกโครงการ</option>
							   <%  
							   RockingBudgetApproveDB rbga = new RockingBudgetApproveDB();
							   List <String> listAPB = rbga.getAP();  
							   
							   List projectMasterList = (List) request.getAttribute("projectMasterList");
							   String projectcode = (String) request.getAttribute("projectcode");
				        		if (projectMasterList != null) {
				        			int b = 0;
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next(); 
						        				
						        	%>
						        			<option <%if(pjModel.getProject_code().equals(projectcode)){ %> selected <%} %> value="<%=pjModel.getProject_code()%> - <%=pjModel.getYear()%>" >
							       			  รออนุมัติ <%=listAPB.get(b)%> - <%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%> - ปี <%=pjModel.getYear() %>
							       			</option>
						        	<%
						        	b++;
			      						}
					        		
									}
								%>
					   		</select>
					   		<s:hidden id="year" name="rockingBudgetForm.year" /> 
						</div>
					</div> 
			        <div class="cell colspan6"> 
			        	ค่าใช้จ่าย
				        <div class="input-control text full-size">
				        	<% List groupcostCodeList = (List) request.getAttribute("groupcostCodeList");
			    				String gcostcode = (String) request.getAttribute("gcostcode");
			    			if (groupcostCodeList != null) {
			    				Iterator Iterate = groupcostCodeList.iterator();
			    				%>
			    				<select name="rockingBudgetForm.gcostcode" id="gcostcode">
			    					<option selected value="">-- please Select --</option>
			    				<%
			        			while(Iterate.hasNext()){
			        				GroupCostCodeMasterModel gcc1Info = (GroupCostCodeMasterModel) Iterate.next(); 
					        				
					        	%> 
					        	<option <%if(gcc1Info.getCostCode().equals(gcostcode)){ %> selected <%} %> value="<%=gcc1Info.getCostCode()%>" ><%=gcc1Info.getCostName()%></option> 
					        	<% 	
		      						} 
			    				%>
			    				</select>
							<%	}  %>
						     
						</div>
					</div>    
			    </div>
			 </div> 
			 <div class="flex-grid">
			  	<div class="row flex-just-center" >
			    	<div class="cell colspan12" align="center">
						<button class="button success" type="submit" name="save" >บันทึกการโยกงบประมาณ</button>
						 
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
                    <th>ค่าใช้จ่าย</th> 
                    <th>วันที่</th> 
                    <th> </th> 
                </tr>
                </thead> 
                  
                <tbody>
                <%
                List RockingBudgetDList = (List) request.getAttribute("RockingBudgetDList");
                 
        		int y=0;
        		if(RockingBudgetDList != null){
        			
        			Iterator amIterate = RockingBudgetDList.iterator();
        			while(amIterate.hasNext()){
        				RockingBudgetForm anInfo = (RockingBudgetForm) amIterate.next();  
        		%>
        			<tr>
	        			<td class="tdhidden" align="center"><div  class="input-control">
	        				 
	        				<% if(anInfo.getApprove_status().equals("CC")){%>
	        						<label class="input-control small-check checkbox"> 
			                			<input class="archk"  type="checkbox" name="archk" value="<%=anInfo.getDocno()%>-<%=anInfo.getGcostcode()%>" data-show="indeterminate" disabled />
			                		<span class="check"></span> 
			                        </label>
		        					<input type="hidden" name="approveStatus" value="CC" /><input type="text" value=" ยกเลิกรายการ" size="8" readonly="readonly" />
	        				<% }else{ %>
	        						<label class="input-control small-check checkbox"> 
			                			<input class="archk" type="checkbox" name="archk" id="archk" value="<%=anInfo.getDocno()%>-<%=anInfo.getGcostcode()%>" data-show="indeterminate" />
			                		<span class="check"></span> 
			                        </label> 
						    		<select name="approveStatus" id="approvestatus" >
							        	<option <% if(anInfo.getApprove_status().equals("AP")){%> selected <%} %> value="AP">อนุมัติ</option>
							        	<option  <%if (anInfo.getApprove_status().equals("WA")){%> selected <%} %> value="WA">รอการอนุมัติ</option>
							        	<option value="CC">ยกเลิกรายการ</option> 
							        </select>  
							    <%
							    }	 
							  %>  
						    <input type="hidden" name="arDocno" value="<%=anInfo.getDocno()%>" />
						    <input type="hidden" name="arGcostcode" value="<%=anInfo.getGcostcode()%>" />  
						    <input type="hidden" name="arGcostcode_rock" value="<%=anInfo.getGcostcode_rock()%>" />
						</div></td>
	        			<td class="tdant" align="left"><%=anInfo.getDocno()%></td>  
	                    <td class="tdantn" align="left"><%=anInfo.getGcostname()%></td>  
	                    <td class="tdantn" align="left"><%=anInfo.getDocdate()%></td>  
	                    <td align="center"><button class="button mini-button" type="button" onclick="javascript:getRockingBudgetApprove('<%=anInfo.getDocno()%>','<%=anInfo.getProject_code()%>',
	                    '<%=anInfo.getYear()%>','<%=anInfo.getGcostcode()%>');"> <span class="mif-search"></span></button></td>
                	</tr>
        		<%		
        		 
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
			var load = window.open('/pcpnru/windowRockingBudgetApprove.action?docno='+tdocno+'&project_code='+tprojectcode+'&year='+tyear+'&gcostcode='+tgcostcode+' ','',
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
              	scrollY: '60vh', 
              	scrollX: true,
              	scrollCollapse: true, 
                ordering: false,
                "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
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
