package com.example.apidasar;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie {

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private Object voteAverage;

	@SerializedName("overview")
	private String overview;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("genres")
	private ArrayList<Genre> Genres;

	class Genre {
		int id;
		String name;

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("poster_path")
	private String posterPath;

	public String getReleaseDate(){
		return releaseDate;
	}

	public Object getVoteAverage(){
		return voteAverage;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public ArrayList<Genre> getGenres() {
		return Genres;
	}

	public String getOverview() {
		return overview;
	}
}