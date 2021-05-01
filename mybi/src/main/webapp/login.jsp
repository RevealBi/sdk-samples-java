<%@page contentType="text/html" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>MyBI</title>
    <link rel="stylesheet" href="css/site.css"/>
    <link rel="preconnect" href="https://code.jquery.com" />

    <link rel="stylesheet" href="https://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
    <script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js"></script>
</head>
<body>
	<script type="text/javascript">
		function submitLogin() {
			var username = document.getElementById("Login_Username_Input").value;
			var password = document.getElementById("Login_Password_Input").value;
			
			if (username == null || password == null || username == "" || password == "") {
				console.log("missing values for login");
				return;
			}
			$.post(
					{
						url: "${pageContext.request.contextPath}/api/login", 
						contentType: 'application/json',
						data: JSON.stringify({
							username: username,
							password: password
						})
					})
					.done(function(data) {
						if (data.succeeded) {
							location.pathname="${pageContext.request.contextPath}";
						} else {
							document.getElementById("Login_Error").innerHTML = data.message;
						}
					})
					.fail(function(xhr) {
						console.log("login failed: " + xhr.status);	
						document.getElementById("Login_Error").innerHTML = "Login failed";
					});
		}
		function checkSubmitEnabled() {
			var username = document.getElementById("Login_Username_Input").value;
			var password = document.getElementById("Login_Password_Input").value;
			
			var disabled = (username == null || password == null || username == "" || password == "");
			document.getElementById("Login_Submit").disabled = disabled;
			return !disabled;
		}
		function userOnKeyup(e) {
			checkSubmitEnabled(); 
		}
		function pwdOnKeyup(e) {
			var ok = checkSubmitEnabled(); 
			if (ok && e.code === "Enter") {
				submitLogin();
			}
		}
	</script>
	<div class="Login-Container">
	    <div class="Login-Card">
	        <div class="Login-Form">
	            <input id="Login_Username_Input" class="Login-Input" type="username" placeholder="user name" onkeyup="userOnKeyup(event)"/>
	            <input id="Login_Password_Input" class="Login-Input" type="password" placeholder="password" onkeyup="pwdOnKeyup(event)"/>
	            <button id="Login_Submit" class="Login-Button" onclick="submitLogin()" disabled>Sign In</button>
	            <div id="Login_Error" class="Login-Error"></div>
	        </div>
	    </div>
	</div>
</body>
</html>