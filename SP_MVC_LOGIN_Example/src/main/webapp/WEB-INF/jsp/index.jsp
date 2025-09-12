<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8"%>
<head>
<title>Login</title>
</head>
<body>
	<h1>Get Request - Login</h1>
	<form action='/login' method="get">
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

	<h1>Post Request - Login</h1>
	<form action='/login_post' method="post">
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