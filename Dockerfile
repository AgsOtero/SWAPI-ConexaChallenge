# Usar una versión específica de Amazon Corretto
FROM amazoncorretto:8u312

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado por Maven en el directorio de trabajo
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Ejecutar la aplicación Java
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
