## Please see full Node.js, Express, React Native project [here](https://github.com/adingeist/niblet-app).

# SpringAuthBackendAPI
A retired version of the authentication API for Niblet. It utilizes Spring Boot to handle requests and PostgreSQL for persistent storage. Niblet's backend is currently managed with Node.js, Express, and MongoDB.

# Purpose of this project
This code was originally used in the social networking application, Niblet. There's nothing wrong with it. It actually works phenomenally. However, Node.js proved to be more suitable for the project, instead of Java and Spring Boot. I hope by sharig this code, it can help someone trying to build an authentication API and learn about 3-tier architecture.

# What can it do?
The server listens for CRUD HTTP requests. Each endpoint will return a JSON object or accept one to manipulate the PostgreSQL database. Users will create an account by sending a POST request. The data will be validated and ensure no duplicate entries exist in the database. Next, a 6-digit secure-random generated verification token is   sent to the user's email or phone number provided. Emails are sent using the Gmail API. SMS are handled with the Twilio API.

# Video Overview
[![Niblet Spring API Overview](https://img.youtube.com/vi/qc8fa0i_WGY/0.jpg)](https://www.youtube.com/watch?v=qc8fa0i_WGY)
