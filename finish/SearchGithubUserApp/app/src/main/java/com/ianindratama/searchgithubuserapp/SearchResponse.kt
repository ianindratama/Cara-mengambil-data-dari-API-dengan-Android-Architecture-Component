package com.ianindratama.searchgithubuserapp

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("items")
	val items: List<GithubUserData>

)

data class GithubUserData(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("html_url")
	val url: String

)