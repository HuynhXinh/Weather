#!/usr/bin/env bash

function __print() {
  #  printf "\033[1;31m$1\033[0m\n"
  printf "\033[96m==========> $1\033[0m\n"
}

function runTest() {
  __print "Run sit debug unit test"
  ./gradlew clean
  ./gradlew :app:jacocoTestReportDebug
  ./gradlew :data:jacocoTestReportDebug
  ./gradlew :domain:jacocoTestReport
  ./gradlew mergeJacocoReports jacocoTestReportMerged
  ./gradlew mergeTestReports
}