var BASE_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=f6d04af6ab78e991990e4ea3e297bbf5&";
	var UrlParams = "&units=metric&type=accurate&mode=json&lang=sp";
	var Forecast_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?APPID=f6d04af6ab78e991990e4ea3e297bbf5&";
	var ForeCast_Params = "&cnt=5&units=metric&type=accurate&mode=json&lang=sp";
	var IMG_URL = "http://openweathermap.org/img/w/";
	var geocoder = new google.maps.Geocoder();
	var source, destination;

	/*function getWeatherLocation(source, destination) {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(GetCurrentWeather,
					CurrentLocationFailure, {
						enableHighAccuracy : true,
						timeout : Infinity,
						maximumAge : 0
					});
		} else {
			alert("Geolocation no es soportada por el explorador.");
		}
	}*/
	
	function GetCurrentWeather(sourceDir, destinationDir) {
		
		//**********POSICIONES DEL RECORRIDO****************///
		geocoder.geocode({'address': sourceDir}, function(results, status) {
			if (status === google.maps.GeocoderStatus.OK) {
			  source = results[0].geometry.location;
			  GetWeatherSource(source);
			} else {
			  alert('Geocode no fue exitoso por la siguiente razón: ' + status);
			}
		});
		
		geocoder.geocode({'address': destinationDir}, function(results, status) {
			if (status === google.maps.GeocoderStatus.OK) {
			  destination= results[0].geometry.location;
			  GetWeatherDestination(destination);
			} else {
			  alert('Geocode no fue exitoso por la siguiente razón: ' + status);
			}
		});
	}
	
	function GetWeatherSource(source){
		// Configura las URL para obtener el clima actual en el origen
		var SourceCurrentWeather_url = BASE_URL + "lat=" + source.lat() + "&lon=" + source.lng() + UrlParams;
		
		// Realiza la llamada al API de OpenWeather para obtener el clima actual en el origen
		var SourceCurrentReq = new XMLHttpRequest();
		SourceCurrentReq.onreadystatechange = function() {
			if (SourceCurrentReq.readyState == 4 && SourceCurrentReq.status == 200) {
				var JSONobj = JSON.parse(SourceCurrentReq.responseText);
				ParseSource(JSONobj);
			}
		}
		SourceCurrentReq.open("GET", SourceCurrentWeather_url, true);
		SourceCurrentReq.send();
	}
	
	function GetWeatherDestination(destination){
		// Configura las URL para obtener el clima actual en el destino
		var DestinationCurrentWeather_url = BASE_URL + "lat=" + destination.lat() + "&lon=" + destination.lng() + UrlParams;
		
		// Realiza la llamada al API de OpenWeather para obtener el clima actual en el destino
		var DestinationCurrentReq = new XMLHttpRequest();
		DestinationCurrentReq.onreadystatechange = function() {
			if (DestinationCurrentReq.readyState == 4 && DestinationCurrentReq.status == 200) {
				var JSONobj = JSON.parse(DestinationCurrentReq.responseText);
				ParseDestination(JSONobj);
			}
		}
		DestinationCurrentReq.open("GET", DestinationCurrentWeather_url, true);
		DestinationCurrentReq.send();
		
	}
	
	function ParseSource(obj) {
		// Clima actual en el origen
		/*document.getElementById("dvWeatherSource").innerHTML = "Clima Origen <br>" + 
				"<img src='" + IMG_URL + obj.weather[0].icon + ".png'> <br> " + 
				"Condici&#243;n: " + obj.weather[0].description + "<br>" + 
				"Temperatura: " + obj.main.temp + " C&#176;<br>" + 
				"Humedad: " + obj.main.humidity + " % <br>" +
				"Nubosidad: " + obj.clouds.all + "% <br>" + 
				"Viento: " + obj.wind.speed + " m/s <br>";*/
		document.getElementById("imgClimaOrigen").src=IMG_URL + obj.weather[0].icon + ".png";
		
		document.getElementById("txtClimaCondicionOrigenVal").value=obj.weather[0].description;
		document.getElementById("txtClimaTemperaturaOrigenVal").value=parseFloat(obj.main.temp).toFixed(2);
		document.getElementById("txtClimaHumedadOrigenVal").value=parseFloat(obj.main.humidity).toFixed(2);
		document.getElementById("txtClimaNubosidadOrigenVal").value=parseFloat(obj.clouds.all).toFixed(2);
		document.getElementById("txtClimaVientoOrigenVal").value=parseFloat(obj.wind.speed).toFixed(2);	

	}
	
	function ParseDestination(obj) {
		// Clima actual en el origen
		/*document.getElementById("dvWeatherDestination").innerHTML = "Clima Destino <br>" + 
				"<img src='" + IMG_URL + obj.weather[0].icon + ".png'> <br> " + 
				"Condici&#243;n: " + obj.weather[0].description + "<br>" + 
				"Temperatura: " + obj.main.temp + " C&#176;<br>" + 
				"Humedad: " + obj.main.humidity + " % <br>" +
				"Nubosidad: " + obj.clouds.all + "% <br>" + 
				"Viento: " + obj.wind.speed + " m/s <br>";*/
		document.getElementById("imgClimaDestino").src=IMG_URL + obj.weather[0].icon + ".png";
		document.getElementById("txtClimaCondicionDestinoVal").value=obj.weather[0].description;
		document.getElementById("txtClimaTemperaturaDestinoVal").value=parseFloat(obj.main.temp).toFixed(2);
		document.getElementById("txtClimaHumedadDestinoVal").value=parseFloat(obj.main.humidity).toFixed(2);
		document.getElementById("txtClimaNubosidadDestinoVal").value=parseFloat(obj.clouds.all).toFixed(2);
		document.getElementById("txtClimaVientoDestinoVal").value=parseFloat(obj.wind.speed).toFixed(2);

	}