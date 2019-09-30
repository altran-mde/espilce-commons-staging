#!/bin/bash

WIN_HOME_DIR=/mnt/c/Users/nstotz
WORKSPACE_DIR=$WIN_HOME_DIR/eclipse-workspaces/espilce-commons
GIT_DIR=$WIN_HOME_DIR/git/espilce-commons
ECLIPSE_DIR=$WIN_HOME_DIR/eclipse-distros/dsl-latest-stable
MAVEN_DIR=$WIN_HOME_DIR/.m2
WAIT_FOR_DEBUG=n

java \
	-ea \
	-Dfile.encoding=UTF-8 \
	-agentlib:jdwp=transport=dt_socket,server=y,address=8998,suspend=$WAIT_FOR_DEBUG \
	-jar $MAVEN_DIR/repository/org/junit/platform/junit-platform-console-standalone/1.5.2/junit-platform-console-standalone-1.5.2.jar \
	-classpath \
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.hamcrest.library_1.3.0.v20180524-2246.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.junit.jupiter.api_5.5.1.v20190826-0900.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.junit.jupiter.params_5.5.1.v20190826-0900.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.junit.platform.commons_1.5.1.v20190826-0900.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.junit.platform.engine_1.5.1.v20190826-0900.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.junit.jupiter.engine_5.5.1.v20190826-0900.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.opentest4j_1.0.0.v20170910-2246.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.junit_4.12.0.v201504281640/junit.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.eclipse.jdt.annotation_2.1.150.v20180322-1206.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.apache.commons.lang3_3.1.0.v201403281430.jar:"\
"$WORKSPACE_DIR/.metadata/.plugins/org.eclipse.pde.core/.bundle_pool/plugins/org.apache.commons.io_2.2.0.v201405211200.jar:"\
"$GIT_DIR/component/exception/org.espilce.commons.exception/target/classes:"\
"$GIT_DIR/component/lang/org.espilce.commons.lang/bin:"\
"$GIT_DIR/component/lang/org.espilce.commons.lang.test.base/bin:"\
"$GIT_DIR/component/lang/org.espilce.commons.lang.test/target/test-classes:"\
"$ECLIPSE_DIR/configuration/org.eclipse.osgi/352/0/.cp:"\
"$ECLIPSE_DIR/configuration/org.eclipse.osgi/350/0/.cp:"\
"$ECLIPSE_DIR/configuration/org.eclipse.osgi/657/0/.cp" \
	--scan-classpath \
	--reports-dir=./target/reports \
	-n=org.espilce.commons.lang.test.conversionutils.javafile.javaurl.*
