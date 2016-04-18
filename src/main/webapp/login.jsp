<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
   
    <link rel='shortcut icon' type='image/x-icon' href='favicon.ico' />

    <title>Login form</title>

    <link href="css/metro.css" rel="stylesheet">
    <link href="css/metro-icons.css" rel="stylesheet">
    <link href="css/metro-responsive.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
   
</head>
<body class="bg-dark">

	<script src="js/jquery-2.1.3.min.js"></script>
    <script src="js/metro.js"></script>
    
    <div class="login-form padding20 block-shadow">
        <form action="login" method="post">
            <h1 class="text-light">Login to service</h1>
            <hr class="thin"/>
            <br />
            <div class="input-control text full-size">
                <label for="user_login">Username:</label>
                <s:textfield name="empmodel.username" required=""/>
                <button class="button helper-button clear"><span class="mif-cross"></span></button>
            </div>
            <br />
            <br />
            <div class="input-control password full-size">
                <label for="user_password">Password:</label>
                <s:password name="empmodel.password" required=""/>
                <button class="button helper-button reveal"><span class="mif-looks"></span></button>
            </div>
            <br />
            <br />
            <div class="form-actions">
                <button type="submit" class="button primary">Login to...</button>
                <button type="button" class="button link">Cancel</button>
            </div>
        </form>
    </div>

 	

    <script>
        $(function(){
            var form = $(".login-form");

            form.css({
                opacity: 1,
                "-webkit-transform": "scale(1)",
                "transform": "scale(1)",
                "-webkit-transition": ".5s",
                "transition": ".5s"
            });
        });
    </script>


</body>
</html>