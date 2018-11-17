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
				console.log("El valor de data.exist es: " + data.exist);
				if (data.exist == "true"){
					$("#altaEspera").hide();
					$("#altaResponseOK").hide();
					$("#altaResponseKO").show();
					$("#altaResponseKO").html('La aplicación ya estaba dada de alta en la API');
				} else if (data.exist == "false"){
					$("#altaEspera").hide();
					$("#altaResponseKO").hide();
					$("#altaResponseOK").show();
					$("#altaResponseOK").html('La aplicación se ha dado de alta correctamente en la API.');
				}
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
				console.log("El valor de data.exist es: " + data.exist);
				if (data.exist == "true"){
					$("#bajaEspera").hide();
					$("#bajaResponseKO").hide();
					$("#bajaResponseOK").show();
					$("#bajaResponseOK").html('La aplicación se ha dado de baja en la API');
				} else if (data.exist == "false"){
					$("#bajaEspera").hide();
					$("#bajaResponseOK").hide();
					$("#bajaResponseKO").show();
					$("#bajaResponseKO").html('La aplicación no está dada de alta en la API.');
				}
			}
		})
		return false;
	})
})