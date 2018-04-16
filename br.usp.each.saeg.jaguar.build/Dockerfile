FROM maven:3-jdk-8
RUN apt update && apt install -y build-essential
WORKDIR ~/
ADD ./ ~/jaguar
WORKDIR ~/jaguar
RUN make prepare
RUN make build
RUN make run