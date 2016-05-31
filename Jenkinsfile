
git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
app_url = 'git@github.com:eignhpants/basic-site.git'

node('app-server'){
    stage "Checkout"
    git credentialsId: 'e6a36c15-1342-4105-9ef8-896857a5781c', url: 'git@github.com:eignhpants/basic-site.git'

    sh "ls -la"
    //sh 'pm2 start bin/www'
}