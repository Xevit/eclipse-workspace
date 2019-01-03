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
					window.close();
				}
			}
		})
		
		return false;
	})
})