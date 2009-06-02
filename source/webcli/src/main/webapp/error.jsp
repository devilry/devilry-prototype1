<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>Error</title>
		<link rel="stylesheet" type="text/css" href="skin.css"/>
	</head>
	<body>
        <h1>${sitetitle}</h1>


		<div id="errorbox">
			<h2>Error!</h2>
			${errormsg}
		</div>
	</body>
</html>