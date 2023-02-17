FROM openjdk:19-jdk-alpine
ARG  WAR_FILE=target/*.war
COPY ${WAR_FILE} AttornatusJavaBackend.war
ENTRYPOINT ["java", "-jar", "AttornatusJavaBackend.war"]