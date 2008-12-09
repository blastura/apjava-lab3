#!/bin/sh
#

export CATALINA_HOME=`pwd`/../apache-tomcat-6.0.16
export AXIS_HOME=$CATALINA_HOME/webapps/axis2
export AXIS_LIB=$AXIS_HOME/WEB-INF/lib
# export ANT_HOME=`pwd`/../apache-ant-1.7.0

$CATALINA_HOME/bin/startup.sh
