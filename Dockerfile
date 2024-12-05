FROM openjdk:21-oracle

COPY ./target/app.jar app.jar

CMD ["java", "-jar", "app.jar"]