


// git (github.com key): e6a36c15-1342-4105-9ef8-896857a5781c
//github-key (github-key): 160b6a13-2b6d-4486-b3ff-c0b7dbe6646f


git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
git_url = 'git@github.com:eignhpants/'
project = 'basic-site'
app_url = "${git_url}${project}.git"

node(NODE_LABEL){

    sh 'ls -la'

    stage "Checkout"
    git branch: 'master', credentialsId: git_creds, url: app_url

    stage "Build"
    sh "make build"
    sh 'ls -la'

    withEnv([DEPLOY_PORT=3334]){
        stage "Start on ${DEPLOY_PORT}"
        sh "pm2 start basic.js"
        sh 'ls -la'

    }
    //sh 'pm2 start bin/www'
}