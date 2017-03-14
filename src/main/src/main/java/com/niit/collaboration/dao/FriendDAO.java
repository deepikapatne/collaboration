package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Friend;

public interface FriendDAO
{
	
	public List<String> getFriendList(String username);

	public boolean get(String userID, String friendID);
	
	public boolean save(Friend friend);
	public boolean accept(String userID, String friendID);
	
	public boolean reject(String userID, String friendID);
	public boolean cancel(String userID, String friendID);
	
	public List<Friend> showPendingRequests(String userID);
	public List<Friend> viewSentRequests(String username);
		
}