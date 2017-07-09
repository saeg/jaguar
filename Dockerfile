FROM anapsix/alpine-java
MAINTAINER myNAME 
COPY br.usp.each.saeg.jaguar.core/target/br.usp.each.saeg.jaguar.core-1.0.0-jar-with-dependencies.jar /home/jaguar-core.jar
CMD ["java","-jar","/home/jaguar-core.jar", "--help"]