# Rest_API_Client

Company: CODTECH IT SOLUTIONS

Name: Akash Kumar

Intern ID : CTIS7110

Domain: Java Programming

Duration: 4 Weeks

Mentor: Neela Santosh

**Project Description:**

This project is a lightweight and efficient Java-based REST API client designed to fetch real-time weather data using external web services. It demonstrates how to integrate third-party APIs, process JSON responses, and present structured output in a clean and readable format. The application leverages modern Java features such as the java.net.http.HttpClient API and the Jackson library for JSON parsing.

🚀 **Overview**

The application takes a city name as input (via command-line arguments or default value) and performs a two-step process:

* **Geocoding** – Converts the city name into geographic coordinates (latitude and longitude).
*** Weather Fetching** – Uses these coordinates to retrieve current weather data from a public weather API.

The final output is displayed in a well-formatted table, providing key weather metrics such as temperature, humidity, wind speed, and perceived temperature.

🔑 **Key Features**

**1. City-to-Coordinates Conversion**

The application integrates with a geocoding API to dynamically resolve city names into precise geographic coordinates. This ensures accurate weather data retrieval for any valid location worldwide.

**2. Real-Time Weather Data Retrieval**

Weather data is fetched using a REST API, providing up-to-date information including:
* Temperature
* Apparent (feels-like) temperature
* Relative humidity
* Wind speed
* Observation time
  
**3. Modern HTTP Client Usage**

Built using Java’s HttpClient, the project demonstrates asynchronous-ready, efficient HTTP communication with configurable timeouts for reliability.

**4. JSON Parsing with Jackson**

The application uses ObjectMapper and JsonNode from the Jackson library to parse complex JSON responses into structured Java objects.

**5. Error Handling & Resilience**

Robust error handling is implemented to manage:
* Invalid city names
* API failures
* Network issues
The use of Optional ensures safe handling of missing or invalid data.

**6. Clean Console Output**

Weather information is displayed in a structured tabular format, improving readability and user experience in the command-line interface.

🛠️ **Technical Highlights**

* Uses java.net.http.HttpClient for REST communication
* Implements URLEncoder for safe query parameter encoding
* Parses JSON using Jackson (com.fasterxml.jackson.databind)
* Utilizes Duration for request timeout configuration
* Follows modular design with separation of concerns:
    - Location class for geocoding results
    - WeatherData class for structured weather information
* Employs immutable data structures for better reliability
  
📚 **Learning Outcomes**

This project helps developers understand:

* How to consume REST APIs in Java
* Handling HTTP requests and responses
* JSON parsing and data extraction
* Clean code structuring with helper methods and classes
* Error handling in network-based applications
  
✅ **Use Cases**

* CLI-based weather applications
* Learning project for REST API integration
* Base template for building advanced weather dashboards
* Educational reference for Java networking and JSON processing

**Output:**

<img width="550" height="300" alt="Screenshot 2026-04-03 122123" src="https://github.com/user-attachments/assets/884fa938-4fbc-412c-952f-6769a1aa04e7" />
