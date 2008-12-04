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

cp dist/*service.aar $CATALINA_HOME/webapps/axis2/WEB-INF/services
cp dist/*api.jar $CATALINA_HOME/webapps/axis2/WEB-INF/lib
