import hudson.model.*;
import hudson.util.*;
import jenkins.model.*;
import hudson.FilePath.FileCallable;
import hudson.slaves.OfflineCause;
import hudson.node_monitors.*;

for (node in Jenkins.instance.nodes) {
  computer = node.toComputer()
  //println(computer.nodeName + " - " + computer.getChannel())
  if (computer.getChannel() == null) continue
    
  rootPath = node.getRootPath()
  size = DiskSpaceMonitor.DESCRIPTOR.get(computer).size
  roundedSize = size / (1024 * 1024 * 1024) as int
  println("node: " + node.getDisplayName() + ", free space on " + rootPath + " is " + roundedSize + "GB")
  
  if(roundedSize < 10) {
    computer.setTemporarilyOffline(true, new hudson.slaves.OfflineCause.ByCLI("disk cleanup"))
    //println(Jenkins.instance.items)
    for (item in Jenkins.instance.getAllItems(TopLevelItem)) {
      if (item instanceof com.cloudbees.hudson.plugins.folder.Folder) {
        println(item.getFullDisplayName() + " is a folder, skipping")
        continue
        }
      
      if(item instanceof Job && item.isBuilding()) {
      	println(item.getFullDisplayName() + " is currently building, skipping")
        continue
        }
      jobName = item.getFullDisplayName()
      //println(itemName)
      
      println("cleaning workspaces of job " + jobName)
      
      if(("${item.class}".contains('WorkflowJob'))) {
      	println(item.getFullDisplayName() + " is a WorkFlowJob, skipping")
        continue
        }
      
                
      if(("${item.class}".contains('WorkflowMultiBranchProject'))) {
      	println(item.getFullDisplayName() + " is a WorkflowMultiBranchProject, skipping")
        continue
        }

      if(("${item.class}".contains('ExternalJob'))) {
      	println(item.getFullDisplayName() + " is a ExternalJob, skipping")
        continue
        }
      
      workspacePath = node.getWorkspaceFor(item)
      if(workspacePath == null) {
        println("oops, workspace path not found")
        continue
        }
      
      println("workspace is " + workspacePath)
      
      customWorkspace = item.getCustomWorkspace()
      if(customWorkspace) {
      	workspacePath = node.getRootPath().child(customWorkspace)
        println("custom workspace is " + workspacePath)
        }
      
      pathAsString = workspacePath.getRemote()
      if(workspacePath.exists()) {
      	workspacePath.deleteRecursive()
        println("deleted from location " + pathAsString + " on " + node.getDisplayName())
        } else {
        println(".... nothing to delete at " + pathAsString)
        }
      }
    computer.setTemporarilyOffline(false, null)
    }
  }