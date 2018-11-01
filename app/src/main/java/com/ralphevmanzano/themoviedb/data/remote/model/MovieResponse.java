package com.ralphevmanzano.themoviedb.data.remote.model;

import com.ralphevmanzano.themoviedb.data.local.entity.Movie;
import com.squareup.moshi.Json;

import java.util.Date;
import java.util.List;

public class MovieResponse {
	
	@Json(name = "page")
	private Integer     page;
	@Json(name = "total_results")
	private Integer     totalResults;
	@Json(name = "total_pages")
	private Integer     totalPages;
	@Json(name = "results")
	private List<Movie> movies = null;
	
	private Date lastRefresh;
	
	public MovieResponse() {
	}
	
	public MovieResponse(Integer page, Integer totalResults, Integer totalPages, List<Movie> movies, Date lastRefresh) {
		super();
		this.page = page;
		this.totalResults = totalResults;
		this.totalPages = totalPages;
		this.movies = movies;
		this.lastRefresh = lastRefresh;
	}
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getTotalResults() {
		return totalResults;
	}
	
	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}
	
	public Integer getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
	
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	public Date getLastRefresh() {
		return lastRefresh;
	}
	
	public void setLastRefresh(Date lastRefresh) {
		this.lastRefresh = lastRefresh;
	}
}