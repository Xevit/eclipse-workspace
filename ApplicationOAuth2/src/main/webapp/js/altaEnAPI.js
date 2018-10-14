
$(document).ready(function(){
	if (exist == "true"){
		$("#AppExist").show();
	} else if(exist == "false") {
		$("#AppDontExistYet").show();
	} else {
		$("#AppRegisterError").show();
	}
})