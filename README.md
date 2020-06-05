
Make sure you have [JDK 8](https://openjdk.java.net/) or higher installed. Check other requirements of KVision [here](https://kvision.gitbook.io/kvision-guide/part-1-fundamentals/setting-up).

Run in two separate terminals:
* `./gradlew backendRun` - Starts Spring Boot backend server on port 8080. 
* `./gradlew -t frontendRun` - Starts a webpack dev server on port 3000. 

Open `http://localhost:3000` in a browser

Your API can receive the generated matches by making a POST request to `http://localhost:8080/rpc/SkyWeaverAPI/ListMatches`:
```json
{
	"req": {
		"accountAddress":"111"
	}
}
```
Response:
```json
{
  "res": [
    {
      "id": 2,
      "status": "FORFEITED",
      "mode": "RANKED_RANDOM",
      "player1": {
        "address": "111",
        "name": "Test",
        "region": null,
        "tagArtID": "bg-mind-02",
        "deckString": "SWxSTR015CiiGq7hrP2qkBNkGWdfgjuG1dGbpDVhRoew3J7f9BVJs4N7EnGi6V6vrTGMbq1Ggqk3MwnzSZWJzKDxk2c1nj7QGzn2jLDa6Y1wXpQKZz488AAUpsCDc8BC1L4GwjAc7suWCh5NKG2uDfzw5",
        "initDeckString": "SWxSTR01"
      },
      "player2": {
        "address": "121",
        "name": "Test",
        "region": null,
        "tagArtID": "bg-mind-02",
        "deckString": "SWxSTR015CiiGq7hrP2qkBNkGWdfgjuG1dGbpDVhRoew3J7f9BVJs4N7EnGi6V6vrTGMbq1Ggqk3MwnzSZWJzKDxk2c1nj7QGzn2jLDa6Y1wXpQKZz488AAUpsCDc8BC1L4GwjAc7suWCh5NKG2uDfzw5",
        "initDeckString": "SWxSTR01"
      },
      "player1DeckClass": "STR",
      "player2DeckClass": "STR",
      "winningPlayer": 2,
      "turnNonce": 1
    }
  ]
}
```
