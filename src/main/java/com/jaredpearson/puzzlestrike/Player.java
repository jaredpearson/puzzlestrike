package com.jaredpearson.puzzlestrike;

public class Player {
	private Integer id;
	private String name;
	
	public Player(){
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
