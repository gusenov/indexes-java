#!/bin/bash
set -x # echo on

~/.gradle/wrapper/dists/gradle-3.5-rc-2-bin/7ktl4k9rdug30mawecgppf5ms/gradle-3.5-rc-2/bin/gradle uploadArchives

# If all went fine - go to the https://oss.sonatype.org/#stagingRepositories and look for your ‘staging’ library.
# It should be somewhere at the end of the list. 
# Select it, and press ‘Close’ button. 
# Closing a library actually means that you’re ready to release it. 
# Another option is ‘dropping’ a library, which means removing it from the list. 
# If closing went fine - you should see a ‘Release’ button active.

# Press it and then get back to JIRA and post a comment there that you promoted you library.

# [OSSRH-34983] indexes-java - Sonatype JIRA
# https://issues.sonatype.org/browse/OSSRH-34983
#  Only one JIRA issue per top-level groupId is necessary.
#  This looks like a duplicate of OSSRH-34127 (https://issues.sonatype.org/browse/OSSRH-34127), so let's track progress in that issue.

# After that you should get a response from Sonatype that your library will be available in ~10 minutes and it will be synced with the Maven Central in the next few hours.
