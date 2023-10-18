/**
 * 
 */
 
 document.addEventListener("DOMContentLoaded", function(){
	//alert("page loaded");
	
	const but = document.getElementById("button");
	
	but.addEventListener("click", function(){
		var user = document.querySelector("input[name=username]").value;
		//alert(user);
		const SERVICE_URL = "http://localhost:8080/Lab-1-Project-2/Check";
		var url = new URL(SERVICE_URL);
		url.searchParams.set("username", user);
		
		var xhr = new XMLHttpRequest();
		xhr.open("GET", url, true);
		xhr.send(null);
		xhr.onreadystatechange = function() {
			
			if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
				//alert(xhr.responsetext);
				document.getElementById("output").innerHTML = xhr.responseText;
			}
		}
	});
	
	const pass1 = document.querySelector("input[name=password1]");
	const pass2 = document.querySelector("input[name=password2]");
	
	pass2.addEventListener("keyup", function(){
		val1 = pass1.value;
		val2 = pass2.value;
		
		if (val1 == val2) {
			pass2.style.backgroundColor = "green";
		}
		else {
			if (val2 == "") {
				pass2.style.backgroundColor = "white";
			}
			else {
				pass2.style.backgroundColor = "red";			
			}
		}
	});
});