FROM maven:3-jdk-8
RUN apt update && apt install -y build-essential
WORKDIR /tmp/
RUN git clone https://github.com/saeg/jaguar.git  
WORKDIR /tmp/jaguar
RUN make prepare
RUN make build
RUN make run