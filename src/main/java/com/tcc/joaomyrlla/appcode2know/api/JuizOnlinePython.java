package com.tcc.joaomyrlla.appcode2know.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class JuizOnlinePython {
  public static final String URL_POST = "http://localhost:5000/submissao_caso_teste";

  HttpClient client = HttpClient.newHttpClient();

  Gson gson = new Gson();


  public HashMap<String, Object> realizaSubmissao(HashMap<String, Object> req) throws IOException, InterruptedException {
    String jsonRequest = gson.toJson(req, req.getClass());

    HttpRequest request = HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(2))
            .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
            .uri(URI.create(URL_POST))
            .header("Content-Type", "application/json")
            .build();

    try {
      HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
      HashMap<String, Object> json = gson.fromJson(response.body(), HashMap.class);

      return json;
    } catch (Exception e) {
      System.out.println("Tempo limite excedido");
    }

    HashMap<String, Object> jsonComTimeout = new HashMap<>();
    jsonComTimeout.put("entrada", "");
    jsonComTimeout.put("saida", "Tempo limite excedido");
    jsonComTimeout.put("status", "Tempo limite excedido");
    jsonComTimeout.put("error", "");
    jsonComTimeout.put("tempo", -1.0);


    return jsonComTimeout;
  }
}
