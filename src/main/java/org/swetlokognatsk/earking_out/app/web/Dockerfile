FROM eclipse-temurin:26-jre

WORKDIR /app

ENTRYPOINT ["java", "-cp", "/app/BOOT-INF/classes:/app/BOOT-INF/lib/*", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-Dspring.profiles.active=dev", "org.swetlokognatsk.earking_out.WebEntryPoint"]