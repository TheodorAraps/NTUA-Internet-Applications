let userID = "EMPTY"

 async function checkLogin() {
	let result = false;
	const url = 'https://oauth2.googleapis.com/tokeninfo?id_token=' + userID;
	result = await fetch(url).then(response => response.json()).then(json => {
		if ("error" in json) {
			window.alert("You must log in with a google account first!")
			return false
		}
		return true
	})
	window.alert(result.toString())
	return result
}

function handleCredentialResponse(data) {
	console.log(data)
	userID = data['credential'];
	fetchUserDetails(data['credential'])
}

function fetchUserDetails(cred)
{
	const Http = new XMLHttpRequest();
	const url = 'https://oauth2.googleapis.com/tokeninfo?id_token=' + cred;
	Http.open("GET", url);
	Http.send();
	
	Http.onreadystatechange = (e) => {
		if(Http.readyState === XMLHttpRequest.DONE) {
			console.log(Http.responseText)
			const parsedResponse = JSON.parse(Http.responseText);
			window.alert('User ' + parsedResponse['given_name'] + " " + parsedResponse['family_name'] + ' just signed in')
		}
	}
}
