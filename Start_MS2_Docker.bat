cd /D "%~dp0/irs_microservice_2"
mvnw clean package && docker build --tag=irs-2:latest . && docker run -p8882:8082 --name irs-2 irs-2:latest && pause