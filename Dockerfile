FROM maven:3-jdk-8
WORKDIR /tmp/
RUN git clone https://github.com/saeg/jaguar.git  
WORKDIR /tmp/jaguar
RUN mvn clean install

FROM java:8
COPY --from=builder /temp/jaguar jaguar
CMD ["./br.usp.each.saeg.jaguar.example/run.sh"]  