<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="requisition">
  <head>
    <base href="<%=basePath%>">
    
    <title>หน้าเบิกสินค้า</title>
    
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
		<link href="css/docs.css" rel="stylesheet"> 
	 	<link href="css/select2.css" rel="stylesheet">
	 	<link href="css/bootstrap-datepicker3.css" rel="stylesheet">
	 	
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/docs.js"></script>
 		<script src="js/jquery.dataTables.min.js"></script>     
		<script src="js/select2.js"></script>
		<script src="js/bootstrap-datepicker-th.js"></script>
		<script src="js/angular.min.js"></script>
		<script src="js/requisition.js"></script>
  </head>
  
  <body ng-controller="myCtrl">
  	
    <div><%@include file="topmenu.jsp" %></div>
	<br>
	<form name="requisitionform">
		<div class="example" data-text="รายละเอียด">
		<div class="flex-grid">
  			<div class="row flex-just-center">
  				<div class="cell colspan1 "> 
			       	<h4 class="align-right">เลขที่เอกสาร&nbsp;</h4>
			    </div>
			    <div class="cell colspan4"> 
		    		<h4>
		    		<div class="input-control full-size"> 
					    <input id="docno" name="docno" ng-model="docno" />
					</div>
					</h4>
		    	</div>
		  	</div>
	  	</div>
			<div class="flex-grid">
			  	<div class="row flex-just-center">
			        <div class="cell colspan1 "> 
			       		<h4 class="align-right">โครงการ&nbsp;</h4>
			    	</div>
			    	<div class="cell colspan4" > 
			       		 <h4><small class="input-control full-size"> 
				       		 <select id="project_code" ng-change="projectchange()" ng-model="project" name="project_code" data-validate-func="required" data-validate-hint="กรุณาเลือกโครงการที่รับ">
							   <option value="">กรุณาเลือกโครงการ</option>
							   <%
							   
							   	List projectMasterList1 = null;
							   extendsprojectmaster ext = new extendsprojectmaster();
							   	projectMasterList1 = ext.getListProject_Join_Projecthead("", "","","");
							   	List projectMasterList = projectMasterList1;
				        		if (projectMasterList != null) {
					        		for (Iterator iterPj = projectMasterList.iterator(); iterPj.hasNext();) {
					        			ProjectModel pjModel = (ProjectModel) iterPj.next();
					        			
						        			
						        				
						        	%>
						        			<option value="<%=pjModel.getProject_code()%> - <%=pjModel.getYear() %>" >
							       			 	<%=pjModel.getProject_code()%> - <%=pjModel.getProject_name()%> - ปี <%=pjModel.getYear() %>
							       			</option>
						        	<%
						        			
			      						} 
									}
								%>
					   		</select>
						</small></h4>
			    	</div>
			    	<div class="cell colspan1 "> 
			       		<h4 class="align-right">วันที่&nbsp;</h4>
			    	</div>
			    	<div class="cell colspan4"> 
			    		<h4>
			    		<div class="input-control full-size"> 
						    <input id="day" name="day" ng-model="day" required/>
						</div>
						</h4>
			    	</div>
			    </div>
			</div>
			<div class="flex-grid">
			  	<div class="row flex-just-center">
			        <div class="cell colspan2"> 
			       		<h4>ดำเนินการในการจัด&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan1">
			    		<select name="type_requisition" ng-model="requisiton_type" required>
			    			<option value="">กรุณาเลือกข้อมูล</option>
			    			<option value="1">จัดซื้อ</option>
			    			<option value="2">จัดจ้าง</option>
			    			<option value="3">อื่น ๆ</option>
			    		</select>
			    	</div>
			    	
			    	 
			    	<div class="cell colspan1"> 
			      		<h4 align="right">คำอธิบาย&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan6">
			    		<h4><small class="input-control full-size">
						    <input type="text" id="description" name="description" ng-model="description" required> 
						</small></h4>
			    	</div>
			    	
			    </div>
			</div>
					
			<div class="flex-grid">
			  	<div class="row flex-just-center">
			        <div class="cell colspan2"> 
			       		<h4 align="right">ค่าใช้จ่าย&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan10">
			    		<h4><div class="input-control full-size">
			    			<select name="gcostcode" ng-model="gcostcode" id="gcostcode" ng-change="gcostcodechange()" required>
			    				<option value=""> -- please Select --</option>
						    	<option ng-repeat="option in datas" value="{{option.gcostcode}}">{{option.gcostcode_name}}</option>
						    	
						   	</select>
						</div></h4>
						
			    	</div>
				</div>
			</div> 
			<div class="flex-grid">
			  	<div class="row flex-just-center">  
			    	<div class="cell colspan1"> 
			       		<h4 align="right">จำนวน&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan1">
			    		<h4><small class="input-control full-size">
						    <input type="text" id="unit" name="unit" ng-model="unit" ng-keyup="amount=unit*priceperunit;tobalance=frombalance - (unit*priceperunit)" required> 
						</small></h4>
			    	</div> 
			    	<div class="cell colspan1"> 
			      		<h4 align="right">ราคา&nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan2">
			    		<h4><small class="input-control full-size">
						    <input type="text" id="priceperunit" name="priceperunit" ng-model="priceperunit"  ng-keyup="amount=unit*priceperunit;tobalance=frombalance - (unit*priceperunit)" required> 
						</small></h4>
			    	</div>
			    	<div class="cell colspan1"> 
			      		<h4 align="right">รวมราคา &nbsp;</h4> 	  
			    	</div> 
			    	<div class="cell colspan2">
			    		<h4><small class="input-control full-size">
						    <input type="text" id="amount" name="amount" ng-model="amount" required> 
						</small></h4>
			    	</div>
			    </div>
			</div> 
			
			<div class="flex-grid">
			  	<div class="row flex-just-center">
			        <div class="cell colspan2"> 
			       		<h4 align="left">คงเหลือ ยกมา&nbsp;</h4> 	  
			    	</div>
			    	<div class="cell colspan3">
			    		<h4><small class="input-control full-size">
						    <input type="text" id="frombalance" name="frombalance" value="{{ frombalance | currency:'฿' }}" required> 
						</small></h4>
			    	</div>
			    	
			    	<div class="cell colspan2"> 
			       		<h4 align="left">คงเหลือ ยกไป&nbsp;</h4> 	  
			    	</div>
			    	<div class="cell colspan3">
			    		<h4><small class="input-control full-size">
						    <input type="text" id="tobalance" name="tobalance" value="{{ tobalance | currency:'฿' }}" required> 
						</small></h4>
			    	</div>
			    </div>
			</div>
			
			<div class="flex-grid">
			  	<div class="row flex-just-center" >
			    	<div class="cell colspan12" align="center">
						<button class="button success" type="submit" name="add" ng-click="requisitionform.$valid && addrequisition()" >บันทึกการเบิก</button>
						<button ng-click="print()" class="button"><span class="mif-printer fg-green" ></span> Print</button>
					</div> 
			    </div>
			</div> 
		
		</div> <!-- End of example --> 
	</form>
	<div class="example" data-text="รายการ">
            <table id="table_project" class="dataTable striped border bordered" data-role="datatable" data-searching="true">
                <thead>
                <tr>  
                    <th>ชื่อโครงการ</th>
                    <th>ชื่อกิจกรรม</th> 
                    <th>ชื่อกิจกรรมย่อย</th>
                    <th>รายละเอียด</th>
                    <th>ประเภทการเบิก</th>
                    <th>จำนวน</th>
                    <th>ราคา</th>
                    <th>ราคารวม</th>
                    <th>คงเหลือยกมา</th>
                    <th>คงเหลือยกไป</th>
                    <th>เบิกโดย</th>
                    <th>ลบข้อมูล</th>
                </tr>
                </thead> 
                  
                <tbody>
                <tr ng-repeat="list in selectlist">  
                    <td>{{list.project_name}}</td>
                    <td>{{list.subjob_name}}</td>
                    <td>{{list.childsubjobname}}</td>
                    <td>{{list.description}}</td>
                    <td>{{list.requisition_typename}}</td>
                    <td>{{list.unit}}</td>
                    <td>{{list.priceperunit}}</td>  
                    <td>{{list.amount}}</td> 
                    <td>{{list.frombalance}}</td> 
                    <td>{{list.tobalance}}</td>
                    <td>{{list.takeby}}</td>  
                    <td><button ng-click="deleterequisition(list.requisition_docno,list.gcostcode)" class="button"><span class="mif-bin fg-red" ></span></button></td> 
                </tr>	 
                </tbody>
            </table>
        </div> <!-- End of example table --> 
    <script type="text/javascript">
		$(function(){
			$("#project_code").select2();
			
			$("#day").datepicker({
			    format: "dd/mm/yyyy",
		        todayBtn: true,
		        clearBtn: true,
		        autoclose: true,
		        todayHighlight: true
		    });
		});
	</script>
	
  </body>
</html>
