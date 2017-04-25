
#!/bin/bash
PROJECT_DIR="~/workspace/runtime-New_configuration/jsoup-1_3_4-1"
JAGUAR_JAR="~/.m2/repository/br/usp/each/saeg/jaguar/br.usp.each.saeg.jaguar.core/0.0.6-SNAPSHOT/br.usp.each.saeg.jaguar.core-0.0.6-SNAPSHOT-jar-with-dependencies.jar"
JAGUAR_MAIN_CLASS="br.usp.each.saeg.jaguar.core.cli.JaguarRunner"

java -cp  "$PROJECT_DIR/target/*:$JAGUAR_JAR" \
"$JAGUAR_MAIN_CLASS" \
--dataflow \
--outputType F \
--logLevel TRACE \
--projectDir "$PROJECT_DIR" \
--classesDir "$PROJECT_DIR/target/classes/" \
--testsDir "$PROJECT_DIR/target/test-classes/" \
--testsListFile "$PROJECT_DIR/testNames.txt"
