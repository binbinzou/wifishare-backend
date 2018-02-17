FROM anapsix/alpine-java:jre8
#update system timezone
#RUN set time zone
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#update application timezone
RUN echo "Asia/Shanghai" >> /etc/timezone
VOLUME /tmp
ADD wifishare-backend*.jar app.jar
RUN bash -c 'touch /app.jar'
#EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=Asia/Shanghai","-jar","/app.jar"]
