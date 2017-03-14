app.controller('EventController', function($scope, $location, EventService){
	console.log('Entered eventController')
	$scope.events;
	$scope.event = {
			id : '', name : '', description : '', venue : '', date_time : '', date4 : '' 
	};
	
	$scope.message;
	$scope.registerEvent = function(){
		console.log('Entered Function to save in Event controller')
	    EventService.registerEvent($scope.event).then(function(response){
	    	console.log("Event Registeration success" + response.status)
	    $location.path("/home");   
	    }); 	
	}
	
	function getAllEvents() {
		console.log("Entered getAllEvents in controller")
		EventService.getAllEvents().then(function(response) {
			console.log("List events successful"+response.status)
			$scope.events = response
		})
	}
	getAllEvents()
})