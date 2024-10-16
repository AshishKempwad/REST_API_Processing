package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        //STEP 1 : Get the api url
        String urlString = "https://jsonplaceholder.typicode.com/posts"; // API URL to fetch posts

        try{
            //STEP 2 : Create URL object
            URL url =new URL(urlString);

            //STEP 3 : Open HTTP connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //STEP 4 : Set HTTP method and property for json object
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept","application/json"); // Expecting JSON response

            //STEP 5 : Check if the request was successful (response code 200)
            if(conn.getResponseCode() != 200){
                throw new RuntimeException("HTTP GET Request Failed with Error code : " + conn.getResponseCode());
            }

            //STEP 6 : Read the response from the input stream
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while((line = br.readLine())!=null){
                response.append(line);
            }
            br.close();
            conn.disconnect();

            //STEP 7 : Parse the JSON response
            JSONArray jsonArray = new JSONArray(response.toString());

            //STEP 8 : Process each post
            for(int i=0;i<jsonArray.length();i++){
                JSONObject post = jsonArray.getJSONObject(i);
                int id = post.getInt("id");
                String title = post.getString("title");
                String body = post.getString("body");

                //Print post details
                System.out.println("Post ID : " + id);
                System.out.println("Title : " + title);
                System.out.println("Body :" + body);
                System.out.println("-----------------------------");

            }

        }catch(Exception ex){
            ex.printStackTrace();
        }


    }
}