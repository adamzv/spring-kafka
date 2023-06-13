FROM eclipse-temurin:17.0.7_7-jre-jammy
RUN addgroup --system spring && adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]