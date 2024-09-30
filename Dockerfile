FROM openjdk:latest
COPY build/libs/e-commerce-0.0.1-SNAPSHOT.jar e-commerce-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/e-commerce-0.0.1-SNAPSHOT.jar"]