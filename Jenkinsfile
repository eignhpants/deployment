
git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
app_url = 'git@github.com:eignhpants/pit-fighter.git'

node('app-server'){
    stage "Checkout"
    git credentialsId: '160b6a13-2b6d-4486-b3ff-c0b7dbe6646f', url: app_url

    sh "ls -la"

    stage "Build"
    sh "make build"

    stage "Start on 5533"

    withEnv([PORT=5533]){
        sh "pm2 start app.js"
    }
    //sh 'pm2 start bin/www'
}
