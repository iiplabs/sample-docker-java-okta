FROM maven:3.6.3-jdk-11-slim AS buildOne
WORKDIR /app
COPY . /app
RUN mvn package

FROM adoptopenjdk:11-jre-hotspot as buildTwo
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
COPY --from=buildOne ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY --from=buildTwo /app/dependencies/ ./
COPY --from=buildTwo /app/snapshot-dependencies/ ./
COPY --from=buildTwo /app/spring-boot-loader/ ./
COPY --from=buildTwo /app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]