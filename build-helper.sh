#!/usr/bin/env bash

function __print() {
  #  printf "\033[1;31m$1\033[0m\n"
  printf "\033[96m==========> $1\033[0m\n"
}

function releasePatch() {
  __print "Release for patch version"
  ./gradlew clean
  ./gradlew increasePatchVersion
  ./gradlew createReleaseBranch
}

function addReleaseTag() {
  __print "Merge to master and develop"
  ./gradlew clean
  ./gradlew addGitTagForRelease
}

function createHotFix() {
  __print "Create hot fix"
  ./gradlew clean
  ./gradlew increasePatchVersion
  ./gradlew createGitHotFix
}

function haveDoneHotFix() {
  __print "Have done hot fix"
  ./gradlew clean
  ./gradlew haveDoneHotFix
}