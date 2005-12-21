#!/bin/sh

JAVA_PATH=/usr

${JAVA_PATH}/bin/java -classpath ./lib/mysql.jar:./lib/jaxen-core.jar:./lib/jaxen-jdom.jar:./lib/saxpath.jar:./lib/openide-loaders.jar:./lib/openide.jar:./lib/jdom.jar:./lib/org.linoratix.base64.jar:./lib/org.linoratix.configfilereader.jar:./ContentServer/build/classes contentserver.ContentServer ${HOME}/.lintrasearch/conf/server.xml $1

