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
		<link href="css/select2.css" rel="stylesheet">
		
	 	<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script> 
	    <script src="js/select2.js"></script>
	</head>

	<body> 
		 <div><%@include file="topmenu.jsp" %></div>
		 <% 
		 	String authen_code = ""; 
		 	if(request.getAttribute("authen_type")!=null) authen_code = (String)request.getAttribute("authen_type"); 
		  %>
		 <h3 class="align-center">กำหนด สิทธิ์การใช้งาน</h3>
		 <div class="example" data-text="รายละเอียด">
		 <form id="ap" action="authenPageMaster" method="post">
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
				      			<option <%if(authen_code.equals(aModel.getAuthen_type())){ %>selected<%}%> value="<%=aModel.getAuthen_type()%>" >
				       			 	<%=aModel.getAuthen_type()%> - <%=aModel.getAuthen_type_name()%>
				       			</option>
								<%		} 
									}
								%>
					   		</select>
						</div>
					</div> 
			        
					<div class="cell align-left colspan3"><br>
						  <button class="button success" name="add">บันทึกสิทธิ์การใช้งาน</button>
						  <button class="button danger" name="clear">ยกเลิก</button>    
					</div> 
			    </div>
			 </div> 
			 <%  
			  if(!authen_code.equals("")){ 
			 
			    AuthenPageMasterDB apm = new AuthenPageMasterDB();
			 	int x = 0, y = 4, z = 0, a = 0, c = 0, valc = 0;
			    int countn = apm.countGroupPage("");
			    valc = countn;
			 	do{ 
				  x++;
				  z = y*x;
				  
				  if(valc>4){
					  valc = valc-4;
					  c = 3;
				  }  
				  else{ 
					  c = valc-1;
			 	  } 
			%>
			<div class="grid">
	         	<div class="row cells12">
	         	 <% 
	         		List <String> listpagegroupname =  apm.getGroupPageName(""); 
	         	    List <String> listpagegroupcode =  apm.getGroupPageCode(""); 
	         	    
	         	 	for(int b=0; b<=c;b++){ 
				 %>
				 	<div class="cell colspan3">
	         			<div class="treeview" data-role="treeview">
	         				<ul>
                                <li data-mode="checkbox" data-name="c1" class="node">
                                    <span class="node-toggle"></span>
                                    <span class="leaf"><h5><%=listpagegroupname.get(a)%></h5></span>
                                    <ul>
                                <%  
                                	List <AuthenPageMasterModel> authenPageMasterModel = apm.getPage(listpagegroupcode.get(a));
                                
	            	         	 	for(AuthenPageMasterModel apmm : authenPageMasterModel){ 
	            	         	 	boolean chkauthenpage = apm.chkAuthenPage(authen_code, apmm.getPage_code());
                                %>     
                                        <li>
                                            <label class="input-control checkbox small-check">
					                            <input type="checkbox"  name="chkpage" value="<%=apmm.getPage_code()%>" <%if(chkauthenpage==true){%>checked<%}%>>
					                            <span class="check"></span>
					                            <span class="caption"><%=apmm.getPage_name()%></span> 
					                        </label>
                                        </li> 
                                 <% } %> 
                                 	</ul>   
                            	</li>
                            </ul>
				 		</div>
				 	</div> 
				 	<%a++;  } %>
				</div>
			</div>	 
				 
			<%  }while(z <= countn); 
			 	
			  }
			%>
			  
			   
		 </form>  
		</div>   
		  
   		<script>
        $(function(){
        	$("#authen_type").select2();
        	
			$("#authen_type").change(function () {
        		
        	$("#ap").submit();
        	});
        });
    	</script>
	</body>
</html>
