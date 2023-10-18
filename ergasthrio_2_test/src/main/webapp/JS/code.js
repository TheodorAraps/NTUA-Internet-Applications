/*******************************************************************************
 * 
 * Javascript code file!
 * 
 ******************************************************************************/

var xmlhttp;
//XMLHttpRequest.onreadystatechange = callback;
function loadDoc(url) {
	xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = onComplete;
	xmlhttp.open("GET", url, true);
	xmlhttp.send(null);
}
//0   UNSENT  open() has not been called yet.
//1   OPENED  send() has been called.
//2   HEADERS_RECEIVED    send() has been called, and headers and status are available.
//3   LOADING Downloading; responseText holds partial data.
//4   DONE    The operation is complete.
function onComplete(event) {
	if (xmlhttp.readyState == 4)
		writeContent(xmlhttp.responseText);
}

function writeContent(data) {
	document.getElementById("content").innerHTML = data;
}

function asyncLoadContent(url) {
	writeContent("Please Wait<br/>Loading...");
	loadDoc(url);
}
function buy_item(){
	var item_type= document.getElementById("type").innerText;
	var item_name= document.getElementById("name").innerText;
	var item_price= document.getElementById("price").innerText;
	alert("You have purchased a "+item_type + " named: " +item_name +" with price "+item_price );
}
function submit_form() {

	var form = document.getElementById("contactForm");
	var ok = true;

	if (form.name.value.length < 2) {
		ok = false;
		alert("Too short name.");
	}
	else if (form.email.value.length < 2) {
		ok = false;
		alert("Too short mail.");
	}
	else if( form.email.value.indexOf('@') < 0 || form.email.value.indexOf('.') < 0 )
	{
		ok = false;
		alert("mail address doesn't comply to RFC-12349856145\nPlease read it and re-type accordingly :)");
	}

	return ok;
}