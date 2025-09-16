#FROM eclipse-temurin:21-jdk-alpine
FROM eclipse-temurin:17.0.16_8-jdk-alpine
EXPOSE 8082
WORKDIR /app

COPY applications/app-service/build/libs/reportes.jar /app/reportes.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
# Replace with a non-root user to avoid running the container with excessive privileges
RUN adduser -D appuser

USER appuser
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar /app/reportes.jar" ]
