mvn clean validate compile assembly:assembly -DdescriptorId=jar-with-dependencies;
cd target;
java -jar autodownload-1-jar-with-dependencies.jar $1 $2; 
cd ..
