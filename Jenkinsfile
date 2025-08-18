pipeline {
    agent any

    tools {
        nodejs 'NodeJS_club'  // 确保Jenkins中已配置此工具
        maven 'Maven_club'    // 确保Jenkins中已配置此工具
    }

    environment {
        // 新项目路径配置
        BACKEND_DIR = "uio/src"          // 后端代码目录
        FRONTEND_DIR = "uio/wsssfun-ui"  // 前端代码目录
        
        // 构建优化配置
        NPM_REGISTRY = "https://registry.npmmirror.com"
        MAVEN_OPTS = "-Xmx2048m -XX:MaxPermSize=512m"
        NODE_OPTIONS = "--max-old-space-size=4096"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],  \
                    extensions: [
                        [$class: 'RelativeTargetDirectory', relativeTargetDir: 'uio'],
                        [$class: 'CleanBeforeCheckout']
                    ],
                    userRemoteConfigs: [[
                        url: 'https://github.com/ProphetSAMA/uIO.git',
                        credentialsId: 'github-credential'
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
                    sh """
                        ${tool 'Maven_club'}/bin/mvn clean package \
                        -DskipTests \
                        -s ${env.JENKINS_HOME}/settings.xml
                    """
                }
            }
            post {
                success {
                    stash name: 'backend-artifact', includes: "target/*.jar"
                }
            }
        }

        stage('Build Frontend') {
            when {
                expression { fileExists("${env.FRONTEND_DIR}/package.json") }
            }
            steps {
                dir(env.FRONTEND_DIR) {
                    sh """
                        npm config set registry ${env.NPM_REGISTRY}
                        npm ci --prefer-offline --no-audit
                        npm run build
                    """
                }
            }
            post {
                success {
                    stash name: 'frontend-artifact', includes: "dist/**"
                }
            }
        }

        stage('Archive') {
            steps {
                script {
                    unstash 'backend-artifact'
                    unstash 'frontend-artifact'
                    archiveArtifacts artifacts: "**/target/*.jar, **/dist/**", fingerprint: true
                }
            }
        }
    }

    post {
        always {
            cleanWs(
                cleanWhenAborted: true,
                cleanWhenFailure: true,
                cleanWhenSuccess: true,
                patterns: [
                    [pattern: '**/.git/**', type: 'INCLUDE'],
                    [pattern: '**/node_modules/**', type: 'EXCLUDE']
                ]
            )
        }
        failure {
            mail(
                to: 'wsssfun@icloud.com',
                subject: "构建失败: ${env.JOB_NAME}",
                body: """
                项目: ${env.JOB_NAME}
                失败阶段: ${env.STAGE_NAME}
                日志: ${env.BUILD_URL}console
                """
            )
        }
    }
}
