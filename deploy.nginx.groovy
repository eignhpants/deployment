git_creds = 'e6a36c15-1342-4105-9ef8-896857a5781c'
git_url = 'git@github.com:eignhpants/'
project = 'basic-site'
app_url = "${git_url}${project}.git"
nginx_dir = "/etc/nginx/"


node(NODE_LABEL){

    stage 'Make Nginx Temnplate'
    withEnv(['SERVER_NAME=whatever, PORT=whatever']) {
    // some block
        sh "envsubst < nginx.template.conf > Dockerfile"
    }

    //sh 'pm2 start bin/www'
}





/*


server {
    listen 80;

    server_name ${SERVER_NAME} www.${SERVER_NAME};
    access_log /var/log/nginx/dev.example.log;

    location / {
        proxy_pass http://10.132.137.72:${PORT};
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
    }

}




*/