FROM tomcat:latest
#FROM openjdk:11-jdk
COPY target/*.war /usr/local/tomcat/webapps/bank.war
