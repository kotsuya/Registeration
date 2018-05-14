package com.example.yoo.Registeration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {

    /*
        // Emulator Test
        $ npm install ngrok -g
        $ ngrok http 8080
    */

    final static  private String URL = "http://bcb9c240.ngrok.io/ScheduleDelete.php";
    private Map<String, String> parameters;

    public DeleteRequest(String userID, int courseID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID+"");

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}