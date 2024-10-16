package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GitHubRepoFetcher {
    private static final String BASE_URL = "https://api.github.com/users/";

    public static void main(String[] args){
        String userName = "AshishKempwad";
        List<String> repositories = fetchRepositories(userName);
        System.out.println("Repositories of user " + userName + ":");
        for(String repo : repositories){
            System.out.println(repo);
        }
    }

    private static List<String> fetchRepositories(String userName) {
        List<String> repositories = new ArrayList<>();
        int page = 1;
        int perPage = 30; // GitHub's default items per page

        try{
            boolean hasMorePages;
            do{
                String urlString = BASE_URL+userName+"/repos?page="+page+"&per_page="+perPage;
                System.out.println("Fetching page "+page+" of repositories : "+urlString);

                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("accept","application/json");

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while((line = br.readLine())!=null){
                    response.append(line);
                }

                br.close();

                JSONArray jsonArray = new JSONArray(response.toString());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject repo = jsonArray.getJSONObject(i);
                    repositories.add(repo.getString("name"));
                }

                hasMorePages = response.length() == perPage;
                page++;
                conn.disconnect();

            }while(hasMorePages);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return repositories;
    }
}
