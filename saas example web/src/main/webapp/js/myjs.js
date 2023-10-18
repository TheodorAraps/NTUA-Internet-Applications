let userID = "EMPTY"

function action() {
	if(checkLogin() == false) {
		window.alert("user must login")
	}
	else {
		window.alert("user is logged in")
	}
}

async function checkLogin() {
	let result;
	const url = 'https://oauth2.googleapis.com/tokeninfo?id_token=' + userID;
	result = await fetch(url).then(response => response.json()).then(json => {
		if ("error" in json) {
			window.alert("user is not logged in ------")
			return false
		}
		return true
	})
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
	const url='https://oauth2.googleapis.com/tokeninfo?id_token=' + cred;
	Http.open("GET", url);
	Http.send();
	
	Http.onreadystatechange = (e) => {
		if(Http.readyState === XMLHttpRequest.DONE) {
			console.log(Http.responseText)
			const parsedResponse = JSON.parse(Http.responseText);
			//window.alert(Http.responseText);
			window.alert('User ssssssssssss' + parsedResponse['given_name'] + " " + parsedResponse['family_name'] + ' just signed in')
		}
	}
}