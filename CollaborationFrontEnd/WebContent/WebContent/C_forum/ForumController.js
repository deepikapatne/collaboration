app.controller('ForumController', function($scope, $location,ForumService){
	console.log('Entered ForumController')
	$scope.forums;
	$scope.forum = {
			id : '', name : '', description : '', username : '', status : '', forum_date : '',date3 : '', 
	};
	
	$scope.message;
	$scope.registerForum = function(){
		console.log('Excecuted Save ')
		ForumService.registerForum($scope.forum).then(function(response){
			console.log("Forum Registration success" + response.status)
		$location.path("/home");
		});
	}
	
	function getAllForums(){
		console.log("Entered getallForums")
		ForumService.getAllForums().then(function(response){
			console.log(response.status)
		    console.log(response.data)
		    $scope.forums = response.data
		})
	}
	getAllForums()
});