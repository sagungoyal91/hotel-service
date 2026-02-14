FROM eclipse-temurin:22-jdk
COPY target/hotel-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
