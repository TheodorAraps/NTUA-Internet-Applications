/**
 * Javascript Document
 */

document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const newPassInputField = document.querySelector('input[name=newPassword]'); // input tag
	const passStrength = document.querySelector('#passStrength'); // span tag
	
	// Add Click Event Listener
	newPassInputField.addEventListener("keyup", function(){
		var pass = newPassInputField.value;
		
		// Regex
		const letters = /[a-zA-z]/;
		const numbers = /\d/;
		const puncs = /[.,\/#!$%\^&\*;:{}=\-_'`~()]/;
		
		// Check password Strength
		if (letters.test(pass) && numbers.test(pass) && puncs.test(pass))
		{
			passStrength.style.color = "green";
			passStrength.innerHTML = "Hard";
		}
		else if (letters.test(pass) && numbers.test(pass) && !puncs.test(pass) || letters.test(pass) && puncs.test(pass) && !numbers.test(pass) || !letters.test(pass) && puncs.test(pass) && numbers.test(pass))
		{
			passStrength.style.color = "orange";
			passStrength.innerHTML = "Medium";
		}
		else
		{
			passStrength.style.color = "red";
			passStrength.innerHTML = "Easy";
		}
	});	
	
})
