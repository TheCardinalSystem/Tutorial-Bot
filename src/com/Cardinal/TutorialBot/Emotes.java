package com.Cardinal.TutorialBot;

public enum Emotes {

	HEAVY_CHECK("\u2705");
	
	private String name;
	private Emotes(String name) {
		this.name = name;
	}
	
	public String toUnicode() {
		return name;
	}
}
