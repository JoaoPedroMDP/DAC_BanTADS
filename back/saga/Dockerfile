FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
COPY target/saga-*.jar /app.jar
# COPY ./ /app.jar
ENTRYPOINT [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar" ]
# ENTRYPOINT [ "java","-jar","/back/saga/target/saga-0.0.1-SNAPSHOT.jar" ]
# CMD ["ls"]