//https://wiki.jenkins.io/display/JENKINS/Manually+run+log+rotation+on+all+jobs

import jenkins.model.*
Jenkins.instance.getAllItems(AbstractProject.class)
.findAll { it.logRotator }
  .each {
    println("Running LogRotate on " + it.getDisplayName())
    it.logRotator.perform(it)
    }
