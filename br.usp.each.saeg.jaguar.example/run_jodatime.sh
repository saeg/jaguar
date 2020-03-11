#!/bin/bash

# 
# Script to run jaguar on defects4j Joda-Time
#

CP="$(defects4j export -p cp.test -w jodatime)"
PROJECT_DIR="jodatime/"
CLASSES_DIR="target/classes/"
TESTS_DIR="target/test-classes/"
TEST_SUITE="org.joda.time.TestAllPackages"
JAGUAR_JAR="br.usp.each.saeg.jaguar.core-1.0.0-jar-with-dependencies.jar"
AGENT_JAR="ba-dua-agent-rt-0.4.1-SNAPSHOT-all.jar"
HEURISTIC="Ochiai"
LOG_LEVEL="DEBUG" # ALL / TRACE / DEBUG / INFO / WARN / ERROR

java -jar $JAGUAR_JAR \
	--agent $AGENT_JAR \
    --classpath $CP \
	--projectDir $PROJECT_DIR \
	--classesDir $CLASSES_DIR \
	--testsDir $TESTS_DIR \
	--testSuite $TEST_SUITE \
	--logLevel $LOG_LEVEL \
	--dataflow
