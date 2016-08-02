


// git (github.com key): e6a36c15-1342-4105-9ef8-896857a5781c
//github-key (github-key): 160b6a13-2b6d-4486-b3ff-c0b7dbe6646f


git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
git_url = 'git@github.com:eignhpants/'
project = 'aligned-software.com'
app_url = "${git_url}${project}.git"
deployment_url = "${git_url}deployment.git"




node(NODE_LABEL){

    stage "Build"
    git branch: 'master', credentialsId: git_creds, url: app_url
    sh "make build-docker"

}




node(NODE_LABEL){

    stage "Checkout Deployment"
    git branch: "master", credentialsId: git_creds, url: deployment_url

    stage "Deploy Blog"
    sh "ls -la"
    //sh "docker-compose -f alignedsoftware.com.yml up -d"
    //sh "docker stop iancullinane.com"
    //sh "docker run -d \
    //    --name iancullinane.com \
    //    -p 2368:2368 -v /var/lib/ghost/iancullinane:/var/lib/ghost \
    //    --restart=always \
    //    ghost"

    //sh 'pm2 start bin/www'
}