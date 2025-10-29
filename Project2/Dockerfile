# ใช้ Java 21 ตามใน PDF [cite: 44]
FROM alpine/java:21

EXPOSE 8080

# ตั้งค่า Profile "prod" ตามใน PDF [cite: 46]
ENV JAVA_PROFILE=prod

# ระบุตำแหน่งไฟล์ที่ถูกแตก .jar ออกมา [cite: 48]
ARG DEPENDENCY=target/dependency

# คัดลอกไฟล์ Libs, Metadata, และ Classes ที่แตกออกมา [cite: 49-51]
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# แก้ไข Entrypoint ให้ตรงกับชื่อคลาส Application.java ของคุณ [cite: src/main/java/se331/project2/Application.java]
ENTRYPOINT ["java", "-Dspring.profiles.active=${JAVA_PROFILE}", \
            "-cp", "app:app/lib/*", "se331.project2.Application"]
