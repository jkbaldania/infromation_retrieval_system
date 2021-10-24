# Information Retrieval System
A Java Spring Boot based REST Web Application for the use case of Information Retrieval of Documents (under development).

## Use cases
* User can register with email, username, password and name.
* User can login to the app.
* User can add the batch of files (txt and html) as a zipped file.
* User can query the particular batch of files to find the relevant files which matches with query.
* User should get the results of relevant files as a list with top matching file first, and also the relevance score.
* User can delete any batch.
* User can delete the account.

## Sequence Diagram
![sequence_diagram drawio](https://user-images.githubusercontent.com/56929164/138592494-11c891c6-c2fe-49de-9b5d-741b2de3fc6b.png)

## Resources and Methods

### POST /register
#### Request:
![register request](https://user-images.githubusercontent.com/56929164/138592023-6d238373-c210-4214-8519-e4e0d9bff1a4.PNG)
     
#### Response:
![register response](https://user-images.githubusercontent.com/56929164/138592052-30a4243c-2592-44a4-a0e5-f359150e8255.PNG)

### POST /authenticate
#### Request:
![authenticate request](https://user-images.githubusercontent.com/56929164/138592165-0a1a7aa0-67d5-43a8-bba5-f5936a2b6949.PNG)


#### Response:
![authenticate response](https://user-images.githubusercontent.com/56929164/138592174-0795b629-b584-4614-9c01-985fd8496d7f.PNG)


### POST /add_batch
#### Request:
![add_batch request](https://user-images.githubusercontent.com/56929164/138592184-c3bd10fb-13b0-4262-85ba-144cd461e0e6.PNG)


#### Response:
![add_batch reponse](https://user-images.githubusercontent.com/56929164/138592188-897e913d-8c5e-4eb0-998b-f3e79c877976.PNG)


### GET /query_batch
#### Request:
![query_batch request](https://user-images.githubusercontent.com/56929164/138592195-c0dda366-dc66-4a71-a307-38eb8a512f6e.PNG)


#### Response:
![query_batch response](https://user-images.githubusercontent.com/56929164/138592199-66135477-b92b-47a6-900e-f0d53dd69124.PNG)


### DELETE /delete_batch
#### Request:
![delete_batch request](https://user-images.githubusercontent.com/56929164/138592208-e23a1758-b133-4ab8-8c67-7334c82a2348.PNG)


#### Response:
![delete_batch response](https://user-images.githubusercontent.com/56929164/138592217-348721dd-aab3-4073-b3da-1a5c8f44ca62.PNG)


### DELETE /delete_account
#### Request:
![delete_account request](https://user-images.githubusercontent.com/56929164/138592231-6096f06b-b013-437c-b5e6-34909467bb25.PNG)


#### Response:
![delete_account response](https://user-images.githubusercontent.com/56929164/138592235-13c6388b-bf8f-4b86-ad28-9affe53e6e1b.PNG)
