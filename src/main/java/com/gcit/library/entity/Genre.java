package com.gcit.library.entity;

public class Genre implements BaseEntity{
	private int genreId;
	private String genreName;
	
	
	public Genre(int genreId) {
		super();
		this.genreId = genreId;
	}
	public Genre(String genreName) {
		this.genreName = genreName;
	}
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
}
