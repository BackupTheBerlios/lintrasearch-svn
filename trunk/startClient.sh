#!/bin/sh

CONF_FILE=${HOME}/.lintrasearch/conf/client.xml
JAVA_PATH=/opt/jre1.5.0_06
#JAVA_PATH=/usr

export CLASSPATH=`pwd`/lib


${JAVA_PATH}/bin/java -classpath ./lib/jaxen-core.jar:./lib/jaxen-jdom.jar:./lib/saxpath.jar:./lib/openide-loaders.jar:./lib/openide.jar:./lib/AbsoluteLayout.jar:./lib/jdom.jar:./lib/org.linoratix.configfilereader.jar:./lib/org.linoratix.base64.jar:./lib/activation.jar:./ContentClient/dist/ContentClient.jar contentclient.ContentClient ${CONF_FILE}

