# Usar la imagen oficial de OpenJDK con Java 17
FROM eclipse-temurin:17-jdk AS build

# Establecer directorio de trabajo
WORKDIR /app

# Instalar Ollama
#RUN curl -fsSL https://ollama.com/install.sh | sh

# Descargar el modelo necesario
#RUN ollama pull llama3.1

# Copiar archivos de configuración y dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Construir la aplicación
RUN ./mvnw package -DskipTests


# Segunda etapa para crear la imagen final
FROM eclipse-temurin:17-jre

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR desde la etapa de compilación
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 11434 3000


# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]


