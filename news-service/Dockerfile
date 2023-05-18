FROM gradle:jdk17 AS bootJar
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17.0-jdk-slim
RUN mkdir /app
COPY --from=bootJar /home/gradle/src/build/libs/*.jar /app/
ENV JAVA_OPTS=""
CMD java ${JAVA_OPTS} -jar /app/news-service-0.0.1-SNAPSHOT.jar