FROM gradle:jdk21-jammy AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy

RUN mkdir -p /app/bin

COPY ./opentelemetry-javaagent.jar /app/bin/opentelemetry-javaagent.jar
COPY --from=build /home/gradle/src/build/libs/*.jar /app/bin/app.jar

CMD ["bash", "-c", "java $JAVA_OPTS -javaagent:/app/bin/opentelemetry-javaagent.jar -jar /app/bin/app.jar"]
