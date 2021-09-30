FROM openjdk:12
VOLUME /tmp
EXPOSE 8090
ADD ./target/springboot-servicio-zuul-server-0.0.1-SNAPSHOT.jar servicio-zuul-server.jar
ENTRYPOINT ["java", "-jar", "/servicio-zuul-server.jar"]