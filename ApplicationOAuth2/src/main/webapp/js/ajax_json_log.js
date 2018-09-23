/**
 * JavaScript para el envío de información al Login de usuarios.
 */

$('#login-form').submit(function(){
	$('#loginhide').hide();
})

$('#register-form').submit(function(){
	$('#reginhideKO').hide();
	$('#reginhideOK').hide();
})


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
						$('#loginhide').html('El nombre de usuario o contraseña no es correcto');
						$('#loginhide').slideDown(500);
					} else {
						window.location.href = "/ApplicationOAuth2";
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

$(document).ready(function(){
	$('#register-form').submit(function(){
		$.ajax({
			url: 'registra_usuario',
			type: 'POST',
			datatype: 'json',
			data: $('#register-form').serialize(),
			success: function(data){
				console.log();
				if(data.goodFormat){
					if (data.userExist){
						$('#reginhideKO').html('El usuario ya existe');
						$('#reginhideKO').slideDown(500);
					} else {
						$('#reginhideOK').html('El usuario ha sido creado satisfactoriamente');
						$('#reginhideOK').slideDown(500);
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