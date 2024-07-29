package br.com.zimbres.StatsFipe.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIConsumption {

    public String getData (String pUrl) {
        HttpClient oClient = HttpClient.newHttpClient();

        HttpRequest oRequest = HttpRequest.newBuilder()
                        .uri(URI.create(pUrl))
                        .build();

        HttpResponse<String> oResponse = null;

        try {
            oResponse = oClient.send(oRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException IoIeE) {
            IoIeE.printStackTrace();
        }

        return oResponse.body();
    }
}
