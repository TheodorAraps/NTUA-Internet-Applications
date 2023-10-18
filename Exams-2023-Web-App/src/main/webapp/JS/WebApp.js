/**
 * Javascript Document
 */

document.addEventListener('DOMContentLoaded', function(event) {
	
	// Find Element by ID when DOM is ready
	const userNameField = document.querySelector('input[name=username]'); // input tag
	const loginButton = document.querySelector('input[name=login]'); // input tag
	const PassInputField = document.querySelector('input[name=password]'); // input tag
	const passStrength = document.querySelector('#passdiv'); // div tag
	
	loginButton.disabled = true;
	
	// Add Click Event Listener
	userNameField.addEventListener("keyup", function(){
		var username = userNameField.value;
		
		// Regex
		const onlySpaces = /^\s*$/;
		
		// Check password Strength
		if (username == "" || onlySpaces.test(username))
		{
			loginButton.disabled = true;
		}
		else 
		{
			loginButton.disabled = false;
		}
	});	
	
	// Add Click Event Listener
	PassInputField.addEventListener("keyup", function(){
		var pass = PassInputField.value;
		
		// Regex
		const lettersNumbers = /^[a-z0-9]+$/i;
		const onlySpaces = /^\s*$/;
		
		// Check password Strength
		if (lettersNumbers.test(pass) || pass == "" || onlySpaces.test(pass))
		{
			passStrength.style.color = "red";
			passStrength.innerHTML = "Easy";
		}
		else if (pass.length < 8)
		{
			passStrength.style.color = "orange";
			passStrength.innerHTML = "Medium";
		}
		else
		{
			passStrength.style.color = "black";
			passStrength.innerHTML = "Hard";
		}
	});	
	
})
