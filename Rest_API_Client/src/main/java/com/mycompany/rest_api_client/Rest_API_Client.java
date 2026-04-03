/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rest_api_client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

public class Rest_API_Client {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        String city = args.length > 0 ? String.join(" ", args) : "Raigarh";

        try {
            Optional<Location> location = geocodeCity(city);
            if (location.isEmpty()) {
                System.out.println("City not found: " + city);
                System.exit(1);
            }

            WeatherData weatherData = fetchWeather(location.get());
            printStructured(weatherData);
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to fetch weather data: " + e.getMessage());
            System.exit(1);
        }
    }

    private static Optional<Location> geocodeCity(String city) throws IOException, InterruptedException {
        String encoded = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + encoded
                + "&count=1&language=en&format=json";

        HttpResponse<String> response = sendGet(url);
        if (response.statusCode() != 200) {
            return Optional.empty();
        }

        JsonNode root = MAPPER.readTree(response.body());
        JsonNode results = root.path("results");
        if (!results.isArray() || results.isEmpty()) {
            return Optional.empty();
        }

        JsonNode first = results.get(0);
        String name = first.path("name").asText();
        String country = first.path("country").asText();
        double latitude = first.path("latitude").asDouble();
        double longitude = first.path("longitude").asDouble();

        return Optional.of(new Location(name, country, latitude, longitude));
    }

    private static WeatherData fetchWeather(Location location) throws IOException, InterruptedException {
        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + location.latitude
                + "&longitude=" + location.longitude
                + "&current=temperature_2m,relative_humidity_2m,apparent_temperature,wind_speed_10m"
                + "&timezone=auto";

        HttpResponse<String> response = sendGet(url);
        if (response.statusCode() != 200) {
            throw new IOException("Weather API error: " + response.statusCode());
        }

        JsonNode root = MAPPER.readTree(response.body());
        JsonNode current = root.path("current");
        JsonNode units = root.path("current_units");

        return new WeatherData(
                location.name,
                location.country,
                current.path("time").asText(),
                current.path("temperature_2m").asDouble(),
                units.path("temperature_2m").asText(),
                current.path("apparent_temperature").asDouble(),
                units.path("apparent_temperature").asText(),
                current.path("relative_humidity_2m").asInt(),
                units.path("relative_humidity_2m").asText(),
                current.path("wind_speed_10m").asDouble(),
                units.path("wind_speed_10m").asText()
        );
    }

    private static HttpResponse<String> sendGet(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void printStructured(WeatherData data) {
        String line = "+-----------------------+-----------------------------+";
        System.out.println(line);
        System.out.printf("| %-21s | %-27s |%n", "Location", data.city + ", " + data.country);
        System.out.println(line);
        System.out.printf("| %-21s | %-27s |%n", "Observation Time", data.time);
        System.out.printf("| %-21s | %-27s |%n", "Temperature", data.temperature + " " + data.temperatureUnit);
        System.out.printf("| %-21s | %-27s |%n", "Feels Like", data.apparentTemperature + " " + data.apparentTemperatureUnit);
        System.out.printf("| %-21s | %-27s |%n", "Humidity", data.humidity + " " + data.humidityUnit);
        System.out.printf("| %-21s | %-27s |%n", "Wind Speed", data.windSpeed + " " + data.windSpeedUnit);
        System.out.println(line);
    }

    private static class Location {
        final String name;
        final String country;
        final double latitude;
        final double longitude;

        Location(String name, String country, double latitude, double longitude) {
            this.name = name;
            this.country = country;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    private static class WeatherData {
        final String city;
        final String country;
        final String time;
        final double temperature;
        final String temperatureUnit;
        final double apparentTemperature;
        final String apparentTemperatureUnit;
        final int humidity;
        final String humidityUnit;
        final double windSpeed;
        final String windSpeedUnit;

        WeatherData(String city,
                    String country,
                    String time,
                    double temperature,
                    String temperatureUnit,
                    double apparentTemperature,
                    String apparentTemperatureUnit,
                    int humidity,
                    String humidityUnit,
                    double windSpeed,
                    String windSpeedUnit) {
            this.city = city;
            this.country = country;
            this.time = time;
            this.temperature = temperature;
            this.temperatureUnit = temperatureUnit;
            this.apparentTemperature = apparentTemperature;
            this.apparentTemperatureUnit = apparentTemperatureUnit;
            this.humidity = humidity;
            this.humidityUnit = humidityUnit;
            this.windSpeed = windSpeed;
            this.windSpeedUnit = windSpeedUnit;
        }
    }
}
