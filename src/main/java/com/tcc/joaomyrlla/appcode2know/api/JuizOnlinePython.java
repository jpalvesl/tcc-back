package com.tcc.joaomyrlla.appcode2know.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class JuizOnlinePython {
  public static final String URL_POST = "http://localhost:5000/submissao_caso_teste";

  HttpClient client = HttpClient.newHttpClient();

  Gson gson = new Gson();

  public HashMap<String, Object> realizaSubmissao() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                    "\t\"codigo_resposta\": \"r = float(input())\\npi = 3.14159\\n\\narea = pi * r**2\\nprint('A={:.4f}'.format(area))\",\n" +
                    "\t\"entradas\": \"2\\n\"\n" +
                    "}"))
            .uri(URI.create(URL_POST))
            .header("Content-Type", "application/json")
            .build();

    HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
    HashMap<String, Object> json = gson.fromJson(response.body(), HashMap.class);

    return json;
  }
}
