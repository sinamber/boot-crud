<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>BOOT-CRUD</title>
<link rel="shortcut icon" href="../img/favicon.ico">
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="../bootstrap/js/html5shiv.min.js"></script>
<script src="../bootstrap/js/respond.min.js"></script>
<![endif]-->
<style type="text/css">
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h1>Spring Boot + Mybatis + Freemarker ,Hello World!</h1>
			<ul>
				<#list data_list as data>
				 <li>${data}</li>
				</#list>
			</ul>
		</div>
	</div>
</div>
<script src="../bootstrap/js/jquery-2.0.3.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>
</body>
</html>