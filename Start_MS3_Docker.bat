cd /D "%~dp0/irs_microservice_3"
mvnw clean package && docker build --tag=irs-3:latest . && docker run -p8883:8083 --name irs-3 irs-3:latest && pause