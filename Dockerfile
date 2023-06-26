FROM openjdk:11-jdk
COPY target/*war /opt/jboss/wildfly/standalone/deployments/
