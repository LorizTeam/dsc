<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.inventoryModel.*" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>หมวดสินค้า</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
		<link href="css/sweetalert.css" rel="stylesheet" />
		
		
	</head>
	<body>
		<s:set name="fromwindow" value="pdgmodel.fromwindow"/>
		
		<s:if test="%{#fromwindow=='true'}">
			<div><%@include file="window-topmenu.jsp" %></div>
		</s:if>
		<s:else>
			<div><%@include file="topmenu.jsp" %></div>
		</s:else>
		
		<form action="productgroupmaster.action" method="post">
			<div class="grid" >
				 <div class="row cells12 " >
		 			<div class="cell align-center colspan2">
	         	 	</div>
	         	 	<div class="cell align-center colspan3">
	         	 	</div>
		 			<div class="cell align-left colspan5"><h3>ข้อมูลหมวดสินค้า </h3></div>
		 			<div class="cell align-left colspan2">
					</div>
				</div>
			    <div class="example" data-text="Add">
			        <div class="row cells12 ">
				 		<div class="cell colspan1"> </div>
				 		<div class="cell colspan4">
				 			รหัสหมวดสินค้า
				 			<div class="input-control text"> 
				 				<s:textfield name="pdgmodel.progroup_id" id="progroup_id" readonly="true"/>
				 			</div>
				 		</div>
				 		<div class="cell colspan4"> 
				 			ชื่อหมวดสินค้า
				 			<div class="input-control text">
				 				<s:textfield name="pdgmodel.progroup_name" id="progroup_name" required=""/>
				 			</div>
				 			
				 			<button type="submit" class="button success" name="add" id="add"><span class="mif-plus mif-lg fg-white"></span></button>
				 			<button type="submit" class="button primary" name="update" id="update"><span class="mif-checkmark mif-lg fg-white"></span></button>
				 		</div>
				 		
				 	</div>
			    </div>
			    <div class="example" data-text="Display">
			    	<table id="table_progroup" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
		                <thead>
		                <tr>  
		                	<th>ลบข้อมูล <input type="checkbox" id="checkall"></th>
		                	<th>รหัสหมวดสินค้า</th>
		                	<th>ชื่อหมวดสินค้า</th>
		                    <th>วันที่เพิ่มข้อมูล</th>
		                    
		                </tr>
		                </thead> 
		                <tbody>
		                <%
			         		if(request.getAttribute("progroupList") != null){
			         			List<ProductGroupModel> progroupList = (List) request.getAttribute("progroupList");
			         			for(ProductGroupModel pdgmodel:progroupList){
			         				
			         	%>		
			         			
									<tr>
										<td><input type="checkbox" name="delprogroup" id="delprogroup" value="<%=pdgmodel.getProgroup_id() %>"> </td>
										<s:if test="%{#fromwindow=='true'}">
											<td class="progroup_id"><a href="#" onclick="getProgroup('<%=pdgmodel.getProgroup_id() %>','<%=pdgmodel.getProgroup_name() %>')"><%=pdgmodel.getProgroup_id() %></a></td>
										</s:if>
										<s:else>
											<td class="progroup_id"><%=pdgmodel.getProgroup_id() %></td>
										</s:else>
					                	<td class="progroup_name"><%=pdgmodel.getProgroup_name() %></td>
					                    <td><%=pdgmodel.getCreate_datetime() %></td>
					                    
					                </tr>
								
			         	<%
			         			}
			         		}
			         	%>
		                
		                </tbody>
		           	</table>
		           	<div class="row cells12 ">
				 		<div class="cell colspan4"> </div>
				 		<div class="cell colspan4"> 
				 			<button type="submit" class="button danger" name="delete" id="delete"><span class="mif-minus mif-lg fg-white"></span></button>
				 		</div>
				 		
				 	</div>
			    </div> 
		 	</div>
		 	<s:hidden name="pdgmodel.alertmsg" id="alertmsg"/>
		 	<s:hidden name="pdgmodel.fromwindow" id="fromwindow"/>
		</form>
		
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/jquery.dataTables.min.js"></script>
	    <script src="js/sweetalert.min.js"></script>
	    
		<script type="text/javascript">
		function getProgroup(progroup_id,progroup_name){
			window.opener.document.getElementById("progroup_id").value= progroup_id;
			window.opener.document.getElementById("progroup_name").value= progroup_name;
			window.close();
		}
		$(function(){
			if($("#alertmsg").val() != ""){
        		swal("Error",$("#alertmsg").val() , "error");
        	}
			var table = $('#table_progroup').DataTable( { 
         		scrollY: '39vh',
         		scrollX: true, 
         		scrollCollapse: true,
                ordering: false,
                "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
            } );
			$("#checkall").click(function(){
				if($(this).prop("checked")){
					$('[name="delprogroup"]').prop("checked",true);
				}else{
					$('[name="delprogroup"]').prop("checked",false);
				}
			});
			
			$("#delete").click(function(){
				$("#progroup_name").val("-");
			});
			$('#table_progroup tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		            $("#progroup_id").val("");
		            $("#progroup_name").val("");
		        }
		        else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		            var $index = $(this).index();
		            $("#progroup_id").val($(".progroup_id").eq($index).text());
		            $("#progroup_name").val($(".progroup_name").eq($index).text());
		        }
		    });
			
		});
		</script>
	</body>
</html>