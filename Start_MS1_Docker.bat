cd /D "%~dp0/irs_microservice_1"
mvnw clean package && docker build --tag=irs-1:latest . && docker run -p8881:8081 --name irs-1 irs-1:latest && pause