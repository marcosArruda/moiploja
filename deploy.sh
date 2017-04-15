#!/usr/bin/env bash

JAR_DIR=/home/marcos/prog-apps/workspaces/workspace-moip/moiploja/moiploja-site/target
JAR_NAME_ORIGIN=moiploja-site-1.0.jar
JAR_NEW_NAME=moiploja-new.jar

if [[ -f "$JAR_DIR/$JAR_NAME_ORIGIN" ]]
then
	mv $JAR_DIR/$JAR_NAME_ORIGIN $JAR_DIR/$JAR_NEW_NAME
fi

scp $JAR_DIR/$JAR_NEW_NAME marcos@marcosarruda.info:/home/marcos

ssh -t marcos@marcosarruda.info << EOF
	sudo service moiploja stop
	mv $JAR_NEW_NAME $JAR_NAME_ORIGIN
	sudo service moiploja start
EOF
