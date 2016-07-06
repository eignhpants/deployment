
// git (github.com key): e6a36c15-1342-4105-9ef8-896857a5781c
//github-key (github-key): 160b6a13-2b6d-4486-b3ff-c0b7dbe6646f

git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
git_url = 'git@github.com:eignhpants/'
project = 'sweetbasil.rocks'
app_url = "${git_url}${project}.git"

node(NODE_LABEL){


    stage "Checkout"
    git branch: 'master', credentialsId: git_creds, url: app_url

    stage "Build"
    sh "make build"

    stage "Deploy"
    sh "ls -l"
    sh "pm2 start -f sweetbasil.js"
    //sh 'pm2 start bin/www'
}