FROM maven:latest AS reportserver

WORKDIR /usr/src/reportserver
COPY pom.xml .
RUN mvn -X -B -f  pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -X -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests


FROM openjdk:8-jdk-alpine
COPY --from=reportserver /usr/src/reportserver/target/reportserver-1.0.0.jar /usr/src/reportserver/target/reportserver-1.0.0.jar
