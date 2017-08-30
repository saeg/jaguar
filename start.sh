#!/bin/bash

PROJECT_DIR="./br.usp.each.saeg.jaguar.example/"
JAGUAR_JAR="./br.usp.each.saeg.jaguar.core/target/br.usp.each.saeg.jaguar.core-1.0.0-jar-with-dependencies.jar"
JAGUAR_MAIN_CLASS="br.usp.each.saeg.jaguar.core.cli.JaguarRunner4Eclipse"
JACOCO_JAR="./br.usp.each.saeg.jaguar.plugin/lib/jacocoagent.jar"

# CONTROL-FLOW
#
## JvmArgs = [-javaagent:/home/henrique/workspace/java/jaguar/br.usp.each.saeg.jaguar.plugin/lib/jacocoagent.jar=output=tcpserver,includes=*, -Xmx1024m]
##
## JaguarArgs = [--outputType, F, --logLevel, INFO, --projectDir, /home/henrique/workspace/runtime-EclipseApplication/example, --classesDir, /home/henrique/workspace/runtime-EclipseApplication/example/target/classes, --testsListFile, /tmp/testNames7371158783007714516.txt]

java -cp $PROJECT_DIR/target/classes/ br.usp.each.saeg.jaguar.example.Max

java -javaagent:$JACOCO_JAR=output=tcpserver,includes=* -cp  $PROJECT_DIR/target/classes/:$JAGUAR_JAR:$JACOCO_JAR \
"$JAGUAR_MAIN_CLASS" \
--outputType F \
--logLevel TRACE \
--projectDir "$PROJECT_DIR" \
--classesDir "$PROJECT_DIR/target/classes/" \
--testsListFile "$PROJECT_DIR/testListFile.txt"

# DATA-FLOW
#
## JvmArgs = [-javaagent:/home/henrique/workspace/java/jaguar/br.usp.each.saeg.jaguar.plugin/lib/jacocoagent.jar=output=tcpserver,includes=*,dataflow=true, -Xmx1024m]
##
## JaguarArgs = [--dataflow, --outputType, F, --logLevel, INFO, --projectDir, /home/henrique/workspace/runtime-EclipseApplication/example, --classesDir, /home/henrique/workspace/runtime-EclipseApplication/example/target/classes, --testsListFile, /tmp/testNames4495953423924079598.txt]
#
#java -javaagent:$JACOCO_JAR=output=tcpserver,includes=*,dataflow=true -cp $PROJECT_DIR/target/classes/:$JAGUAR_JAR:$JACOCO_JAR" \
#"$JAGUAR_MAIN_CLASS" \
#--dataflow \
#--outputType F \
#--logLevel TRACE \
#--projectDir "$PROJECT_DIR" \
#--classesDir "$PROJECT_DIR/target/classes/" \
#--testsListFile "$PROJECT_DIR/testListFile.txt"
