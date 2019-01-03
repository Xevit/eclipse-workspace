$(document).ready(function(){
	$('#login-form').submit(function(){
		$.ajax({
			url: 'ProcessGrant',
			type: 'POST',
			datatype: 'json',
			data: $('#login-form').serialize(),
			success: function(data){
				console.log("El valor de data.isValidUser es: " + data.isValidUser);
				if(!data.isValidUser){
					alert('Usuario o password incorrecto');
				} else {
					alert('Usuario Correcto');
					callProcess();
				}
			}
		})
		
		return false;
	})
})

function callProcess(){
	//window.location.replace("ProcessGrant");
	window.location.href = "http://localhost:8080/APIOAuth2/ProcessGrant" + "?" + "client_id=" + client_id + "&" + "redirect_uri=" + redirect_uri;
}