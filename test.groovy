import jenkins.util.*;
 import jenkins.model.*;

 def thr = Thread.currentThread();
 def currentBuild = thr?.executable;
 def workspace = currentBuild.getModuleRoot().absolutize().toString();

#  def project = new XmlSlurper().parse(new File("$workspace/build.gradle"))
#  def version = project.version.toString()

 def future_build_number_file = new File("$workspace/../","pipelineNumber")
 def future_build_version_file = new File("$workspace/../","pipelineVersion")
 def new_future_build_number_file = 0
 def pipeline_number = null

 if( !future_build_version_file.exists() || !future_build_version_file.text.equals(${version}) ) {
  new_future_build_number_file = 1
  def new_future_build_version_file = ${version}
  pipeline_number = ${version} + '.1'

  future_build_version_file.createNewFile()
  def w = future_build_version_file.newWriter()
  w << "$new_future_build_version_file"
  w.close()
 } else if(future_build_version_file.text.equals(${version})){
  if( future_build_number_file.exists() ) {
      new_future_build_number_file = future_build_number_file.text.toInteger()+1
  }else{
      new_future_build_number_file = 1
  }
  pipeline_number = ${version} + '.' + new_future_build_number_file
 }

 future_build_number_file.createNewFile()
 def w = future_build_number_file.newWriter()
 w << "$new_future_build_number_file"
 w.close()

 def map = [PIPELINE_NUMBER: pipeline_number]
 return map
