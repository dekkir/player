### Bugs
1. The request to create a player should be POST (`GET /player/create/{editor}`)
2. The request to create a player  (`GET /player/create/{editor}`) returns the player with filled `id` and `login` fields, but the rest are null. However, if we get the information about this player by `get player request`, we will see that all the fields are filled.
3. A player cannot be created by `editor = admin`, which means that it does not meet the requirement `Only those with role ‘supervisor’ or ‘admin’ can create users`.
4. A player can be created with the password that does not meet the requirement `‘password’ must contain latin letters and numbers (min 7 max 15 characters)`.
5. A player can be created with the gender that does not meet the requirement ``User`s ‘gender’ can only be: ‘male’ or ‘female’``.
6. A player can be created with the already existed `screenName` that does not meet the requirement `‘screenName’ field is unique for each user`.
7. The request to update players (`PATCH /player/update/{editor}/{id}`) has no validation for gender, age, and password, and it does not change the role of the player even if the player is `admin`.
8. If in the body of the request to update players (`PATCH /player/update/{editor}/{id}`) contains the already existed login in the field `login`, the server answers with the undocumented error 409.
9. The request to get all the players (`GET /player/get/all`) does not have any parameters, so there cannot be `4XX` errors, and they should be removed from Swagger.
10. The request to get all the players (`GET /player/get/all`) does not return roles of players, however there is the filed `role` in DTO model.
11. The request to get a player by id (`POST /player/get`) does not have validation for `playerId` and always answers with the code 200; also the code 201 should be removed from Swagger.
12. The server for the request to update players (`PATCH /player/update/{editor}/{id}`) does not check `id` and accept any `long` number (including negative ones) even if it does not exist.
13. The request to delete a player (`DELETE /player/delete/{editor}`) answers with the code 204 when the deletion is successful, so the code 200 should be removed from Swagger.
14. All the requests require no authorization, so the code 401 should be removed from Swagger.