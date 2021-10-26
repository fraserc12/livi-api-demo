### Build backend
FROM maven:3.8.3-openjdk-11-slim as api-build

WORKDIR /livi-api

# Copy the pom.xml file - will be cached unless pom.xml changes
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copy source files
COPY ./src ./src

# Package application
RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Image to run app
FROM openjdk:11.0-jre-slim

ARG DEPENDENCY=/livi-api/target/dependency

# Copy dependencies
COPY --from=api-build ${DEPENDENCY}/BOOT-INF/lib /livi-api/lib
COPY --from=api-build ${DEPENDENCY}/META-INF /livi-api/META-INF
COPY --from=api-build ${DEPENDENCY}/BOOT-INF/classes /livi-api

ENTRYPOINT ["java","-cp","livi-api:livi-api/lib/*","com.livi.demo.HealthcheckApplication"]