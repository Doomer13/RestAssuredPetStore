FROM openjdk:11
WORKDIR /app
COPY out/artifacts/unnamed/unnamed.jar /app/test.jar
ENTRYPOINT ["java", "-jar", "test.jar"]
