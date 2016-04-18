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
		<s:set name="fromwindow" value="productModel.fromwindow"/>
		
		<s:if test="%{#fromwindow=='true'}">
			<div><%@include file="window-topmenu.jsp" %></div>
		</s:if>
		<s:else>
			<div><%@include file="topmenu.jsp" %></div>
		</s:else>
	<form action="product" method="post">
		<div class="grid" >
			<div class="row cells12 " >
	 			<div class="cell colspan4"> </div>
	 			<div class="cell align-right colspan1"><h3>สินค้า </h3>
	 			</div>
	 			<div class="cell colspan3"></div>
	 			<div class="cell colspan4">
				</div>
			</div>
			<div class="example" data-text="Add">
		        <div class="row cells12 ">
			 		<div class="cell colspan1"> </div>
			 		<div class="cell colspan4">
			 			รหัสสินค้า
			 			<div class="input-control text full-size"> 
			 				<s:textfield name="productModel.product_code" id="product_code" required=""/>
			 			</div>
			 		</div>
			 		<div class="cell colspan4"> 
			 			ชื่อสินค้า
			 			<div class="input-control text full-size">
			 				<s:textfield name="productModel.product_name" id="product_name" required=""/>
			 			</div>
			 		</div>
			 		
			 	</div>
			 	<div class="row cells12 ">
			 		<div class="cell colspan1"> </div>
			 		<div class="cell colspan4">
			 			หน่วยนับ
			 			<div class="input-control text full-size"> 
			 				<s:hidden name="productModel.unit_id" id="unit_id" readonly="true"/>
			 				<s:textfield name="productModel.unit_name" id="unit_name" required=""/>
			 				<div class="button-group">
						 	<button class="button primary" type="button" onclick="getunit()"> <span class="mif-search"></span></button>
							<button class="button danger" type="button" id="deleteunit"><span class="mif-bin"></span></button>
							</div>
			 			</div>
			 		</div>
			 		<div class="cell colspan4"> 
			 			หมวดสินค้า
			 			<div class="input-control text full-size">
			 				<s:hidden name="productModel.progroup_id" id="progroup_id" readonly="true"/>
			 				<s:textfield name="productModel.progroup_name" id="progroup_name" required=""/>
			 				<div class="button-group">
						 	<button class="button primary" type="button" onclick="getprogroup()"> <span class="mif-search"></span></button>
							<button class="button danger" type="button" id="deleteprogroup"><span class="mif-bin"></span></button>
							</div>
			 			</div>
			 			
			 		</div>
			 	</div>
			 	<div class="row cells12 ">
			 		<div class="cell colspan1"> </div>
			 		<div class="cell colspan4">
			 			ประเภทสินค้า
			 			<div class="input-control text full-size"> 
			 				<s:hidden name="productModel.protype_id" id="protype_id" readonly="true"/>
			 				<s:textfield name="productModel.protype_name" id="protype_name" required=""/>
			 				<div class="button-group">
						 	<button class="button primary" type="button" onclick="getprotype()"> <span class="mif-search"></span></button>
							<button class="button danger" type="button" id="deleteprotype"><span class="mif-bin"></span></button>
							</div>
			 			</div>
			 		</div>
			 		<div class="cell colspan4"> 
			 			แบรนด์สินค้า
			 			<div class="input-control text full-size">
			 				<s:hidden name="productModel.brand_id" id="brand_id" readonly="true"/>
			 				<s:textfield name="productModel.brand_name" id="brand_name" required=""/>
			 				<div class="button-group">
						 	<button class="button primary" type="button" onclick="getbrand()"> <span class="mif-search"></span></button>
							<button class="button danger" type="button" id="deletebrand"><span class="mif-bin"></span></button>
							</div>
			 			</div>
			 		</div>
			 	</div>
			 	<div class="row cells12 ">
				 	<div class="cell colspan4"> </div>
				 	<div class="cell colspan4">
				 	สถานะสินค้า
				 		<div class="input-control text full-size">
					 		<select name="productModel.status_id" id="status_id" required="">
					 			<option value="">กรุณาเลือกสถานะของสินค้า</option>
					 			<option value="01">Enable</option>
					 			<option value="02">Disable</option>
					 		</select>
				 		</div>
				 	</div>
				 	<div class="cell colspan4"> </div>
			 	</div>
			 	<div class="row cells12 ">
				 	<div class="cell colspan4"> </div>
				 	<div class="cell colspan4">
				 		<button type="submit" class="button success" name="add" id="add"><span class="mif-plus mif-lg fg-white"></span></button>
						<button type="submit" class="button primary" name="update" id="update"><span class="mif-checkmark mif-lg fg-white"></span></button>
				 	</div>
				 	<div class="cell colspan4"> </div>
			 	</div>
		    </div>
			
		</div>
		
		
				<div class="example" data-text="Display">
			    	<table id="table_product" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
		                <thead>
		                <tr>  
		                	<th>ลบข้อมูล <input type="checkbox" id="checkall"></th>
		                	<th>รหัสสินค้า</th>
		                	<th>ชื่อสินค้า</th>
		                	<th>หน่วยนับ</th>
		                	<th>หมวดสินค้า</th>
		                	<th>ประเภทสินค้า</th>
		                	<th>แบรนด์สินค้า</th>
		                	<th>สถานะ</th>
		                    <th>วันที่เพิ่มข้อมูล</th>
		                    
		                </tr>
		                </thead> 
		                <tbody>
		                <%
			         		if(request.getAttribute("productList") != null){
			         			List<ProductModel> productList = (List) request.getAttribute("productList");
			         			for(ProductModel productModel:productList){
			         				
			         	%>		
			         			
									<tr>
										<td><input type="checkbox" name="delproduct" id="delproduct" value="<%=productModel.getProduct_code() %>"> </td>
										<s:if test="%{#fromwindow=='true'}">
											<td class="product_code"><a href="#" onclick="sendProduct('<%=productModel.getProduct_code() %>','<%=productModel.getProduct_name() %>')"><%=productModel.getProduct_code() %></a></td>
										</s:if>
										<s:else>
											<td class="product_code"><%=productModel.getProduct_code() %></td>
										</s:else>
										<td class="product_name"><%=productModel.getProduct_name() %></td>
					                	<td class="unit"><%=productModel.getUnit_id() %>-<%=productModel.getUnit_name() %></td>
					                	<td class="progroup"><%=productModel.getProgroup_id() %>-<%=productModel.getProgroup_name() %></td>
					                	<td class="protype"><%=productModel.getProtype_id() %>-<%=productModel.getProtype_name() %></td>
					                	<td class="brand"><%=productModel.getBrand_id() %>-<%=productModel.getBrand_name() %></td>
					                	<td class="status"><%=productModel.getStatus_id() %>-<%=productModel.getStatus_name() %></td>
					                    <td><%=productModel.getCreate_datetime() %></td>
					                    
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
		<s:hidden name="productModel.alertmsg" id="alertmsg"/>
		<s:hidden name="productModel.fromwindow" id="fromwindow"/>
		
		</form>

		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/jquery.dataTables.min.js"></script>
	    <script src="js/sweetalert.min.js"></script>
   		
	    <script type="text/javascript">
	    function getunit() {
   			var load = window.open('/pcpnru/windows_entranc_unitMaster.action','pr',
   			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
	    function getprogroup() {
   			var load = window.open('/pcpnru/windows_entranc_productgroupmaster.action','pr',
   			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
	    function getprotype() {
   			var load = window.open('/pcpnru/windows_entranc_protypemaster.action','pr',
   			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
	    function getbrand() {
   			var load = window.open('/pcpnru/windows_entranc_brandmaster.action','pr',
   			             'scrollbars=yes,menubar=no,height=700,width=1280,resizable=yes,toolbar=no,location=yes,status=no');
   		}
	    function sendProduct(product_code,product_name){
			window.opener.document.getElementById("product_code").value= product_code;
			window.opener.document.getElementById("product_name").value= product_name;
			window.close();
		}
		$(function(){
			if($("#alertmsg").val() != ""){
        		swal("Error",$("#alertmsg").val() , "error");
        	}
			var table = $('#table_product').DataTable( { 
         		scrollY: '39vh',
         		scrollX: true, 
         		scrollCollapse: true,
                ordering: false,
                "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
            } );
			$("#checkall").click(function(){
				if($(this).prop("checked")){
					$('[name="delproduct"]').prop("checked",true);
				}else{
					$('[name="delproduct"]').prop("checked",false);
				}
			});
			
			$("#delete").click(function(){
				$("#product_code").val("-");
				$("#product_name").val("-");
				$("#unit_name").val("-");
				$("#progroup_name").val("-");
				$("#protype_name").val("-");
				$("#brand_name").val("-");
			});
			
			$('#table_product tbody').on( 'click', 'tr', function () {
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		            $("#product_code").val("");
		            $("#product_name").val("");
		            $("#unit_id").val(""); $("#unit_name").val("");
		            $("#progroup_id").val(""); $("#progroup_name").val("");
		            $("#protype_id").val(""); $("#protype_name").val("");
		            $("#brand_id").val(""); $("#brand_name").val("");
		            $("#status_id").val("");
		        }
		        else {
		            table.$('tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		            var $index = $(this).index();
		            $("#product_code").val($(".product_code").eq($index).text());
		            $("#product_name").val($(".product_name").eq($index).text());
		            $("#unit_id").val($(".unit").eq($index).text().split("-")[0]); 
		            $("#unit_name").val($(".unit").eq($index).text().split("-")[1]);
		            $("#progroup_id").val($(".progroup").eq($index).text().split("-")[0]); 
		            $("#progroup_name").val($(".progroup").eq($index).text().split("-")[1]);
		            $("#protype_id").val($(".protype").eq($index).text().split("-")[0]); 
		            $("#protype_name").val($(".protype").eq($index).text().split("-")[1]);
		            $("#brand_id").val($(".brand").eq($index).text().split("-")[0]); 
		            $("#brand_name").val($(".brand").eq($index).text().split("-")[1]);
		            $("#status_id").val($(".status").eq($index).text().split("-")[0]);
		        }
		    });
			
		});
		</script>
</body>
</html>