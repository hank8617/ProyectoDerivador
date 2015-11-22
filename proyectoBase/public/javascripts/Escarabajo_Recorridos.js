		var map, currentPositionMarker;
		var source, destination;
		var sourcePosition, destinationPosition;
        var directionsDisplay;
        var directionsService = new google.maps.DirectionsService();
		var geocoder = new google.maps.Geocoder();
		var initTime, finalTime;
        var realSource, realDestination;
        var currentPos;
        var watchPositionID = null;
		
		google.maps.event.addDomListener(window, 'load', function () {
            new google.maps.places.SearchBox(document.getElementById('txtSource'));
            new google.maps.places.SearchBox(document.getElementById('txtDestination'));
            directionsDisplay = new google.maps.DirectionsRenderer({ 'draggable': true });
            GetRoute();
        });

        function GetRoute() {
			//Asigna por defecto Bogotá en el centro del mapa
            var bogota = new google.maps.LatLng(4.598889, -74.080833);
            var mapOptions = {
                zoom: 12,
                center: bogota,
				mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(document.getElementById('dvMap'), mapOptions);
            directionsDisplay.setMap(map);
            directionsDisplay.setPanel(document.getElementById('dvPanel'));
			
			
            //Consulta las direcciones para llegar del origen al destino
            source = document.getElementById("txtSource").value;
            destination = document.getElementById("txtDestination").value;

            var request = {
                origin: source,
                destination: destination,
                travelMode: google.maps.TravelMode.DRIVING
            };
            directionsService.route(request, function (response, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                    directionsDisplay.setDirections(response);
                }
            });

            //Calcula distancia y duración estimados
            var service = new google.maps.DistanceMatrixService();
            service.getDistanceMatrix({
                origins: [source],
                destinations: [destination],
                travelMode: google.maps.TravelMode.DRIVING,
                unitSystem: google.maps.UnitSystem.METRIC,
                avoidHighways: false,
                avoidTolls: false
            }, function (response, status) {
                if (status == google.maps.DistanceMatrixStatus.OK && response.rows[0].elements[0].status != "ZERO_RESULTS") {
                    var distance = response.rows[0].elements[0].distance.value;
                    var duration = response.rows[0].elements[0].duration.value;
                    

                    document.getElementById("txtTiempoEstimadoVal").value = parseFloat(duration/60).toFixed(2);
					document.getElementById("txtDistanciaEstimadaVal").value = parseFloat(distance/1000).toFixed(2);

                } else {
                    alert("No es posible encontrar un camino.");
                }
            });
			//Llama la función de calcular el clima en Escarabajo_Clima.js
			GetCurrentWeather(source, destination);
        }
		
		function StartRoute(){
			//Obtiene la hora de inicio
			initTime = new Date().getTime();
			//Consulta y monitorea la posición del usuario
			if(navigator.geolocation){
				navigator.geolocation.getCurrentPosition(function (p) {
					realSource = new google.maps.LatLng(p.coords.latitude, p.coords.longitude);
					console.log(realSource);
					window.location.hash = '#dvMap';
				});
				var opts = {
					enableHighAccuracy: true,
					timeout			  : 5000,
					maximumAge		  : 0
				}
				if(watchPositionID == null){
					watchPositionID = navigator.geolocation.watchPosition(displayAndWatch, CurrentLocationFailure, opts);	
				}
				
			}
			else {
				alert('Geo Location no es soportada por el explorador.');
			}
			
		}
		
		function EndRoute(){
			//Obtiene la hora de finalización
			finalTime = new Date().getTime();

			if(watchPositionID != null){
				navigator.geolocation.clearWatch(watchPositionID);
				watchPositionID = null;
			}
			//Calcula la distancia con base a la posición final del usuario
			if(navigator.geolocation){
				navigator.geolocation.getCurrentPosition(function (p) {
					realDestination = new google.maps.LatLng(p.coords.latitude, p.coords.longitude);
					console.log(realSource);
					console.log(realDestination);
					console.log(p);
					var realDistance = google.maps.geometry.spherical.computeDistanceBetween(realSource, realDestination);
					var avgSpeed = 60*(realDistance/1000)/((finalTime-initTime)/60000);
					document.getElementById("txtTiempoRealVal").value = parseFloat((finalTime-initTime)/60000).toFixed(2);
					document.getElementById("txtDistanciaRealVal").value = parseFloat(realDistance/1000).toFixed(2);
					document.getElementById("txtVelocidadMediaVal").value = parseFloat(avgSpeed).toFixed(2);
				});
				
			}
			else {
				alert('Geo Location no es soportada por el explorador.');
			}	
			var realDistance = google.maps.geometry.spherical.computeDistanceBetween(realSource, realDestination);
		}

		function setCurrentPosition(pos) {
			//currentPositionMarket.setMap(null);
			if(currentPositionMarker == null){
				currentPositionMarker = new google.maps.Marker({
				map: map,
				position: new google.maps.LatLng(
					pos.coords.latitude,
					pos.coords.longitude
				),
				title: "Current Position"
			});
			}
			else{
				setMarkerPosition(currentPositionMarker, pos);
			}
			console.log(pos.coords.latitude + " " + pos.coords.longitude);
			map.setZoom(20);
			map.panTo(new google.maps.LatLng(
					pos.coords.latitude,
					pos.coords.longitude
				));
		}

		function displayAndWatch(position) {
			setCurrentPosition(position);
			watchCurrentPosition();
		}

		function watchCurrentPosition() {
			var positionTimer = navigator.geolocation.watchPosition(
				function (position) {
					setMarkerPosition(
						currentPositionMarker,
						position
					);
				});
		}

		function setMarkerPosition(marker, position) {
			marker.setPosition(
				new google.maps.LatLng(
					position.coords.latitude,
					position.coords.longitude)
			);
		}

		
		/*function ShowCurrentLocation(p){
			var myLatLng = new google.maps.LatLng(p.coords.latitude, p.coords.longitude);
			
			var mapTemp = document.getElementById("dvMap");
			/*var mapOptions={
				zoom:10,
				center:myLatLng,
				mapTypeId:google.maps.MapTypeId.ROAD
			};
			
			map = new google.maps.Map(mapTemp, mapOptions);
			
			currentPositionMarker.setPosition(myLatLng);
			
			google.maps.event.addListener(currentPositionMarker, "click", function (e) {
				var infoWindow = new google.maps.InfoWindow();
				infoWindow.setContent(currentPositionMarker.title);
				infoWindow.open(mapTemp, currentPositionMarker);
			});
		}*/
		
		function CurrentLocationFailure(error){
				var errorType={
					0:"Error Desconocido",
					1:"Permisos negados por el usuario",
					2:"Ubicación del usuario no disponible",
					3:"Time out"
				};
					 
				var errMsg = errorType[error.code];
					 
				if(error.code == 0 || error.code == 2){
					errMsg = errMsg+" - "+error.message;
				}
		}

		function SaveMetrics(){
			console.log("Entra a SaveMetrics");
			var time = document.getElementById("txtTiempoRealVal").textContent;
			var dist = document.getElementById("txtDistanciaRealVal").textContent;
			window.location.href = "@routes.guardarMetricas/";
			console.log("Finaliza SaveMetrics");
		}