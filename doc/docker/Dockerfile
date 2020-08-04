FROM openjdk:8-jre-alpine as builder

WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 提取分层信息
RUN java -Djarmode=layertools -jar app.jar extract

# 第二阶段
# FROM adoptopenjdk/openjdk8
FROM openjdk:8-jre-alpine

# apk 镜像源
#RUN echo -e "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.9/main\n\
#https://mirror.tuna.tsinghua.edu.cn/alpine/v3.9/community" > /etc/apk/repositories

# 安装字体库，easyexcel 依赖
RUN apk --update add ttf-dejavu && \
      rm -rf /var/cache/apk/*

WORKDIR application

# 制作镜像层
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENV TZ="Asia/Shanghai" JAVA_OPTS=" " SPRING_OPTS=" "

EXPOSE 80
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom org.springframework.boot.loader.JarLauncher ${SPRING_OPTS}"]
