/**
 * 
 */
 function on() {
  document.getElementById("overlay").style.display = "block";
}

function off() {
  document.getElementById("overlay").style.display = "none";
}
 
 document.addEventListener("DOMContentLoaded", function(){
	const SERVICE_URL = "http://localhost:8080/Lab-1-HW-1/LabDate";
	var url = new URL(SERVICE_URL);
 	const subs = document.querySelectorAll(".sub");
	subs.forEach((sub, i) => {
	 	sub.addEventListener("click", function(){
	
		 	url.searchParams.set("sub", i+1);
			var xhr = new XMLHttpRequest();
			xhr.open("GET", url, true);
			xhr.send();
			xhr.onreadystatechange = function() {
				
				if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {
					//alert("Date: " + xhr.responseText);
					document.getElementById("overlayText").innerHTML = "Date: " + xhr.responseText;
				}
			}		
		});
	});
 });