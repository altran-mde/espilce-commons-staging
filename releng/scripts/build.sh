#!/usr/bin/env bash

repository="MDEAssets"
updateSite="develop"

mvn clean install --fail-at-end \
	-f releng/org.espilce.commons.parent/pom.xml \
	-s releng/settings.xml \
	-Dmde.updatesite=$updateSite \
	-Dmde.repository=$repository \
	-Dmde.reactor.maven.debug=false \
	-e \
	-U

build_success=$?

exit $build_success


