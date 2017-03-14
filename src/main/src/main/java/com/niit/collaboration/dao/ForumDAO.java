package com.niit.collaboration.dao;


import java.util.List;

import com.niit.collaboration.model.Forum;

public interface ForumDAO
{

	public boolean save(Forum forum);
	
	public boolean delete(int id);
	
	public boolean update(Forum forum);
	
	public List<Forum> list();
}
