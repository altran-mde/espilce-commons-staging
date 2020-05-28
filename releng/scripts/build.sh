#!/usr/bin/env bash

targetBranch="$1"
tag="$2"
updateSite="develop"
profiles="acceptance-test"
publish="OFF"
if [ -n "$2" ]; then
	publish="ON"
	baselineTag=$2
	dots=${baselineTag//[^.]}
	if [ ${#dots} == 2 ]; then
		updateSite=${baselineTag%.*}
	else
		updateSite=${baselineTag}
	fi
else if [ "$targetBranch" == "develop" ]; then
	publish="ON"
fi fi

if [[ $publish == ON ]]; then
	echo "Checking if there is a previous" $updateSite " in the cloud"
	if curl --output /dev/null --silent --head --fail -u admin:AP2nisfj4PaqW5mWM5qsrftbSs1 "https://artifactory.acidspace.nl/artifactory/espilce-commons/$updateSite/content.jar"; then
	    profiles+=",merge-existing-repository"
fi fi


echo Building $updateSite

mvn clean install --fail-at-end -P$profiles \
	-f releng/org.espilce.commons.parent/pom.xml \
	-s releng/settings.xml \
	-Dmde.updatesite=$updateSite \
	-Dmde.reactor.maven.debug=false \
	-e \
	-U

# Deploy to the specified update-site if it is develop or a tag has been created.
if [ $publish == ON ]; then
	echo "Deploying to " $updateSite
	cd releng/org.espilce.commons.repository && mvn deploy -s ../settings.xml -Dmde.updatesite=$updateSite
fi

build_success=$?

exit $build_success


