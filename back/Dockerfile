FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/rental-eventos-0.0.1-SNAPSHOT.jar"]
