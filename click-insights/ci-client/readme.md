## Client
Generates sample click events as JSON. Below is a sample message generated:
```json
{"timestamp":"10/11/2015 04:40:10:504","url":"yass.com/view/pay","method":"POST","browser":"safari","email":"roselle.estell@hotmail.com","fname":"Roselle","lname":"Estell","geoname":"Newark","latitude":"40.735657","longitude":"40.735657","sessionid":"686281781"}
```json
* [timestamp] : Represents timestamp when this event happened
* [url] : Associated URL to this event. This can be a view/data URL
* [method] : HTTP method that identifies the operation perform on the web resource identified by URL.
* [browser] : Browser from where the event was generated.
* [email] : User's email ID.
* [fname] : User's first name.
* [lname] : User's last name.
* [geoname] : Geography name from where the event was generated.
* [latitiude] : Latitude of geography
* [longitude] : Longitude of geography
* [sessionid] : Session identifier

## Usage
### Building the library
```sh
mvn clean install
```sh
Above command shall generate a fat jar in target folder under ci-client folder. 
### JSON event generation
```sh
java -jar .\target\ci-client-1.0.jar MM-DD-YYYY OUTPUT_LOCATION
```sh
First parameter is a data in MM-DD-YYYY format
Second parameter is the out put location where you want the events to be generated.

### FSA based event generator
* Event trails represents real time user session with all activities on shopping site is generated. Also, generates patterns like browser refresh & back button press.