FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/E-Commerce-0.0.1-SNAPSHOT.jar E-Commerce.
EXPOSE 8080
ENTRYPOINT [ "java","-jar" ,"E-Commerce.jar" ]