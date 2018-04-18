all: prepare build

prepare:
	./mvnw install:install-file -Dfile=br.usp.each.saeg.jaguar.core/lib/org.jacoco.core-0.7.6.jar \
     -DgroupId=br.usp.each.saeg -DartifactId=org.jacoco.core \
     -Dversion=0.7.6 -Dpackaging=jar

build:
	./mvnw clean install
	
run:
	./br.usp.each.saeg.jaguar.example/run.sh

docker:
	docker build -t myrepo/jaguar-core:latest -f br.usp.each.saeg.jaguar.build/Dockerfile .