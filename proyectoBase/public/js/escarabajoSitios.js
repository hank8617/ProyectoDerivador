var map, currentPositionMarker, lat,long,nuevaUbic;

google.maps.event.addDomListener(window, 'load', function () {
                      
           if(nuevaUbic){
        	   lat = 4.598889;
        	   long = -74.080833; 
           }
           
           var bogota = bogota = new google.maps.LatLng(lat, long);
           
           var mapOptions = {
               zoom: 12,
               center: bogota,
               mapTypeId: google.maps.MapTypeId.ROADMAP
           };
           map = new google.maps.Map(document.getElementById('dvMap'), mapOptions);
           
           currentPositionMarker = new google.maps.Marker({
				position: bogota,
				map: map,
				draggable:true,
				title:"Arrastrame!"
				});  
           
           currentPositionMarker.setPosition(
       			new google.maps.LatLng(
       					lat,
       					long)
       			);
           
           google.maps.event.addListener(currentPositionMarker, 'dragend', function (event) {
        	   document.getElementById("latitud").value = event.latLng.lat();
        	   document.getElementById("longitud").value = event.latLng.lng();
        	   //alert(event.latLng.lat() + " " + event.latLng.lng());
        	   });          

       });

function asignarUbicacion(latitud,longitud,nuevaUbicacion){
	lat = latitud;
	long = longitud;
	nuevaUbic = nuevaUbicacion;
}


