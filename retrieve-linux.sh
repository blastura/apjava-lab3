#!/bin/sh
#

#CATALINA_HOME=~/edu/apjava/env/5dv085/apache-tomcat-6.0.16
CATALINA_HOME=../apache-tomcat-6.0.16
AXIS_HOME=$CATALINA_HOME/webapps/axis2
AXIS_LIB=$AXIS_HOME/WEB-INF/lib

JARPATH="$AXIS_LIB/*.jar"
AXIS2CP=""
for FILE in $JARPATH; do 
  AXIS2CP=$AXIS2CP:$FILE
done

java -cp "$AXIS2CP:dist/highscoreservice-api.jar" -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.NoOpLog -Ddebug.messages=true se.umu.cs.edu.jap.highscoreservice.RetrieveClient $1 $2 $3 $4 $5 $6 $7 $8 $9
