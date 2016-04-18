<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="pcpnru.projectData.*" %>
<%@ page import="pcpnru.projectModel.*" %>
<%@ page import="pcpnru.utilities.*" %>
<%
	if(session.getAttribute("username") == null){
		response.sendRedirect("login.jsp");
	}else{
		String username = session.getAttribute("username").toString();
		boolean chkAuthen = false;
		String page_code = "002";
		
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
		<title>งบกลาง</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width; initial-scale=1.0">
		
		<link rel="shortcut icon" href="/favicon.ico">
		<link href="css/metro.css" rel="stylesheet">
        <link href="css/metro-icons.css" rel="stylesheet">
		<link href="css/metro-schemes.css" rel="stylesheet">
		<link href="css/docs.css" rel="stylesheet"> 
		<link href="css/jquery.dataTables.min.css" rel="stylesheet">
	 
		<script src="js/jquery-2.1.3.min.js"></script>
	    <script src="js/metro.js"></script>
	    <script src="js/docs.js"></script>
	    <script src="js/prettify/run_prettify.js"></script>
	    <script src="js/ga.js"></script> 
 		<script src="js/jquery.dataTables.min.js"></script>
	</head>

	<body>
		 <div><%@include file="topmenu.jsp" %></div>
		 <h3 class="align-center">งบกลาง</h3>
		 <form action="createCentralBudget.action" method="post">
		 <div class="example" data-text="รายละเอียด">
         <div class="grid">
		  	<div class="row cells12">
		  		<div class="cell colspan1"> 
		        	ปี
			        <div class="input-control text full-size"> 
					    <s:textfield id="year" name="centralBudgetForm.year" maxLength="4" required="" /> 
					</div> 
				</div>
		  		<div class="cell colspan2"> 
		        	จำนวนเงิน
			        <div class="input-control text full-size" dir="rtl"> 
					    <s:textfield id="amt" name="centralBudgetForm.amt" required="" />
					</div> 
				</div>
				<div class="cell colspan4"><br>
					  <button class="button success" type="submit" name="add">สร้าง</button> 
					  <button class="button primary" type="submit" name="update">แก้ไข</button> 
					  <button class="button danger" type="submit" name="delete">ลบ</button>
				</div> 
		    </div>
		 </div>  
		</div>  
		
        <div class="example" data-text="รายการ" >
            <table id="table_unit" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr> 
                	<th width="10%">เลขที่</th>
                    <th width="40%">ปี</th>  
                    <th width="50%">จำนวนเงิน</th>
                </tr>
                </thead> 
                  
                <tbody>
                <%	CreateCentralBudgetDB ccb = new CreateCentralBudgetDB();
            		List CentralBudgetList = ccb.GetUnitMasterList("");
                
                	if (CentralBudgetList != null) {
						int x = 0;
						for (Iterator iter = CentralBudgetList.iterator(); iter.hasNext();) {
						x++; 
						CentralBudgetForm cbf = (CentralBudgetForm) iter.next();
				%>
                <tr> 
                    <td align="center" width="10%"><%=x%></td>
                    <td class="tdyear" align="left" width="40%"><%=cbf.getYear()%></td> 
                    <td class="tdamt" align="left" width="50%"><%=cbf.getAmt()%></td>
                </tr>	  
                <% 	} %>
                
                <%} else { %> 
                	<tr> 
                    <td colspan="3">ไม่พบข้อมูล</td> 
                	</tr> 
                <%	} %>
                </tbody>
            </table> 
            
        </div> <!-- End of example table --> 
     	</form>
     	
   		<script>
   		
   		$("#amt").blur(function (){
				 
   			var budget = $("#amt").val(); 
   			var t1 = "";
   			if(budget == "NaN"){
   				t1 = "0";
   			}else if(budget == ""){
   				t1 = "0";
   			}
   			else{
   				budget = budget.replace(/,/g,"");
   		    var t1 = parseFloat(budget).toLocaleString("en-US");
   			} 
   			$("#amt").val(t1);
   		});
   		
        $(document).ready(function() {
        	
        	$("#year").focus();
        	
    	var table = $('#table_unit').DataTable( {
          	scrollY:  '36.5vh',
          	scrollX: true,
          	scrollCollapse: true,
          	ordering: false,
          	"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
      	} ); 
        	
		$('#table_unit tbody').on( 'click', 'tr', function () { 
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	            $("#year").val("");
	            $("#amt").val(""); 
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            var $index = $(this).index();
	            $("#year").val($(".tdyear").eq($index).text());
	            $("#amt").val($(".tdamt").eq($index).text()); 
	        }
	    });
	} );
    	</script>
	</body>
</html>
