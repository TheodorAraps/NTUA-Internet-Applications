/**
 * Javascript Document
 */

document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const pass1InputField = document.querySelector('input[name=password]');
	const pass2InputField = document.querySelector('input[name=password2]');
	
	// Add Click Event Listener
	pass2InputField.addEventListener("keyup", function(){
		var pass1 = pass1InputField.value;
		var pass2 = pass2InputField.value;
		// Check
		if (pass1 != pass2)
			pass2InputField.style.backgroundColor = "red";
		else
			pass2InputField.style.backgroundColor = "green";
	});	
	
})
