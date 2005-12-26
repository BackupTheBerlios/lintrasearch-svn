#!/bin/sh

JAVA_PATH=/opt/jdk1.5.0_06

${JAVA_PATH}/bin/java -classpath ./lib/mysql.jar:./lib/jaxen-core.jar:./lib/jaxen-jdom.jar:./lib/saxpath.jar:./lib/openide-loaders.jar:./lib/openide.jar:./lib/jdom.jar:./lib/org.linoratix.base64.jar:./lib/org.linoratix.configfilereader.jar:./lib/activation.jar:./ContentServer/dist/ContentServer.jar contentserver.ContentServer ${HOME}/.lintrasearch/conf/server.xml $1

