package com.project.pan.myproject.requestFramework;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: panrongfu
 * @date: 2018/10/8 16:12
 * @describe:
 */

public interface GitHubService {

    @GET("users/{user}/repos")
    Call<Object> listRepos(@Path("user") String user);
}
