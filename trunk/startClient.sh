#!/bin/sh

CONF_FILE=${HOME}/.lintrasearch/conf/client.xml
#JAVA_PATH=/opt/jre1.5.0_06
JAVA_PATH=/usr

${JAVA_PATH}/bin/java -classpath ./lib/jaxen-core.jar:./lib/jaxen-jdom.jar:./lib/saxpath.jar:./lib/openide-loaders.jar:./lib/openide.jar:./lib/AbsoluteLayout.jar:./lib/jdom.jar:./ContentClient/build/classes contentclient.ContentClient ${CONF_FILE}

