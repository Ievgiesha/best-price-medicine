####   Description
"best-price-medicine" is a service to search for the cheapest set of medicines in online pharmacies.
Users send names of medicines, and the program chooses several pharmacies with lowest price for these items combined.
Medicine prices in pharmacies are inserted manually for now, but web page scraping datasource mode is planned as well.

#### How to run
Program uses Spring Boot, so you can start it as a server by executing Maven goal
```mvn spring-boot:run```
or running SearcherApplication class from your IDE. 
Runs on 9090 port by default

#### Endpoints: 
```POST localhost:9090\basket```
Expects medicine names as JSON string list, and returns all possible pharmacies, where these medicines are present, sorted by cheapest total price. 
