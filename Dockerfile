FROM tomcat:9-jre8-alpine
MAINTAINER "yadav.ashu2496@gmail.com"
EXPOSE 8080
COPY ./mvnwebapp.war /usr/local/tomcat/webapps

