package test.com.zarea.googletask.retrofit.restInterface;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;
import test.com.zarea.googletask.model.TaskList;

/**
 * Created by zarea on 2/23/16.
 */
public interface ListsInterface {
//    @Headers("Authorization: OAuthya29.kQIEKGLPJR1Wh1HJwL-IuvjPjpBU6ehvgeSKKYe8eHqNauGUp44ZAuvP6_oM8An8l6MP")
    @GET("/users/@me/lists/%40default")
    void getLists(@QueryMap Map<String, String> queryMap, Callback<TaskList> taskListCallback);
}
