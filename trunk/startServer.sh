#!/bin/sh

JAVA_PATH=/usr

${JAVA_PATH}/bin/java -classpath ./lib/mysql.jar:./lib/jdom.jar:./ContentServer/build/classes contentserver.ContentServer

