FROM tomcat:8.0.20-jre8
\\FROM openjdk:11-jdk
COPY target/*war /opt/jboss/wildfly/standalone/deployments/
