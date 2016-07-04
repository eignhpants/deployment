
git_creds = '160b6a13-2b6d-4486-b3ff-c0b7dbe6646f'
git_url = 'git@github.com:eignhpants/'
project = 'basic-site'
app_url = "${git_url}${project}.git"

node(NODE_LABEL){
    stage "Checkout"
    git credentialsId: 'git_creds', url: app_url

    stage "Build"
    sh "make build"

    stage "Start on 5533"


    sh "pm2 start unicorn.js"

    //sh 'pm2 start bin/www'
}
