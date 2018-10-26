/**	
 * Javascript para el envío de la solicitud de Api y su respuesta
**/

$(document).ready(function(){
	$('#altaApi').submit(function(){
		$.ajax({
			url: 'AltaEnAPI',
			type: 'POST',
			datatype: 'json',
			beforeSend: function (){
				$("#altaEspera").html("Procesando la petición, por favor, espere.");
			},
			success: function(data){
				console.log("El valor de data.goodFormat es: " + data.goodFormat);
			}
		})
		
		return false;
	})
})

$(document).ready(function(){
	$('#bajaApi').submit(function(){
		$.ajax({
			url: 'BajaEnAPI',
			type: 'POST',
			datatype: 'json',
			beforeSend: function (){
				$("#bajaEspera").html("Procesando la petición, por favor, espere.");
			},
			success: function(data){
				console.log("El valor de data.goodFormat es: " + data.goodFormat);
			}
		})
		
		return false;
	})
})