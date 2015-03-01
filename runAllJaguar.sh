function pause(){
   read -p "$*"
}

heuristics=( "DRT" "Jaccard" "Kulczynski2" "McCon" "Minus" "Ochiai" "Op" "Tarantula" "Wong3" "Zoltar" )

CLASSPATH="jaguar.jar;target\classes;target\test-classes"
MAIN="br.usp.each.saeg.jaguar.core.cli.JaguarRunner"
PROJECT_DIR="."
CLASSES_DIR=".\target\classes"
TESTS_DIR=".\target\test-classes"

mkdir jaguar-results

set -x
for h in "${heuristics[@]}"
do
	java -javaagent:jacocoagent.jar=output=tcpserver,dataflow=true -cp $CLASSPATH $MAIN -h $h -p $PROJECT_DIR -c $CLASSES_DIR -t $TESTS_DIR -o jaguar-results/df_$h -df
	java -javaagent:jacocoagent.jar=output=tcpserver -cp $CLASSPATH $MAIN -h $h -p $PROJECT_DIR -c $CLASSES_DIR -t $TESTS_DIR -o jaguar-results/cf_$h 
done
pause