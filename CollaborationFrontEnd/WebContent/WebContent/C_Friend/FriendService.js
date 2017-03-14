app.factory('FriendService', function($http){
	console.log("Excecuted UserServices")
	var BASE_URL = "http://localhost:8081/CollaborationBackend/"
		var friendService = this;
	
	friendService.getAllfriends = function(){
		console.log("Getting myfriend");
		return $http.get(BASE_URL+"myFriends");
	}
	
	friendService.getPendingRequest = function(){
		console.log("getting Pending Request");
		return $http.get(BASE_URL + "viewPendingRequest");
	}
	
	friendService.acceptRequest = function(UserID){
		console.log("Accept Request");
		return $http.get(BASE_URL + "acceptRequest"+UserID)
		.then
		(
				function(response){
			return response.data;
	},function(errResponse){
		console.log("Error Adding Friend");
		return $q.reject(errResponse);
		});
	
	}
	friendService.rejectRequest = function(userID)
	{
		console.log("Rejecting Request");
		return $http.get(BASE_URL + "rejectFriend-"+userID)
		.then
		(
			function(response)
			{
				return response.data;
			},
			function(errResponse)
			{
				console.log("Error Adding Friend");
				return $q.reject(errResponse);
			}
		);
	}
	
	return friendService;

})