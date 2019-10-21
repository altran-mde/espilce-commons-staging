#!/usr/bin/env bash

targetBranch="$1"
updateSite="develop"
profiles="acceptance-test"

if [ "$targetBranch" == "develop" ]; then
	profiles+=",publish"
else if [ "${targetBranch:0:10}" == "refs/tags/" ]; then
	profiles+=",publish"
	baselineTag=${targetBranch:10}
	dots=${baselineTag//[^.]}
	if [ ${#dots} == 2 ]; then
		updateSite=${baselineTag%.*}
	else
		updateSite=${baselineTag}
	fi
fi fi

if [[ $profiles == *"publish"* ]]; then
	if curl --output /dev/null --silent --head --fail "https://nexus-p2.manatree.io/nexus/content/sites/MDEAssets/espilce-commons/$updateSite/content.jar"; then
	    profiles+=",merge-existing-repository"
fi fi

echo Building target branch $targetBranch with profiles $profiles and update site $updateSite

mvn clean install -P$profiles \
	-f releng/org.espilce.commons.parent/pom.xml \
	-s settings.xml \
	-Dmde.reactor.maven.debug=false \
	-Dmde.updatesite=$updateSite \
	-Dmaven.test.failure.ignore=true \
	-e \
	-U
	
build_success=$?

exit $build_success


