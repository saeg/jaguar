FROM java:8u111-jdk-alpine
WORKDIR ~/
ADD ./ ~/jaguar
WORKDIR ~/jaguar
RUN ./mvnw install:install-file -Dfile=br.usp.each.saeg.jaguar.core/lib/org.jacoco.core-0.7.6.jar \
     -DgroupId=br.usp.each.saeg -DartifactId=org.jacoco.core \
     -Dversion=0.7.6 -Dpackaging=jar
RUN ./mvnw clean install
RUN ./br.usp.each.saeg.jaguar.example/run.sh