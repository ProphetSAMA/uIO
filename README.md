# 校园电费查询平台

## 💦项目简介

😘这是一个⚡宿舍电费查询⚡项目，主要功能是查询宿舍电费  
😁可在注册时使用级联选择器选择具体的宿舍，也可以在个人信息中修改  
😊用户可以通过该平台查询自己的电费余额，以及查询电费的消耗情况  
👌信息每小时更新一次，并且有详细的📊数据表格和直观的📈折线图 

😎该项目旨在方便用户查询自己的电费信息，免去繁琐的操作

## 🔗在线展示
 👉 [uIO](https://uio.ink)
    
 👉 **https://uio.ink**

## 🕹️项目功能

- 数据可视化
  - 展示电费余额
  - 展示饼图、柱状图、事件     
  
  
- 电费查询
  - 查询电费余额
  - 查询电费消耗情况     
  
  
- 用户管理
  - 用户注册
  - 用户登录
  - 忘记密码
  - 重置密码
  - 修改密码
  - 退出登录
  - 修改房间信息
  - 修改个人信息

## 🛫技术栈

- **后端**: SpringBoot, MySQL, MyBatis-Plus, Redis
- **前端**: Vue.js, Pinia, Element-Plus, Axios
- **构建工具**: Maven
- **其他**: Lombok, Log4j, Git

## 🏇快速开始

### 前提条件

- 安装 [JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- 安装 [Maven](https://maven.apache.org/install.html)
- 安装 [Node.js](https://nodejs.org/en/download/)
- 安装 [MySQL](https://dev.mysql.com/downloads/installer/)
- 安装 [Redis](https://redis.io/)

### 后端启动

1. 克隆项目到本地:
    ```sh
    git clone https://github.com/ProphetSAMA/uIO.git
    cd uIO
    ```

2. 配置数据库:
    - 创建数据库 `uIO`
    - 导入 `src/main/resources/sql/schema.sql` 和 `src/main/resources/sql/data.sql`

3. 修改数据库配置:
    - 编辑 `src/main/resources/application.yml` 文件，配置数据库连接信息

4. 启动后端服务:
    ```sh
    mvn spring-boot:run
    ```

### 前端启动

1. 进入前端目录:
    ```sh
    cd wsssfun-ui
    ```

2. 安装依赖:
    ```sh
    npm install
    ```

3. 启动前端服务:
    ```sh
    npm run serve
    ```

### 访问平台

在浏览器中打开 [http://localhost:8080](http://localhost:8080) 访问平台

## 参考文档

- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Vue.js 官方文档](https://vuejs.org/v2/guide/)
- [Element-Plus 官方文档](https://element-plus.org/zh-CN/)

## 贡献

欢迎提交 Issue 和 Pull Request 来帮助我们改进项目

## 许可证

本项目基于 MIT 许可证开源，详细信息请参阅 [LICENSE](./LICENSE) 文件
