var app = angular.module('Collaboration',['ngRoute', 'ngCookies']);

app.config(function($routeProvider)
	{
	$routeProvider
	
	.when('/home',
		{
			templateUrl : 'views/home.html',
			controller : 'FriendController'
		})
	.when('/login',
	{
		templateUrl : 'views/login.html',
		controller : 'UserController'
	})
		.when('/logout',
	{
		templateUrl : 'views/home.html',
		controller : 'UserController'
	})
		.when('/register',
	{
		templateUrl : 'C_user/register.html',
		controller : 'UserController'
	})
		.when('/viewUsers',
	{
		templateUrl : 'C_user/userList.html',
		controller : 'UserListController'	
	})
		.when('/viewBlogs',
	{
		templateUrl : 'C_blog/blogs.html',
		controller : 'BlogController'	
	})
	   .when('/viewBlog',
			{
		templateUrl : 'C_blog/viewBlog.html',
//		controller : 'BlogController'	
	})
		.when('/viewJobs',
	{
		templateUrl : 'C_job/jobs.html',
		controller : 'JobController'	
	})
	.when('/viewEvents',
			{
				templateUrl : 'C_event/viewEvents.html',
				controller : 'EventController'	
			})
	.when('/Chat',
			{
				templateUrl : 'C_chat/chat.html',
				controller : 'ChatController'	
			})
		.when('/upload',
			{
				templateUrl : 'C_user/fileUpload.html',
//				controller : 'ChatController'	
			})
		.when('/admin',
			{
				templateUrl : 'C_admin/adminHome.html',	
			})	
		.when('/manageBlogs',
			{
				templateUrl : 'C_admin/manageBlogs.html',
				controller : 'AdminController'
			})
		.when('/manageEvents',
			{
				templateUrl : 'C_admin/manageEvents.html',	
				controller : 'AdminController'
			})
			.when('/addEvents',
			{
				templateUrl : 'C_admin/manageEvents.html',	
//				controller : 'AdminController'
			})
		.when('/manageForums',
			{
				templateUrl : 'C_admin/manageForums.html',
				controller : 'AdminController'
			})
		.when('/manageJobs',
			{
				templateUrl : 'C_admin/manageJobs.html',
				controller : 'AdminController'
			})
		.when('/addJobs',
			{
				templateUrl : 'C_admin/manageJobs.html',	
//				controller : 'AdminController'
			})
		.when('/manageUsers',
			{
				templateUrl : 'C_admin/manageUsers.html',	
				controller : 'AdminController'
			})
		.when('/myProfile',
			{
				templateUrl : 'C_user/myProfile.html',	
				controller : 'FriendController'
			})		
		.when('/myFriends',
			{
				templateUrl : 'C_user/myProfile.html',	
				controller : 'FriendController'
			})	
		.when('/pendingRequests',
			{
				templateUrl : 'C_user/myProfile.html',	
				controller : 'FriendController'
			})
		.when('/sentRequests',
			{
				templateUrl : 'C_user/myProfile.html',	
				controller : 'FriendController'
			})
		.when('/cmred',
			{
				templateUrl : 'C_blog/comment-redirect.html',
			})	
	.otherwise({redirectTo: '/'});
});



app.run( function ($rootScope, $location, $cookieStore, $http) 
		{
			 $rootScope.$on('$locationChangeStart', function (event, next, current) 
			 {
				 console.log("$locationChangeStart")
				    
				 var userPages = ['/myProfile','myFriends','pendingRequests','sentRequests','/upload','/viewUsers'];
		         var adminPages = ['/admin','/manageUsers','/manageJobs','/manageEvents','/manageForums','/manageBlogs','/addEvents','/addJobs'];
		 
				 var currentPage = $location.path();
				 
				 var isUserPage = $.inArray(currentPage, userPages);
				 var isAdminPage = $.inArray(currentPage, adminPages);
					 
				 
				 var isLoggedIn = $rootScope.currentUser.username;
			        
			     console.log("isLoggedIn:" +isLoggedIn)
			     console.log("isUserPage:" +isUserPage)
			     console.log("isAdminPage:" +isAdminPage)
			        
			        if(!isLoggedIn)
			        	{
			        	
			        		if(isUserPage!=-1 || isAdminPage!=-1)  
			        	 	{
				        	  console.log("Navigating to login page:")
				        	  alert("You need to Login first!")
				        	  $location.path('/login');
				         	}
			        	}
			        
					 else //logged in
			        	{
			        	
						 var role = $rootScope.currentUser.role;
						 if(isAdminPage!=-1 && role!='ADMIN' )
							 {
							  alert("You cannot view this page as a " + role )
							  $location.path('/');
							 
							 }
			        	}
			 });
			 
			 // keep user logged in after page refresh
		    $rootScope.currentUser = $cookieStore.get('currentUser') || {};
		    if ($rootScope.currentUser)
		    {
		        $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser; 
		    }
		});