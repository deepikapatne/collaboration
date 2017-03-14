app.factory('ForumService', function($http){

       console.log('Entered Forum service')
       var BASE_URL = "http://localhost:8081/CollaborationBackend/"
       return{
       registerForum : function(forum){
    	return $http.post(BASE_URL + "addForum/" , forum).then(
    			function(response)  {
    				console.log(response.status)
    				console.log(response.header.location)
    				return response.status
    			} ,function(response){
    				cosole.log(response.status)
    				return response.status
    			});
       
       },
       getAllForums : function(){
    	   console.log('Entering getAllForums in Forum Service')
    	   return $http.get(BASE_URL +  "listForum")
       }
       }

})