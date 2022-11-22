FROM openjdk:17
EXPOSE 8080
ADD target/spring-boot-test-app.jar spring-boot-test-app.jar
ENTRYPOINT ["java", "-jar", "spring-boot-test-app.jar"]