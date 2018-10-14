<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-info">
      <a id="oAuth2" class="navbar-brand" href="/ApplicationOAuth2">oAuth2</a>
    </nav>
    <main role="main">
		<div class="container">
	    	<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-login">
						<div class="panel-heading">
							<div class="row">
								<div class="col-md-12" id="ActionResponse">
									<div class="alert alert-warning" role="alert" id="AppExist" style="display: none;">
									  <strong>Warning!</strong> La aplicación ya estaba dada de alta en el API Server.
									</div>
									<div class="alert alert-success" role="alert" id="AppDontExistYet" style="display: none;">
									  <strong>Well done!</strong> La aplicación ha sido dada de alta en el API Server.
									</div>
									<div class="alert alert-danger" role="alert" id="AppRegisterError" style="display: none;">
									  <strong>Error!</strong> Se ha producido un error a la hora de dar de alta la aplicación. Por favor, contacte con el administrador de sistemas.
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</main>	
	<script>
		var exist = '${exist}';
	</script>	
	<script src="../js/altaEnAPI.js"></script>
</body>
</html>