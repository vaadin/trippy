# trippy
Simple trip planner demo for Flow

To use, you need to register your own Google Maps API key at https://console.developers.google.com/. The key should be added to the `application.properties` file with the property key `map.apikey`, e.g. `map.apikey=abcd1234`.

Build using
```
mvn clean install
```
Run using
```
cd trippy-ui
mvn spring-boot:run
```
