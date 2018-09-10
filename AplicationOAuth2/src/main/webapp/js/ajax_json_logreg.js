/**
 * JavaScript para el envío de información al Login y al Registro de usuarios.
 */

$(document).ready(function(){
	$('#login-form').submit(function(){
		$.ajax({
			url: 'login_usuario',
			type: 'POST',
			datatype: 'json',
			data: $('#login-form').serialize(),
			success: function(data){
				console.log("El valor de data.goodFormat es: " + data.goodFormat);
				if(data.goodFormat){
					if(!data.isValid){
						$('#loginhide').html('El nombre: ' + data.username + ' no existe');
						$('#loginhide').slideDown(500);
					} else {
						window.location.href = "/AplicationOAuth2";
					}
				}
				else {
					alert('Please enter a valid username');
				}
			}
		})
		
		return false;
	})

})