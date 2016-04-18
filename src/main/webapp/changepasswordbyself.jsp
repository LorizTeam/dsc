<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(session.getAttribute("username") == null)response.sendRedirect("login.jsp");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>เปลี่ยนรหัสผ่าน</title>
	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width; initial-scale=1.0">
	
	<link rel="shortcut icon" href="/favicon.ico">
	<link href="css/metro.css" rel="stylesheet">
    <link href="css/metro-icons.css" rel="stylesheet">
	<link href="css/metro-schemes.min.css"rel="stylesheet" />
	<link href="css/style.css" rel="stylesheet" />
	<link href="css/docs.css" rel="stylesheet">
	
</head>
<body>
	<script src="js/jquery-2.1.3.min.js"></script>
    <script src="js/metro.js"></script>
	<%@ include file='topmenu.jsp' %>
	
		<h3 class="align-center">เปลี่ยนรหัสผ่าน</h3>
	<div class="example" data-text="เปลี่ยนรหัสผ่าน">
		<form action="index.jsp">
			<div class="grid">
				<div class="row cells10 ">
					<div class="cell offset4 colspan2">
						ขื่อผู้ใช้งาน (Username)
						<div class="input-control full-size text">
							<input type="text" name="username" id="username" disabled>
						</div>
					</div>
				</div>
				<div class="row cells10">
					<div class="cell offset4 colspan2">
						รหัสผ่านปัจจุบัน
						<div class="input-control full-size text">
							<input type="text" name="password" id="password" required>
						</div>
					</div>
				</div>
				<div class="row cells10">
					<div class="cell offset4 colspan2">
						รหัสผ่านใหม่
						<div class="input-control full-size text">
							<input type="text" name="password" id="password" required>
						</div>
					</div>
				</div>
				<div class="row cells10">
					<div class="cell offset4 colspan2">
						รหัสผ่านใหม่ อีกครั้ง
						<div class="input-control full-size text">
							<input type="text" name="repassword" id="repassword" required>
						</div>
					</div>
				</div>
				<div class="row cells10">
					<div class="cell offset4 colspan2"> 
						<button type="submit" class="button primary">เปลี่ยนรหัสผ่าน</button>
                		<button type="submit" class="button danger">ยกเลิก</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>