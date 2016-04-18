<%@ page language="java" import="java.util.*,java.text.DecimalFormat" pageEncoding="utf-8"%>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Employee</title>
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
		 <br>
		 <div class="example" data-text="รายละเอียด">
         <div class="grid">
		  	<div class="row cells10">
		        <div class="cell"> 
		        	รหัสพนักงาน
			        <div class="input-control text full-size">
					    <input type="text">
					</div>
				</div>
		        <div class="cell colspan2"> 
		        	ชื่อ-นามสกุล พนักงาน
			        <div class="input-control text full-size">
					    <input type="text">
					</div>
				</div>
		        <div class="cell"> 
		        	วันเกิด
			        <div class="input-control text full-size">
					    <input type="text">
					</div>
				</div>
				<div class="cell"> 
		        	เบอร์โทรศัพท์
			        <div class="input-control text full-size">
					    <input type="text">
					</div>
				</div>
				<div class="cell colspan2"> 
		        	ที่อยู่
			        <div class="input-control text full-size">
					    <input type="text">
					</div>
				</div>
				<div class="cell"> 
		        	ตำแหน่งงาน
			        <div class="input-control text full-size">
					    <input type="text">
					</div>
				</div>
				<div class="cell colspan2" ><br>
			        	<button class="button success">เพิ่ม</button> <button class="button success">แก้ไข</button> <button class="button success">ลบ</button>
				</div> 
		    </div>
		 </div>  
		</div>  
		 
        <div class="example" data-text="รายการ">
            <table id="table_employee" class="cell-border hover display compact nowrap" cellspacing="0" width="100%">
                <thead>
                <tr> 
                    <th>รหัสพนักงาน</th>
                    <th>ชื่อ-นามสกุลพนักงาน</th>
                    <th>วันเกิด</th>
                    <th>เบอร์โทรศัพท์</th>  
                    <th>ที่อยู่</th> 
                    <th>ตำแหน่งงาน</th> 
                    <th></th>
                </tr>
                </thead> 
                  
                <tbody>
                <tr> 
                    <td>255901</td>
                    <td>นาย สุสวัส พัฒนาโครงการ</td>
                    <td>01-01-2559</td>
                    <td>099-111-8999</td> 
                    <td>99/999 ม.9 ถ.กำลังพัฒนา ต.เมือง อ.เมือง จ.กรุ่งเทพฯ 19999</td>
                    <td>บัญชี</td>
                    <td class="align-center"><a href="projectdt.jsp"  class="toolbar-button"><span class="mif-pencil"></span></a></td>  
                </tr>	 
                </tbody>
            </table>
        </div> <!-- End of example table -->  
         
   		<script>
        $(function(){ 
             	$('#table_employee').DataTable( { 
              	scrollY: '37.5vh', 
              	scrollX: true,
              	scrollCollapse: true,
                ordering: false,
                "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]] 
            } );
        });
    	</script>
	</body>
</html>
