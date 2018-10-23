import hudson.FilePath;

def getNodesByLabel(labels) {
  def lgroup = Jenkins.instance.getLabel(labels)
  def nodesByLabel = []
  def nodes = lgroup.getNodes()
  if (nodes.size() > 0) {
    for (node in nodes)
      nodesByLabel.push(node)
  }
  return nodesByLabel
}

//To print all the list of slaves with build and java as labels
//println(getNodesByLabel("build&&java"))

//To print all the list of slaves setup on Jenkins Master
//println(hudson.model.Hudson.instance.slaves)

def cleanupSlavesByLabel(label) {
  for (slave in getNodesByLabel(label)) {
    if(slave.getChannel() == null) {
      println("... no active connection available with " + slave.getNodeName() + ", skipping")
      continue
    }
    
    println("cleaning up workspaces for " + slave.getNodeName())
    FilePath fp = slave.createPath(slave.getRootPath().toString() + File.separator + "workspace"); 
    fp.deleteRecursive();
    println(fp)
  }
}

# Driver - Usage
cleanupSlavesByLabel("build&&java")
