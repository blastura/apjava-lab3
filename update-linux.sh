#!/bin/sh
#

clear

export CATALINA_HOME=`pwd`/../apache-tomcat-6.0.16
# export JAVA_HOME=/pkg/jdk
export AXIS_HOME=$CATALINA_HOME/webapps/axis2
export AXIS_LIB=$AXIS_HOME/WEB-INF/lib
# export ANT_HOME=`pwd`/../apache-ant-1.7.0

$CATALINA_HOME/bin/shutdown.sh && sleep 2

# rm -r -f $CATALINA_HOME/webapps/dit06ajn/
# rm -r -f $CATALINA_HOME/webapps/dit06ajn.war

# $ANT_HOME/bin/ant clean dist

# cp dist/dit06ajn.war $CATALINA_HOME/webapps

$CATALINA_HOME/bin/startup.sh
#firefox http://localhost:8080/dit06ajn
