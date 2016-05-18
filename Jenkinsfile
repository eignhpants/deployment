
/*
* MAIN LOGIC FOR BRANCHES
*/

// For convenience
def build_repo = 'git@github.turbine.com:CSI/turbine-collectd-pipeline.git'
def target_repo = 'git@github.turbine.com:CSI/turbine-collectd-fpm.git'
def instance_launcher = 'git@github.turbine.com:CSI/instance-launcher.git'
def creds = 'fff57b70-0b76-455b-be60-686d283ab08c'
String build_number = env['BUILD_NUMBER']

// This are the pipeline modules, defined here for scope
def common
def test
def publish

/*
* IMPORTANT
* Must be set per branch, eg if this is the master branch, this value should be master
*/
String branch = 'rhel-building'


if(branch == 'master'){
    repo_target = 'dev'
} else if (branch == 'release'){
    repo_target = 'testing'
}



node('docker'){

    // Checkout pipeline files
    git branch: branch, credentialsId: creds, url: build_repo

    // Load build modules
    common = load 'common.groovy'
    test = load 'test.groovy'
    publish = load 'publish.groovy'
}


if(branch ==  'rhel-building'){
    node('docker'){

        // This will checkout the target repo, and then
        stage 'Build Packages'
        sh 'rm -f output/*'
        common.checkout("master", creds, target_repo)
        common.build(build_number)
    }
}


/*
* Logic control
* These if statements will determine what will happen per branch
*/
if(branch == 'master' || branch == 'release'){

    node('docker'){

        // This will checkout the target repo, and then
        stage 'Build Packages'
        sh 'rm -f output/*'
        common.checkout("master", creds, target_repo)
        common.build(build_number)

        // Before moving on, we need to add the new packages to staging
        // Deploy to production is a different thing
        stage "Update ${repo_target} Repo"
        build "package-adder/publish-${repo_target}"

    }

    node('docker'){

        // Checkout turbine-collectd-fpm
        common.checkout(branch, creds, target_repo)

        // Run the install tests from staging, the arguments will match to the
        // names of build/containers/Dockerfile.some-pattern
        stage 'Debian Jessie Install'
        test.install_tests('debian', 'jessie')
        stage 'Ubuntu Trusty Install'
        test.install_tests('ubuntu', 'trusty')
        //stage 'Centos 7 Install'
        //test.install_tests('centos', '7')
    }

    node('docker'){

        // Checkout the instance launcher
        common.checkout("master", creds, instance_launcher)

        // this credenstialsId is for turbine games
        // IMPORRTANT: If you want to launch in another account you will need to specify
        // the following in aws-instance-launcher: -subnet, -sec-grp, and -ami
        withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: '17ea0932-2700-433c-8cbe-83a0d9dd8052', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
            // Launch one off pipeline instance
            stage "Initial Cloud Test"
            sh 'ls -la'
            sh "make run SELF_DESTRUCT=30 REPO=dev"
        }

        sh 'rm -rf output/*.deb && rm -rf output/*.rpm'
        stash 'verification'

    }

    stage 'Initial Cloud Tests'
    sleep 300

    node('docker') {

        unstash 'verification'
        //common.checkout(branch, creds, target_repo)

        // Run the verification script on a container
        sshagent(['4c6fbcfe-9de8-4d7d-ae6e-237884b8fe82']) {
            sh '. ./output/log; ssh -o StrictHostKeyChecking=no ubuntu@${PRIVATE_IP} "sudo bash" < verify.sh'
        }

    }

}

if(branch == 'release'){
    //build.build(branch)

    node('docker'){


        // Launch an overnight instance
        stage "Overnight Cloud Tests"

        // Checkout the instance launcher
        common.checkout("master", creds, instance_launcher)

        // this credenstialsId is for turbine games
        // IMPORRTANT: If you want to launch in another account you will need to specify
        // the following in aws-instance-launcher: -subnet, -sec-grp, and -ami
        withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: '17ea0932-2700-433c-8cbe-83a0d9dd8052', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
            // Launch one off pipeline instance

            sh "make run SELF_DESTRUCT=1500 REPO=testing"

        }


        sh 'rm -rf output/*.deb && rm -rf output/*.rpm'
        stash 'bake'

    }


    // Sleep for 18 hours, we really only need to wait overnight, whis way if you build at end of day
    // it will check at a reasonable time the next work day
    sleep time: 18, unit: 'HOURS'


    node('docker') {


        unstash 'bake'
        //common.checkout(branch, creds, target_repo)

        // Run the verification script on a container
        sshagent(['4c6fbcfe-9de8-4d7d-ae6e-237884b8fe82']) {
            sh '. ./output/log; ssh -o StrictHostKeyChecking=no ubuntu@${PRIVATE_IP} "sudo bash" < verify.sh'
        }



        stage 'Promote Build to Stable'
        mail to: 'BOS_CSI@turbine.com',
            subject: "A Release job is waiting for approval!",
            body: "Please go to ${env.BUILD_URL} and verify the build"

        input message: 'Mark build as Stable?',
            ok: 'Approve',
        parameters: [[$class: 'TextParameterDefinition', defaultValue: 'Approved', description: '', name: 'Notes']],
        submitter: 'dsasser'


        stage "Publish Stable Package"
        build "package-adder/publish-stable"

    }


}




