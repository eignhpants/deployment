
git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
app_url = 'git@github.com:eignhpants/basic-site.git'

node('app-server'){
    stage "Checkout"
    //git credentialsId: git_creds, url: app_url

    sh "make build"
    //sh 'pm2 start bin/www'
}