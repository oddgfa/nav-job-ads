FROM maven:3.8.7-eclipse-temurin-17-alpine AS build
ENV HOME=/home/app
RUN mkdir -p $HOME
COPY pom.xml $HOME
RUN mvn -f $HOME/pom.xml verify clean --fail-never
COPY src $HOME/src
RUN mvn -f $HOME/pom.xml clean install

FROM eclipse-temurin:17.0.6_10-jre-focal
COPY --from=build /home/app/target/jobads-0.0.1-SNAPSHOT.war /usr/local/lib/jobads.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/jobads.war"]
