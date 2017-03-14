package com.niit.collaboration.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.ForumDAO;
import com.niit.collaboration.model.Forum;

@RestController
public class ForumController {
	
	@Autowired
	Forum forum;

	@Autowired
	ForumDAO forumDAO;
	
	@PostMapping("/addForum")
	public ResponseEntity<Forum>addForum(@RequestBody Forum forum){
		forumDAO.save(forum);
		forum.setErrorCode("200");
		forum.setErrorMsg("Success");
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}
	
	@GetMapping("/listForum")
	public ResponseEntity<List<Forum>>getList(){
		List<Forum> list= forumDAO.list();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		for (Forum f : list) {
			String d = df.format(f.getForum_date());
			f.setDate3(d);
		}
		forum.setErrorCode("200");
		forum.setErrorMsg("Success.....");
		return new ResponseEntity<List<Forum>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/delete_forum-{id}")
	public ResponseEntity<Forum> deleteForum(@PathVariable("id") int id){
	forumDAO.delete(id);
	forum.setErrorCode("200");
	forum.setErrorMsg("delete Success");
	return new ResponseEntity<Forum>(forum, HttpStatus.OK);
}
	@PutMapping("/updateForum")
	public ResponseEntity<Forum> editForum(@RequestBody Forum forum){
		forumDAO.update(forum);
		forum.setErrorCode("200");
	    forum.setErrorMsg("Edit Success");
	    return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	
}
