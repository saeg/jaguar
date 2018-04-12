#!/bin/bash

PROJECT_DIR="./br.usp.each.saeg.jaguar.example"
JAGUAR_JAR="./br.usp.each.saeg.jaguar.core/target/br.usp.each.saeg.jaguar.core-1.0.0-jar-with-dependencies.jar"
JAGUAR_MAIN_CLASS="br.usp.each.saeg.jaguar.core.cli.JaguarRunner"
JACOCO_JAR="./br.usp.each.saeg.jaguar.plugin/lib/jacocoagent.jar"
LOG_LEVEL="TRACE" # ERROR / INFO / DEBUG / TRACE

# CONTROL-FLOW

java -javaagent:$JACOCO_JAR=output=tcpserver -cp $PROJECT_DIR/target/classes/:$PROJECT_DIR/target/test-classes/:$JAGUAR_JAR:$JACOCO_JAR \
		"$JAGUAR_MAIN_CLASS" \
			--outputType F \
			--logLevel "$LOG_LEVEL" \
			--projectDir "$PROJECT_DIR" \
			--classesDir "$PROJECT_DIR/target/classes/" \
			--testsDir "$PROJECT_DIR/target/test-classes/" \
			--testsListFile "$PROJECT_DIR/testListFile.txt"

# DATA-FLOW

java -javaagent:$JACOCO_JAR=output=tcpserver,dataflow=true -cp $PROJECT_DIR/target/classes/:$PROJECT_DIR/target/test-classes/:$JAGUAR_JAR:$JACOCO_JAR \
		"$JAGUAR_MAIN_CLASS" \
			--dataflow \
			--outputType F \
			--logLevel "$LOG_LEVEL" \
			--projectDir "$PROJECT_DIR" \
			--classesDir "$PROJECT_DIR/target/classes/" \
			--testsDir "$PROJECT_DIR/target/test-classes/" \
			--testsListFile "$PROJECT_DIR/testListFile.txt"
