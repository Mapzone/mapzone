def repoProjectsStr = '''[
   { "name": "io.mapzone.controller",
      "repository" : "mapzone",
      "branch": "master",
      "ant": "true",
      "successor": "io.mapzone.product"
   },
   { "name": "io.mapzone.arena",
      "repository" : "mapzone",
      "branch": "master",
      "ant": "false",
      "successor": "io.mapzone.product"
   },
   { "name": "io.mapzone.parent",
      "repository" : "mapzone",
      "branch": "master",
      "ant": "false",
      "successor": "io.mapzone.product"
   },
   { "name": "io.mapzone.product",
      "repository" : "mapzone",
      "branch": "master",
      "ant": "false",
      "archiveProduct": "true"
   }
]'''

def jsonSlurper = new groovy.json.JsonSlurper()
def repoProjects = jsonSlurper.parseText(repoProjectsStr)

repoProjects.each {
   def projectName = it.name
   def branchName = it.branch
   def folder = (it.folder != null) ? it.folder + '/' : ''
   def jobName = it.repository.replace('mapzone-', '') + '_' + it.name + '_' + it.branch
   def repoName = it.repository
   def useAnt = (it.ant != null && it.ant == 'false') ? false : true
   def useSass = (it.sass != null && it.sass == 'true') ? true : false
   def useJUnit = (it.junit != null && it.junit == 'true') ? true : false
   def successor = it.successor
   def archiveProduct = (it.archiveProduct != null && it.archiveProduct == 'true') ? true : false

   mavenJob(jobName) {
     logRotator(-1, 5)
     blockOnUpstreamProjects()
       properties {
          githubProjectUrl('git@github.com:Mapzone/' + repoName + '.git')
       }
       /*environmentVariables {
          envs(snapshotDeployRepo: 'http://build.mapzone.io/nexus/content/repositories/polymap4-snapshots/',
          releaseDeployRepo: 'http://build.mapzone.io/nexus/content/repositories/polymap4-releases/',
          polymap4_target: 'file:/home/jenkins/.jenkins/jobs/polymap4-targetplatform/lastStable/archive/polymap4_target',
          branchName: branchName)
       }*/
        scm {
           git {
              branch('refs/heads/' + branchName)
              remote {
                 url('git@github.com:Mapzone/' + repoName + '.git')
                 credentials('a7a16d8e-fac0-45df-973b-523ff9c81738')
                 refspec('+refs/heads/' + branchName + ':refs/remotes/origin/' + branchName)
              }
             extensions {
               cleanBeforeCheckout()
               //wipeOutWorkspace()
             }  
           }
        }
        triggers {
          githubPush()
          snapshotDependencies(true)
        }
        preBuildSteps {
            if(useAnt) {
                ant {
                   antInstallation('ANT 1.9.6')
                   buildFile(folder + projectName + '/getjars.build.xml')
                   target('build')
                   prop('nexusURL', 'http://build.mapzone.io/nexus/content/groups/public')
                }
             }
            if(useSass) {
                ant {
                   antInstallation('ANT 1.9.6')
                   buildFile(folder + projectName + '/sass.build.xml')
                   target('build')
                }
             }
        }
        //       maven {
                mavenInstallation('Maven 3.3.3')
                  providedSettings('JenkinsMavenSettings')
                  rootPOM(folder + projectName + '/pom.xml')
                  goals('clean')
                  if (!archiveProduct) {
                    goals('deploy')
                  }
                  /*properties (
                     snapshotDeployRepo: 'http://build.mapzone.io/nexus/content/repositories/polymap4-snapshots/', 
                     releaseDeployRepo: 'http://build.mapzone.io/nexus/content/repositories/polymap4-releases/', 
                     polymap4_target: 'file:/home/jenkins/.jenkins/jobs/polymap4-targetplatform/lastStable/archive/polymap4_target',
                     branchName: branchName
                  )*/
                  mavenOpts('-Dmaven.test.failure.ignore=true')
                mavenOpts('-DsnapshotDeployRepo=http://localhost:7581/nexus/content/repositories/polymap4-snapshots/') 
                mavenOpts('-DreleaseDeployRepo=http://localhost:7581/nexus/content/repositories/polymap4-releases/') 
                mavenOpts('-Dpolymap4_target=file:/home/jenkins/.jenkins/jobs/polymap4-targetplatform/lastStable/archive/polymap4_target')
                mavenOpts('-DmapzoneSnapshotDeployRepo=http://localhost:7581/nexus/content/repositories/mapzone-snapshots/') 
                mavenOpts('-DmapzoneReleaseDeployRepo=http://localhost:7581/nexus/content/repositories/mapzone-releases/')
                mavenOpts('-DbranchName=' + branchName)
        //       }
        //}
        postBuildSteps {
          
        }
        publishers {
            if(useJUnit) {
                archiveJunit('**/TEST-*.xml') {
                    testDataPublishers {
                        allowClaimingOfFailedTests()
                        publishTestStabilityData()
                    }
                }
            }
            /*if(successor != null) {
                def successorProject = repoProjects.find { it.name == successor }
                def successorJobName = successorProject.repository.replace('polymap4-', '') + '_' + successorProject.name + '_' + successorProject.branch
                downstream(successorJobName, 'UNSTABLE')
            }*/
          slackNotifications {
                notifyFailure()
                notifyBackToNormal()
          }
        }
    }
}
