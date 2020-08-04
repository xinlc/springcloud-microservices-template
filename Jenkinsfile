#!groovy
pipeline {
	agent none

	parameters {
		choice(
				description: '你需要选择哪个模块进行构建 ?',
				name: 'modulename',
				choices: ['1:all', '2:auth', '3:gateway', '4:order', '5:logistics']
		)

		choice(
				description: '你需要选择哪个环境进行构建 ?',
				name: 'profile',
				choices: ['development', 'testing', 'production']
		)

		string(
				description: '你要构建的版本 ?',
				name: 'pversion',
				defaultValue: '1.0.0',
		)
	}

	stages {
		stage('Build docker') {
			agent any
			environment {
				ACCESS_DOCKER = credentials('docker-register')
				REGISTRY_URL = 'registry-vpc.cn-beijing.aliyuncs.com'
			}
			steps {
				sh "docker login -u ${ACCESS_DOCKER_USR} -p ${ACCESS_DOCKER_PSW} ${REGISTRY_URL}"
				sh "./jenkins-deploy.sh ${params.modulename} ${params.profile} ${params.pversion}"
			}
		}
	}
}
