FROM amazoncorretto:21-alpine

ENV TZ=America/Bogota

RUN apk add --no-cache tzdata \
  && cp /usr/share/zoneinfo/$TZ /etc/localtime \
  && echo $TZ > /etc/timezone

RUN addgroup -S app && adduser -S app -G app
USER app

WORKDIR /app

COPY target/tecnica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","/app/app.jar"]
