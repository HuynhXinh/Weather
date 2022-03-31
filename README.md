# Run unit test
```sh
source run-unit-test.sh && runTest

And go to the folder to see the reports:
Weather/build/reports/jacoco/index.html or Weather/build/reports/tests/test/index.html
```

# Release patch version
```sh
source build-helper.sh && releasePatch
```

# Add release tag
```sh
source build-helper.sh && addReleaseTag
```
# Create hot fix
```sh
source build-helper.sh && createHotFix
```
```
# Have done hot fix
```sh
source build-helper.sh && haveDoneHotFix