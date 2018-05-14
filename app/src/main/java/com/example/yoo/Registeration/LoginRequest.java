package com.example.yoo.Registeration;


import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.provider.Settings.Global.getInt;
import static android.provider.Settings.Global.getString;

public class LoginRequest extends StringRequest {

    /*
        // Emulator Test
        $ npm install ngrok -g
        $ ngrok http 8080
    */
    final static private String URL = "http://bcb9c240.ngrok.io/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}
