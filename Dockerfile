FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/TheShopApp-0.0.1-SNAPSHOT.jar /app/TheShop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/TheShop.jar"]