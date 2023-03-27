package com.example.apidasar;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie {

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("overview")
	private String overview;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("genres")
	private ArrayList<Genre> genres;

	class Genre {
		private int id;
		private String name;

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

	@SerializedName("backdrop_path")
	private String backdropPath;

	public String getReleaseDate(){
		return releaseDate;
	}

	public double getVoteAverage(){
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
		return genres;
	}

	public String getOverview() {
		return overview;
	}

	public String getBackdropPath() {
		return backdropPath;
	}
}