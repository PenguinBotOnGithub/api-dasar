package com.example.apidasar;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;

public class ResponsePopularMovie{

	@SerializedName("page")
	private int page;

	@SerializedName("results")
	private ArrayList<Movie> results;

	public int getPage(){
		return page;
	}

	public ArrayList<Movie> getResults(){
		return results;
	}
}