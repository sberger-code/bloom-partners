curl -XPOST -H "Content-Type: application/json" -d '{ "name": "John Travolta" }' localhost:8080/actor
curl -XPOST -H "Content-Type: application/json" -d '{ "name": "Samuel L. Jackson" }' localhost:8080/actor

curl -XPOST -H "Content-Type: application/json" -d '{ "name": "Quentin Tarantino" }' localhost:8080/director
curl -XPOST -H "Content-Type: application/json" -d '{ "name": "John Badham" }' localhost:8080/director

curl -XPOST -H "Content-Type: application/json" -d '{ "title": "Pulp Fiction", "releaseYear": 1994, "duration": 90, "actors": [{ "id": 1 }, { "id": 2 }], "director": {"id": 1 }}' localhost:8080/movie
curl -XPOST -H "Content-Type: application/json" -d '{ "title": "Saturday Night Fever", "releaseYear": 1977, "duration": 90, "actors": [{ "id": 1 }], "director": {"id": 2 }}' localhost:8080/movie

echo
