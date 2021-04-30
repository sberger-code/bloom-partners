# Movie Memory
#### In memory movie library
To run the app: `java -Duser.country=US -Duser.language=us -jar target/work-sample-1.0-SNAPSHOT.jar server movie-memory.yml`

Postman and curl are good choices to test the app. 

The available REST resources are `movie`, `actor` and `director`. The CRUD endpoint structure for each resource is:

URI | Method | Description
--- | --- | ---
`/{resource}` | POST | Create a new entity
`/{resource}` | GET | Retrieve all entities or filtered
`/{resource}/id` | GET | Retrieve an entity by its ID
`/{resource}/id` | PUT | Update an existing entity
`/{resource}/id` | DELETE | Delete an entity

<br/>
The Movie resource has the structure:

Field | Type
--- | ---
`title` (mandatory | String
`releaseYear` | Integer
`duration` | Integer
`actors` | Array of Actors
`director` | Director

<br/>
The Actor and Director resources both have the structure:

Field | Type
--- | ---
`name` (mandatory) | String
`movies` | Array of Movies

The `movies` property of Actors and Directors is read-only. Actors and directors are assigned to movies via 
POST or PUT on the movie resource. Actors and directors can also be created via POST and PUT 
on the movie resource by providing an object with a value for the `name` property (director) or an array of objects with 
a value for the `name` property (actors). If a value for the `id` property is given and an actor (or director) with 
the given ID exists that actor (or director) is assigned to the movie.

**Example:** See `initialize.sql` for example POST requests. 

Using GET on any resource the entities can optionally be filtered by search terms. Fields of type String are searched
for the substring. Fields of type Integer are compared whether they are equal to the search term.

**Example:** `/movie?director=tarantino&releaseYear=1994`

The records are stored in an in memory relational database. Relational databases are often prefererd when structured
records are related to another. That's the case for example with movies and actors, which have many-to-many 
relationship. For storing the actual movie as a file in the database a document oriented database would be more suitable.
