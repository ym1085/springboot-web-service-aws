FROM openjdk:8-jdk

ENV APP_HOME=/apps/

WORKDIR $APP_HOME

COPY build/libs/*.jar app.jar

EXPOSE 8080

#ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-Xms1g", "-Xmx1g", "-XX:+UseG1GC", "-XX:MetaspaceSize=96m", "-XX:MaxMetaspaceSize=256m", "-jar", "apps.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]
