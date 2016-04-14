package com.example.ray.jachegou.DAOS;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ray on 26/03/2016.
 */
public class Teste {
    public String teste(RequestQueue requestQueue,String login,String senha){

        JSONObject params = new JSONObject();

        try {
            params.put("username", login);
            params.put("password", senha);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String URL="http://192.168.1.103/wwww/JaChegouPedido/webservice/ray.php";
        //create future request object
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        //create JsonObjectRequest
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL, params, future, future);
        //add request to volley
        requestQueue.add(jsObjRequest);
        //pop request off when needed
        try {
            JSONObject response = future.get();//blocking code
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
