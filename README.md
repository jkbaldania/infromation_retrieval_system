# Information Retrieval System
This repository is created to develop a Java Spring Boot based REST Web Application for the use case of Information Retrieval of Documents.

## Use cases
* User can register with email, username, password and name.
* User can login to the app.
* User can add the batch of files (txt and html) as a zipped file.
* User can query the particular batch of files to find the relevant files which matches with query.
* User should get the results of relevant files as a list with top matching file first, and also the relevance score.
* User can delete any batch.
* User can delete the account.

## Resources and Methods

### POST /register
#### Request:
     curl --location --request POST 'localhost:8881/register'
     --header 'Content-Type: application/json'
     --data-raw '{"username":"jkbaldania","email":"jkbaldania@gmail.com","name":"Jay Baldania","password":"jkbaldania"}'
     
#### Response:
     
     
