package com.follower.application.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("users")
public class UserController {
	
	public UserController() {

	}
	public static String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	}
	
	@CrossOrigin
	@GetMapping("{id}")
	public String get(@PathVariable Long id) throws Exception {
		String tokenString = "?access_token=dfe25e4f0f906972b1a1db35abdb462ab2405c31";
		int maxDepth = 5;
		int maxLinks = 3;
		JSONArray so = new JSONArray(UserController.getHTML("https://api.github.com/user/" + id + "/followers" + tokenString));	
		
		LinkedList<Object> pendingLinks = new LinkedList<Object>();
		LinkedList<String> linkIds = new LinkedList<String>();
		for(int i = 0; (i < maxLinks) && (i < so.length()); i++ ) {
			pendingLinks.add(so.get(i));
		}
		int currentDepth = 0;
		int remainingCurrentDepth = pendingLinks.size();
		while((!pendingLinks.isEmpty() ) && (currentDepth < maxDepth)) {
			JSONObject currentFollower = new JSONObject(pendingLinks.poll().toString());
			if(!linkIds.contains(currentFollower.get("id").toString())) {
				linkIds.add(currentFollower.get("id").toString());
				if(currentDepth < maxDepth - 1) {
					String otherFollowersString = UserController.getHTML((currentFollower).get("followers_url").toString() + tokenString);
					JSONArray addingLinks = new JSONArray(otherFollowersString);
					for(int i = 0; ((i < maxLinks) && (i < addingLinks.length())); i++ ) {
						pendingLinks.add(addingLinks.get(i));
					}
				}
			}
			remainingCurrentDepth--;
			if(remainingCurrentDepth == 0) {
				currentDepth++;
				remainingCurrentDepth = pendingLinks.size();
			}
		}
		JSONArray returnJSON = new JSONArray(linkIds);
		return returnJSON.toString();
	}
}
