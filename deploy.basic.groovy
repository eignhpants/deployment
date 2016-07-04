


// git (github.com key): e6a36c15-1342-4105-9ef8-896857a5781c
//github-key (github-key): 160b6a13-2b6d-4486-b3ff-c0b7dbe6646f


git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
git_url = 'git@github.com:eignhpants/'
project = 'basic-site'
app_url = "${git_url}${project}.git"

node(NODE_LABEL){
    stage "Checkout"
    git credentialsId: git_creds, url: app_url

    stage "Build"
    sh "make build"

    stage "Start on 5533"

    withEnv([DEPLOY_PORT=3334]){
        sh "pm2 start basic.js"
    }
    //sh 'pm2 start bin/www'
}