<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	String username = "", project_code = "";
	
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "008";
		
		CheckAuthenPageDB capDB = new CheckAuthenPageDB();
		
		chkAuthen = capDB.getCheckAuthen(username, page_code);
		
		if(chkAuthen==false){
			response.sendRedirect("no-authen.jsp");
		}else{
			project_code = capDB.getProjectCode(username);
		}
	} 
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>บันทึกรายได้</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
	
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
	 	<link href="css/select2.css" rel="stylesheet">
		<link href="css/bootstrap-datepicker3.css" rel="stylesheet"> 
	 	
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
 		<script src="js/jquery.dataTables.min.js"></script>   
		<script src="js/select2.js"></script>
  		<script src="js/bootstrap-datepicker-th.js"></script>
  </head>
  
  <script>
        function no_submit(){
            return false;
        }

        function notifyOnErrorInput(input){
            var message = input.data('validateHint');
            $.Notify({
                caption: 'Error',
                content: message,
                type: 'alert'
            });
        }
    </script>
  
  <body>
  
  	<%
  		DateUtil dateutil = new DateUtil();
	  	List projectMasterList1 = null;
		if (request.getAttribute("projectMasterList") == null) { 
			extendsprojectmaster ext = new extendsprojectmaster();
			projectMasterList1 = ext.getListProject_Join_Projecthead(project_code, "","","");
		}else{
			projectMasterList1 = (List) request.getAttribute("projectMasterList");
		}
		List costCodeMasterList1 = null;
		if (request.getAttribute("costCodeMasterList") == null) {
			CostCodeMasterDB ccM = new CostCodeMasterDB();
			costCodeMasterList1 = ccM.GetCostCodeMasterList("", "","");
		}else{
			costCodeMasterList1 = (List) request.getAttribute("costCodeMasterList");
		}
		
		System.out.println(dateutil.curDateTH());
  	%>
  
    <div><%@include file="topmenu.jsp" %></div>
	<br>
	<h3 class="align-center">บันทึกรายได้</h3>
	<form action="receive1.action" method="post" data-role="validator" data-show-required-state="false" data-hint-mode="line" data-hint-background="bg-red" data-hint-color="fg-white" data-hide-error="5000">
	<div class="example" data-text="รายการรับ">
		<div class="grid">
		  	<div class="row cells12">
		       
		    	<div class="cell colspan5 offset2" > 
		    		 โครงการ
		       		 <div class="input-control full-size"> 
		       		 <select id="project_code" name="projectCode" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
					   <option value="" >กรุณาเลือกโครงการ</option>
					   <%
					   	List projectMasterList = projectMasterList1;
		        		if (projectMasterList != null) {
			        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
			        			ProjectModel pjModel = (ProjectModel) iterPj.next();
	      				%>  
			      			<option value="<%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%> - <%=pjModel.getYear()%>" >
			       			 	<%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%> ปี <%=pjModel.getYear() %>
			       			</option>
							<%		} 
								}
							%>
					   </select>
	                      <span class="input-state-success mif-checkmark"></span>
					   </div>
		    	</div>
	           <div class="cell colspan2">  
	        		วันที่รับ
				    <div class="input-control text full-size " >
                          <input type="text" name="dateTime" id="datepicker"  value="<%=dateutil.curDateTH() %>" data-validate-func="required" data-validate-hint="กรุณาเลือกวันที่รับ">
                          <span class="input-state-success mif-checkmark"></span>
                   	</div>
	               </div>
	           </div>
	           
		    	
	        
		  	<div class="row cells12">
		       
		    	<div class="cell colspan7  offset2">
		    		รายได้<div class="input-control full-size"> 
					    <select id="gcostcode" name="gcostCode" data-validate-func="required" data-validate-hint="กรุณาเลือกประเภทค่าใช้จ่าย">
					   	<option value="">กรุณาเลือกรายการรายได้</option>
					   </select> 
	                    	<span class="input-state-success mif-checkmark"></span> 
					</div>
		    	</div>   
		    </div>
	
			<div class="row cells12">
		       <div class="cell colspan4  offset2">
		    		เล่มที่
		    		<div class="input-control full-size"> 
					    <s:textfield id="vol" name="receiveform.vol" data-validate-func="required" data-validate-hint="กรุณากรอกเล่มที่" /> 
					 	<span class="input-state-success mif-checkmark"></span> 
					</div>
		    	</div>
		    	<div class="cell colspan3">
		    		เลขที่
		    		<div class="input-control full-size">  
					    <s:textfield id="docno" name="receiveform.docNo" data-validate-func="required" data-validate-hint="กรุณากรอก เลขที่" /> 
					    <span class="input-state-success mif-checkmark"></span> 
					    
					</div>
		    	</div>  
		    </div>
	
		  	<div class="row cells12">
		       <div class="cell colspan4  offset2">
		    		ชื่อลูกค้า
		    		<div class="input-control full-size"> 
					    <s:textfield id="amountfrom" name="receiveform.amountfrom" data-validate-func="required" data-validate-hint="กรุณากรอกชื่อูกค้า" /> 
					 	<span class="input-state-success mif-checkmark"></span> 
					</div>
		    	</div>
		    	<div class="cell colspan3">
		    		สถานที่
		    		<div class="input-control full-size">  
					    <s:textfield id="local" name="receiveform.local" data-validate-func="required" data-validate-hint="กรุณาใส่สถานที่รับ" /> 
					    <span class="input-state-success mif-checkmark"></span> 
					    
					</div>
		    	</div>  
		    </div>
		
		  	<div class="row cells12"> 
		    	<div class="cell colspan2  offset2" align="center"><br> 
					  <button class="button success full-size" type="submit" name="ok">ตกลง</button>
				</div> 
		    </div>
		</div>
		
	</div> <!-- End of example --> 

	</form> 
	  
  </body>
  <script>
    $(function(){
    	
        $("#project_code").select2();
       // $("#cost_code").select2();
        
        $("#datepicker").datepicker({
        	format: "dd-mm-yyyy",autoclose:true,todayBtn: "linked",todayHighlight: true
        	
        });
        
        $( "#project_code" ).change(function() {
  		  
			var project_code = $("#project_code").val();
			var out = '';
			 
			$.ajax({  // select history
			  	 
	          type: "post",
	          url: "ajax_receive-1.jsp", //this is my servlet 
	          data: {projectCode:project_code},
	          async:false, 
	          success: function(result){
	          
	          obj = JSON.parse(result);
	          	// alert(obj)
	          	
	          	var value = obj;
	          if( Object.keys(obj).length === 0){
	        	  $("#gcostcode").html('<option value="">กรุณาเลือกรายการค่าใช้จ่าย</option>');
	          }else{
		          for(var i = 0 ; i < obj.length; i++){
						out +=  
						'<option value="'+obj[i].gcostcode+' - '+obj[i].gcostcode_name+'">'+
						   obj[i].gcostcode+' - '+obj[i].gcostcode_name+
						'</option>'; 
					}  
					$("#gcostcode").html(out);
		          }
	          }
	       });
			
		});
        
        
    });
</script>
</html>
