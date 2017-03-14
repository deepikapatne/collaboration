package com.niit.collaboration.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.model.Friend;
import com.niit.collaboration.model.User;
import com.niit.collaboration.model.Friend;

@RestController
public class FriendController {

	private static final Logger log = LoggerFactory.getLogger(FriendController.class);

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	Friend friend;
	@Autowired
	HttpSession session;
	
	@Autowired
	private UserDAO userDAO;

    private boolean isUserExist(String id)
    {
    	if(userDAO.getUser(id)== null)
    		return false;
    	else
    		return true;
    }
    
    @GetMapping("/sendRequest-{id}")
    public ResponseEntity<Friend>addFriend(@PathVariable("id") String friendID){
    	if(session.getAttribute("username")== null)
    	{
    		log.info("Not log in");
    		friend  = new Friend();
    		friend.setErrorCode("100");
    		friend.setErrorMsg("please Login");
    		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    	}
    	
    	boolean check = isUserExist(friendID);
    	if(check)
    	{
    		log.info("Adding Friend");
    		friend.setUserID(session.getAttribute("username").toString());
    		friend.setFriendID(friendID);
    		friend.setStatus('P');
    		boolean value = friendDAO.save(friend);
    		if(value == true)
    		{
    			friend.setErrorCode("200");
    			friend.setErrorMsg("Request send");
    			log.info("Request send");
    		}else{
    			friend.setErrorCode("400");
    			friend.setErrorMsg("Request not send");
    			log.error("Request not send");
    		}
    		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    	}else{
    		log.info("Friend Id is not in Db");
    		friend = new Friend();
    		friend.setErrorCode("100");
    		friend.setErrorMsg("Friend not found");
    		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    	}
    }

    @GetMapping("/myFriends")
    public ResponseEntity<List<String>> getMyFriends(){
    	List<String> list = new ArrayList<String>();
    	if(session.getAttribute("username")== null)
    	{
    		log.info("Not log in ");
    		return null;
    	}else{
    		list = friendDAO.getFriendList(session.getAttribute("username").toString());
    		log.info("List Retrived");
    		if(list.isEmpty() || list == null)
    		{
    			log.info("List is Empty");
    			list.add("You have no friends");
    			return null;
    		}else
    			return new ResponseEntity<List<String>>(list, HttpStatus.OK);
    		
    	}
    }
    
    @GetMapping("/acceptRequest-{id}")
    public ResponseEntity<Friend> acceptFriend(@PathVariable("id") String friendID){
    	if(session.getAttribute("username")== null)
    	{
    		log.info("Not log in");
    		friend = new Friend();
    		friend.setErrorCode("100");
    		friend.setErrorMsg("Please Login");
    		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    	}else{
    		log.info("Accepting Request");
    		String userID = session.getAttribute("username").toString();
    		boolean value =  friendDAO.accept(userID, friendID);
    		if(value)
    		{
    			friend.setUserID(userID);
    			friend.setFriendID(friendID);
    			friend.setErrorCode("200");
    			friend.setErrorMsg("Freiend request accepted");
    			log.info("requst accepted");
    		}else{
    			friend.setErrorCode("404");
    			friend.setErrorMsg("Friend Request not accepted");
    			log.error("Request not accepted");
    		}
    		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    	}
    }
    
    @GetMapping("/rejectFriend-{id}")
    public ResponseEntity<Friend> rejectFriend(@PathVariable("id") String friendID)
    {
    	friend = new Friend();
    	if(session.getAttribute("username")== null)
    	{
    		log.info("NOT LOGGED IN");
			friend.setErrorCode("100");
			friend.setErrorMsg("Please Login");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    	}else{
    		try{
    			String userID = session.getAttribute("username").toString();
    			friend.setUserID(userID);
    			friend.setFriendID(friendID);
    			friendDAO.reject(userID, friendID);
    			log.info("Friend Request reject");
    			friend.setErrorCode("200");
				friend.setErrorMsg("Friend Request Rejected Success");
			}	catch(Exception e)
			{
				friend.setErrorCode("404");
				friend.setErrorMsg("Could not reject Request");
				e.printStackTrace();
			}
    	}
    	return new ResponseEntity<Friend>(friend,HttpStatus.OK);
    }
    
    @GetMapping("/cancelRequest-{id}")
    public ResponseEntity<Friend> cancelRequest(@PathVariable("id") String friendID){
    	friend = new Friend();
    	if(session.getAttribute("username")== null)
    	{
    		log.info("NOT LOGGED IN");
			friend.setErrorCode("100");
			friend.setErrorMsg("Please Login");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    	}else{
    		try{
    			String userID = session.getAttribute("username").toString();
    			friend.setUserID(userID);
    			friend.setFriendID(friendID);
    			friendDAO.cancel(userID, friendID);
    			log.info("Friend Request Cancel");
    			friend.setErrorCode("200");
    			friend.setErrorMsg("Request Cancel");
    		}catch(Exception e){
    			friend.setErrorCode("404");
    			friend.setErrorMsg("Not cancel request");
    			e.printStackTrace();
    		}
    	}
    	return new ResponseEntity<Friend>(friend, HttpStatus.OK);
    }
    
    @GetMapping("//viewPendingRequest")
    public ResponseEntity<List<Friend>> getPendingRequest()
    {
    	List<Friend> list = new ArrayList<Friend>();
    	if(session.getAttribute("username")== null)
    	{
    		log.info("NOT LOGGED IN");
			friend = new Friend();
			friend.setErrorCode("404");
			friend.setErrorMsg("You should LogIn");
			return null;
    	}else{
    		log.info("Getting pending request" +session.getAttribute("username").toString());
    		String userID = session.getAttribute("username").toString();
    		list = friendDAO.showPendingRequests(userID);
    		log.info("pending Request received");
    		if(list.isEmpty() || list==null)
    		{
    			friend.setUserID(userID);  //null
    			friend.setErrorCode("200");
    			friend.setErrorMsg("You have pending Request");
    			list.add(friend);
    			return null;
    		}
    	}
    	return new ResponseEntity<List<Friend>>(list, HttpStatus.OK);
    }
    
    @GetMapping("/viewSentRequest")
    public ResponseEntity<List<Friend>> getSentRequest()
    {
    	List<Friend> list = new ArrayList<Friend>();
    	friend = new Friend();
    	if(session.getAttribute("username")== null)
    	{
    		log.info("NOT LOGGED IN");
			friend.setErrorCode("404");
			friend.setErrorMsg("You should LogIn");
			return null;
    	}
    	else
		{
			log.info("Getting Sent Request for "+session.getAttribute("username").toString());
			String userID = session.getAttribute("username").toString();
			list = friendDAO.viewSentRequests(userID);
			log.info("Sent Requests recieved");
			if(list.isEmpty() || list==null)
			{
				friend.setUserID(userID);
				friend.setErrorMsg("200");
				friend.setErrorMsg("You have no Pending Request");
				return null;
			}
		}
		return new ResponseEntity<List<Friend>>(list, HttpStatus.OK);
	
    }
    
    
    
    
  
}
