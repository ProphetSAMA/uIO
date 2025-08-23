pipeline {
    agent any

    environment {
        BACKEND_DIR = "uio/src"
        FRONTEND_DIR = "uio/wsssfun-ui"
        NPM_REGISTRY = "https://registry.npmmirror.com"
        MAVEN_OPTS = "-Xmx512m -XX:MaxPermSize=256m"
        NODE_OPTIONS = "--max-old-space-size=1024"
        JAVA_OPTS = "-Xmx256m"
    }

    options {
        disableConcurrentBuilds()
        timeout(time: 60, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    extensions: [
                        [$class: 'CloneOption', depth: 1, shallow: true],
                        [$class: 'CleanBeforeCheckout']
                    ],
                    userRemoteConfigs: [[
                        url: 'https://github.com/ProphetSAMA/uIO.git'
                    ]]
                ])
            }
        }

        stage('Build Backend') {
            when {
                expression { fileExists("${env.BACKEND_DIR}/pom.xml") }
            }
            steps {
                dir(env.BACKEND_DIR) {
                    sh '''
                        mvn clean package \
                          -DskipTests \
                          -Dmaven.test.skip=true \
                          -Dmaven.compile.fork=false \
                          -T 1 \
                          -o \
                          -s ${JENKINS_HOME}/settings.xml
                    '''
                }
            }
        }

        stage('Build Frontend') {
            when {
                expression { fileExists("${env.FRONTEND_DIR}/package.json") }
            }
            steps {
                dir(env.FRONTEND_DIR) {
                    sh '''
                        npm config set registry ${NPM_REGISTRY}
                        npm install --no-optional --no-audit --no-fund --prefer-offline
                        npm run build --if-present
                    '''
                }
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: "${env.BACKEND_DIR}/target/*.jar", fingerprint: true
                archiveArtifacts artifacts: "${env.FRONTEND_DIR}/dist/**", fingerprint: true
            }
        }
    }

    post {
        always {
            cleanWs()
            echo '构建失败！详情查看: https://jenkins.wsss.fun/job/uIO/'
        }
    }
}
