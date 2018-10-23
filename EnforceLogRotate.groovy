import hudson.model.*
disableChildren(Hudson.instance.items)
def disableChildren(items) {
	def daysToKeep = 10
	def numToKeep = 5
	def artifactDaysToKeep = 2
	def artifactNumToKeep = 2
	for (item in items) {
	    if (item.class.canonicalName == 'org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject') {
			println("=====================")
			println("JOB: " + item.name)
			println("Job type: " + item.getClass())
            def count = 0
			for (job in item.getAllJobs()) {
				println("----JOB: " + job.name)
				println("----Job type: " + job.getClass())
				println(job.getBuildDiscarder())
              	println("skipping")
                count++
			}
        } else if (item.class.canonicalName == 'org.jenkinsci.plugins.pipeline.multibranch.defaults.PipelineMultiBranchDefaultsProject') {
        	println("=====================")
			println("JOB: " + item.name)
			println("Job type: " + item.getClass())
            def count = 0
			for (job in item.getAllJobs()) {
				println("----JOB: " + job.name)
				println("----Job type: " + job.getClass())
				println(job.getBuildDiscarder())
              	println("skipping")
                count++
			}
        } else if (item.class.canonicalName != 'com.cloudbees.hudson.plugins.folder.Folder') {
			println("=====================")
			println("JOB: " + item.name)
			println("Job type: " + item.getClass())
			if(item.buildDiscarder == null) {
				println("No BuildDiscarder")
				println("Set BuildDiscarder to LogRotator")
			} else {
				println("BuildDiscarder: " + item.buildDiscarder.getClass())
				println("Found setting: " + "days to keep=" + item.buildDiscarder.daysToKeepStr + "; num to keep=" + item.buildDiscarder.numToKeepStr + "; artifact day to keep=" + item.buildDiscarder.artifactDaysToKeepStr + "; artifact num to keep=" + item.buildDiscarder.artifactNumToKeepStr)
				println("Set new setting")
				println("days to keep="+ daysToKeep + "; num to keep=" + numToKeep + "; artifacts days to keep=" + artifactDaysToKeep + "; aftifacts num to keep=" + artifactNumToKeep)
			}
			item.buildDiscarder = new hudson.tasks.LogRotator(daysToKeep, numToKeep, artifactDaysToKeep, artifactNumToKeep)
			item.save()
			println("done")
		} else {
			disableChildren(((com.cloudbees.hudson.plugins.folder.Folder) item).getItems())
		}
	}
}
