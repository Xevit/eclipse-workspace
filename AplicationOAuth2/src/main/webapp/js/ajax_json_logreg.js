/**
 * JavaScript para el envío de información al Login y al Registro de usuarios.
 */

$(document).ready(function(){
	$('#login-form').submit(function(){
		$.ajax({
			url: 'update',
			type: 'POST',
			datatype: 'json',
			data: $('#updateUsername').serialize(),
			success: function(data){
				if(data.goodFormat){
					if(!data.isValid){
						$('#loginhide').html('El nombre: ' + data.username + 'no existe');
						$('#loginhide').slideDown(500);
					} 
				}
				else {
					alert('Please enter a valid username');
				}
				/if(data.isValid){
				/	$('#displayName').html('Your name is: ' + data.username);
				/	$('#displayName').slideDown(500);
				/} 
				/else{
				/	alert('Please enter a valid username!');
				/}
			}
		})
		return false;
	})

})