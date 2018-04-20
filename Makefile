all: prepare build

# Copy Jacoco Core dependency to local maven repository
prepare:
	./mvnw install:install-file -Dfile=br.usp.each.saeg.jaguar.core/lib/org.jacoco.core-0.7.6.jar \
     -DgroupId=br.usp.each.saeg -DartifactId=org.jacoco.core \
     -Dversion=0.7.6 -Dpackaging=jar

# Build only Jaguar Core and its dependencies
build_core:
	./mvnw install -pl br.usp.each.saeg.jaguar.core -am

# Build all modules
build:
	./mvnw clean install
	
# Run a simple example use of Jaguar Core
run:
	./br.usp.each.saeg.jaguar.example/run.sh

# Build the whole project from a docker image
docker:
	docker build -t myrepo/jaguar-core:latest -f br.usp.each.saeg.jaguar.build/Dockerfile .