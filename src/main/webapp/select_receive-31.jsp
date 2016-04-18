<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>หน้ารายละเอียดการรับ</title>
    
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
	<div class="flex-grid">
		  	<div class="row flex-just-center">
		        <div class="cell colspan2 "> 
		       	<h1 class="align-right">โครงการ &nbsp;</h1>
		    	</div>
		    	<div class="cell colspan5" > 
		       		 <h2><small class="input-control"> 
		       		 <select onchange="" disabled>  
					        <option>521800002 - แหล่งเรียนรู้และวิจัย กาซะลองสปา</option> 
					   </select>
					   </small></h2>
		    	</div>
		    </div>
	</div>
	<hr/>  
	<div class="flex-grid">
		  	<div class="row flex-just-left">
		        <div class="cell colspan2"> 
		       	<h2 align="right">ค่าใช้จ่าย&nbsp;</h2> 	  
		    	</div> 
		    	<div class="cell colspan10">
		    		<h3><small class="input-control full-size"><select onchange="" disabled>
					    	<option>521800003 - บริการนวดอโรมา</option> 
					   </select></small>
					</h3>
		    	</div>  
		    </div>
	</div>  
	<hr/>
	<div class="flex-grid">
		  	<div class="row flex-just-center">
		        <div class="cell colspan2"> 
		       		<h2 class="align-right">ได้รับเงินจาก&nbsp;</h2> 	  
		    	</div> 
		    	<div class="cell colspan6">
		    		<h3><small class="input-control full-size"> 
					    <input type="text" disabled id="subjobcode" name="subjobCode" value="คุณ พานุวัฒน์"> 
					</small></h3>
		    	</div>  
		    	<div class="cell colspan2"> 
		       		<h2 class="align-right">สถานที่&nbsp;</h2> 	  
		    	</div> 
		    	<div class="cell colspan2">
		    		<h3><small class="input-control full-size"> 
					    <input type="text" disabled id="subjobcode" name="subjobCode" value="สมาร์ทไอซีที"> 
					</small></h3>
		    	</div>  
		    </div>
		</div>
	<hr/> 
	<div class="flex-grid">
		  	<div class="row flex-just-center">
		        <div class="cell colspan2"> 
		       		<h2 class="align-right">รายละเอียด&nbsp;</h2> 	  
		    	</div> 
		    	<div class="cell colspan6">
		    		<h3><small class="input-control full-size"> 
					    <input type="text" id="subjobcode" name="subjobCode"> 
					</small></h3>
		    	</div>  
		    	<div class="cell colspan1"> 
		       		<h2 class="align-right">จำนวน&nbsp;</h2> 	  
		    	</div>
		    	<div class="cell colspan1">
		    		<h3><small class="input-control full-size"> 
					    <input type="text" id="subjobcode" name="subjobCode"> 
					</small></h3>
		    	</div> 
		    	<div class="cell colspan1"> 
		       		<h2 class="align-right">ราคา&nbsp;</h2> 	  
		    	</div> 
		    	<div class="cell colspan1">
		    		<h3><small class="input-control full-size"> 
					    <input type="text" id="subjobcode" name="subjobCode"> 
					</small></h3>
		    	</div>  
		    </div>
		</div>
	<hr/>
	<div class="flex-grid">
		  	<div class="row flex-just-center">
		    	<div class="cell colspan3" align="center">
					  <button class="button success" type="submit" name="add">เพิ่ม</button> 
					  <button class="button success" type="submit" name="update">แก้ไข</button> 
					  <button class="button success" type="submit" name="delete">ลบ</button>
				</div> 
		    </div>
	</div>
	<hr/>
	</div> <!-- End of example --> 
	
	<div class="example" data-text="รายการ">
            <table id="table_project" class="dataTable striped border bordered" data-role="datatable" data-searching="true">
                <thead>
                <tr>  
                	<th>เลขที่</th>
                    <th>รายละเอียด</th>
                    <th>จำนวน</th>
                    <th>ราคา</th>
                    <th>ราคารวม</th>
                </tr>
                </thead> 
                  
                <tbody>
                <tr>  
                    <td>1</td>
                    <td>ผู้ชาย</td>
                    <td>2</td>
                    <td>250 บาท</td>  
                    <td>500 บาท</td> 
                </tr>
                <tr>  
                    <td>2</td>
                    <td>ผู้หญิง</td>
                    <td>3</td>
                    <td>200 บาท</td>  
                    <td>600 บาท</td> 
                </tr>
                <tr>  
                    <td>3</td>
                    <td>เด็ก</td>
                    <td>2</td>
                    <td>150 บาท</td>  
                    <td>300 บาท</td> 
                </tr>	 
                </tbody>
            </table>
        </div> <!-- End of example table --> 	

  </body>
</html>
