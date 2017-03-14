app.controller('FriendController',function($scope,$location,FriendService){
	
	$scope.friends = [];
	$scope.pendingRequest=[];
	$scope.sentRequest=[];
	
	$scope.friends= FriendService.getAllFriends()
	.then(
			function(response){
				console.log("Executing Get all friend")
				console.log(response.status)
				$scope.friends = response.data;
			},function(errResponse){
				console.log("Error getting friend List")
				console.log(errResponse.data)
			})
			
	$scope.pendingRequest = FriendService.getPendingRequest()
	.then(
			function(response){
				console.log("Excecuting Pending request")
				console.log(response.status)
				$scope.pendingRequest = response.data;
			},function(errResponse){
				console.log("Error getting frienf list")
				console.log(errResponse.data)
			})
			
	$scope.sentRequest = FriendService.getSentRequest()
			.then(
					function(response)
					{
						console.log("Executing Get Sent Request")
						console.log(response.status)
						$scope.pendingRequest = response.data;
					}, function(errResponse)
					{
						console.log("Error getting sent friend list")
						console.log(errResponse.data)
					})		
					
					
	$scope.acceptRequest = function(userID){
		FriendService.acceptRequest(userId)
		.then(
				function(response){
					console.log("Excecuting AccepT Request"+ userID)
					console.log(response.status)
					alert('Friend Accepted')
					$location.path("myFriends")
				},function(errResponse){
					console.log("Error accepting friend")
					console.log(errResponse.data)
					$location.path("/")
				})
	}			
	
	$scope.rejectRequest = function(userID){
		FriendService.rejectRequest(userID)
		.then(
				function(response){
					console.log("Entering Reject Request"+userID)
					console.log(response.status)
					alert('Friend Reject')
					$location.path("/myFriends")
				},function(errResponse){
					console.log("Error reject friend")
					console.log(errResponse.data)
					$location.path("/")
				})
	}
					
})