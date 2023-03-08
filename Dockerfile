#FROM openjdk:19-jdk-alpine
#ARG  WAR_FILE=target/*.war
#COPY ${WAR_FILE} AttornatusJavaBackend.war
#ENTRYPOINT ["java", "-jar", "AttornatusJavaBackend.war"]

#
# Build stage
#
FROM maven:3.9-amazoncorretto-19 AS build
COPY . .
RUN mvn clean package

#
# Package stage
#
FROM openjdk:19-jdk-alpine
COPY --from=build /target/*.war AttornatusJavaBackend.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","AttornatusJavaBackend.jar"]