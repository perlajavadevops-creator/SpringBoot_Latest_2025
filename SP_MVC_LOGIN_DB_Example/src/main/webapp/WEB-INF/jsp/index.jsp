<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8"%>
<head>
<title>Login</title>
</head>
<body>
	<form action='/login' method="post">
		<div>
			<label>Username: <input type="text" name="username" /></label>
		</div>
		<div>
			<label>Password: <input type="password" name="password" /></label>
		</div>
		<div>
			<button type="submit">Login</button>
		</div>
	</form>


</body>
</html>