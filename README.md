Task

Part 1.1: REST Project:
• Create a new Spring Boot Project for the REST-Server part
Part 1.2: User Management:
• Create a controller class for User/Session management (used by the smartphone-app)
• Implement the following endpoints for User/Session management:
o POST /api/users/register – pass the User information in the request body
o POST /api/users/login – passes username and password with basic authentication;
will return a token-string in the response body (if successful)
o POST /api/users/logout – takes an authentication token-string; will logout the user
o GET /api/users – takes an authentication token-string; if the user-role is “fleetmanager”, then a list of all users is returned in the response body; otherwise HTTP 403
(forbidden)
o PUT /api/users/{id} – takes an authentication token-string; pass the User
Information in the request body.
Attention: the “fleet-manager” may update all users, the others only themselves.
• Store all users in memory using a List or Map.
Part 1.3: Vehicle Management:
• Create a controller class for Vehicle management
• Implement the following endpoints for Vehicle management:
Attention: all requests need to provide a valid authentication-token (bearer) of a logged-in
fleet-manager user, otherwise HTTP 403 (forbidden) is returned
o POST /api/vehicles – pass the vehicle information in the request-body. A new
vehicle is registered
o GET /api/vehicles – returns a list of all vehicles
o GET /api/vehicles/{id} – returns the vehicle of the id
o PUT /api/vehicles/{id} – pass the updated vehicle in the request-body
o DELETE /api/vehicles/{id} – remove the vehicle of the id
• Store all vehicles in memory accordingly.


Notes

In this updated version, no more parameters are used to carry information but HTTP body JSON strings. 
Java2JSON is a small helper program, which produces JSON strings of the classes necessary for the HTTP requests (no UI, just code). 