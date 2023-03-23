package com.tcc.joaomyrlla.appcode2know.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class JuizOnlinePython {
  public static final String URL_POST = "http://localhost:5000/submissao_caso_teste";

  HttpClient client = HttpClient.newHttpClient();

  Gson gson = new Gson();


  public HashMap<String, Object> realizaSubmissao(HashMap<String, Object> req) throws IOException, InterruptedException {
    String jsonRequest = gson.toJson(req, req.getClass());

    HttpRequest request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
            .uri(URI.create(URL_POST))
            .header("Content-Type", "application/json")
            .build();

    HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
    HashMap<String, Object> json = gson.fromJson(response.body(), HashMap.class);

    return json;
  }
}
