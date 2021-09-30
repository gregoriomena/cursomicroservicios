FROM openjdk:12
VOLUME /tmp
EXPOSE 8761
ADD ./target/springboot-servicio-eureka-server-0.0.1-SNAPSHOT.jar servicio-eureka-server.jar
ENTRYPOINT ["java", "-jar", "/servicio-eureka-server.jar"]