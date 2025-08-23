pipeline {
    agent any

    options {
        timeout(time: 60, unit: 'MINUTES')
        disableConcurrentBuilds() // 禁止并行构建，减少内存压力
    }

    environment {
        BACKEND_DIR = "uio/src"
        FRONTEND_DIR = "uio/wsssfun-ui"
        NPM_REGISTRY = "https://registry.npmmirror.com"
        
        // 内存限制（关键优化）
        MAVEN_OPTS = "-Xmx512m -XX:MaxPermSize=256m"  // 大幅减少Maven内存
        NODE_OPTIONS = "--max-old-space-size=1024"    // Node.js限制1GB
        JAVA_OPTS = "-Xmx256m"                        // Jenkins构建器内存限制
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [
                        [$class: 'CloneOption', depth: 1, shallow: true], // 浅克隆
                        [$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [
                            [$path: 'uio/src/'],
                            [$path: 'uio/wsssfun-ui/']
                        ]], // 只拉取需要的目录
                        [$class: 'CleanBeforeCheckout']
                    ],
                    userRemoteConfigs: [[
                        url: 'https://github.com/ProphetSAMA/uIO.git'
                        // 如果仓库公开，可以不用凭据
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
                        # 使用系统Maven，避免工具安装开销
                        mvn clean package \
                        -DskipTests \
                        -Dmaven.test.skip=true \
                        -Dmaven.compile.fork=false \
                        -T 1 \
                        -o \
                        -s ${env.JENKINS_HOME}/settings.xml
                    """
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
                        # 使用系统Node.js，避免工具安装开销
                        npm config set registry ${env.NPM_REGISTRY}
                        npm install --no-optional --no-audit --no-fund --prefer-offline
                        npm run build --if-present
                    """
                }
            }
        }

        stage('Archive') {
            steps {
                script {
                    // 只归档必要文件
                    def jarFiles = findFiles(glob: "${env.BACKEND_DIR}/target/*.jar")
                    def distFiles = findFiles(glob: "${env.FRONTEND_DIR}/dist/**")
                    
                    if (jarFiles) {
                        archiveArtifacts artifacts: "${env.BACKEND_DIR}/target/*.jar", fingerprint: true
                    }
                    if (distFiles) {
                        archiveArtifacts artifacts: "${env.FRONTEND_DIR}/dist/**", fingerprint: true
                    }
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
                deleteDirs: true,
                patterns: [
                    [pattern: '**/.git/**', type: 'INCLUDE'],
                    [pattern: '**/node_modules/**', type: 'EXCLUDE'], // 保留node_modules
                    [pattern: '**/target/**', type: 'EXCLUDE']        // 保留target
                ]
            )
        }
        failure {
            // 轻量级通知，避免邮件服务消耗资源
            echo "构建失败！详情查看: ${env.BUILD_URL}console"
        }
    }
}
