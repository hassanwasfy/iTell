#!/bin/bash
# Prepare the JitPack build environment for Maven publication
mvn install:install-file \
  -Dfile=itell-release.aar \
  -DgroupId=com.github.hassanwasfy \
  -DartifactId=itell \
  -Dversion=2.0 \
  -Dpackaging=aar \
  -DgeneratePom=true
