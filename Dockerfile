ARG IMAGE=store/intersystems/iris-aa-community:2020.3.0AA.331.0
ARG IMAGE=intersystemsdc/iris-community:2020.2.0.196.0-zpm
ARG IMAGE=intersystemsdc/iris-aa-community:2020.3.0AA.331.0-zpm
ARG IMAGE=intersystemsdc/iris-community:2020.3.0.200.0-zpm
ARG IMAGE=intersystemsdc/iris-community:2020.4.0.524.0-zpm

FROM maven:latest AS reportserver

WORKDIR /usr/src/reportserver
COPY pom.xml .
COPY /src/main/resources /usr/src/reportserver/src/main/resources
RUN mvn -X -B -f  pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -X -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM $IMAGE

USER root
WORKDIR /opt/irisapp
RUN chown ${ISC_PACKAGE_MGRUSER}:${ISC_PACKAGE_IRISGROUP} /opt/irisapp

USER ${ISC_PACKAGE_MGRUSER}

# copy files
COPY  Installer.cls .
COPY src src
COPY  module.xml .  
COPY iris.script /tmp/iris.script


# special extract treatment for hate-speech dataset
# RUN mkdir /data/hate-speech/ \
#	&& tar -xf /data/hate-speech.tar -C /data/

# load demo stuff
RUN iris start IRIS \
	&& iris session IRIS < /tmp/iris.script \
    && iris stop IRIS quietly

COPY /dsw/irisapp.json /usr/irissys/csp/dsw/configs/

USER root   

# Install Java 8 using apt-get from ubuntu repository
RUN apt-get update && \
	apt-get install -y openjdk-8-jdk && \
	apt-get install -y ant && \
	apt-get clean && \
	rm -rf /var/lib/apt/lists/* && \
	rm -rf /var/cache/oracle-jdk8-installer;
	
# Fix certificate issues, found as of 
# https://bugs.launchpad.net/ubuntu/+source/ca-certificates-java/+bug/983302
RUN apt-get install -y ca-certificates-java && \
	apt-get clean && \
	update-ca-certificates -f && \
	rm -rf /var/lib/apt/lists/* && \
	rm -rf /var/cache/oracle-jdk8-installer;

# Setup JAVA_HOME, to enable apps to know 
# where the Java was installed
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME
ENV JRE_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JRE_HOME

# Setup classpath, to enable apps to know 
# where java classes and java jar libraries was installed
ENV classpath .:/usr/irissys/dev/java/lib/JDK18/*:/opt/irisapp/*:/usr/irissys/dev/java/lib/gson/*:/usr/irissys/dev/java/lib/jackson/*
RUN export classpath
ENV CLASSPATH .:/usr/irissys/dev/java/lib/JDK18/*:/opt/irisapp/*:/usr/irissys/dev/java/lib/gson/*:/usr/irissys/dev/java/lib/jackson/*
RUN export CLASSPATH

USER root

ARG APP_HOME=/tmp/app

COPY src $APP_HOME/src

# Tess4J and another java libraries used, 
# are into jgw folder and jgw folder is in the classpath

COPY --from=reportserver /usr/src/reportserver/target/lib /usr/irissys/dev/java/lib/JDK18/

COPY --from=reportserver /usr/src/reportserver/target/reportserver-1.0.0.jar /usr/irissys/dev/java/lib/JDK18/
