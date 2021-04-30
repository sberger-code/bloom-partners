# Movie Memory
#### In memory movie library
To run the app: `java -jar target/work-sample-1.0-SNAPSHOT.jar server movie-memory.yml`

To test the app use Postman or curl. The available endpoints are:

URI | Method | Description
--- | --- | ---
/movie | POST | Create a new Movie record
/movie | GET | Retrieve all movies
/movie/id | GET | Retrieve a movie by its ID
/movie?title=\<searchTerm\><br/>&releaseYear=\<year\><br/>&duration=\<duration in minutes\><br/>&actor=\<searchTerm\> | GET | Filter results by title, release year, duration, actor and/or director <br/> ex: `http://localhost:8080/movie?releaseYear=2008&director=tarantino
/movie/id | PUT | Update an existing movie
/movie/id | DELETE | Delete a movie
/actor | POST | Create a new Actor recrod
/actor | GET | Retrieve all actors
/actor/id | GET | Retrieve an actor by ID
/actor/id | PUT | Update an existing actor
/actor/id | DELETE | Delete an actor
