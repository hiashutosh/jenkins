FROM tomcat:8-jre8
MAINTAINER "yadav.ashu2496@gmail.com"
COPY ./mvnwebapp.war /usr/local/tomcat/webapps

