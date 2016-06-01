
git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
app_url = 'git@github.com:eignhpants/basic-site.git'

node('app-server'){
    stage "Checkout"
    git credentialsId: '160b6a13-2b6d-4486-b3ff-c0b7dbe6646f', url: 'git@github.com:eignhpants/basic-site.git'

    sh "ls -la"
    //sh 'pm2 start bin/www'
}