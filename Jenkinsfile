pipeline {
    agent any

    tools {
        maven 'Maven_3'
    }

    environment {
        XRAY_CLIENT_ID     = '19B21B3A87A84FB187E8ED0C929260EA'
        XRAY_CLIENT_SECRET = '8f8d1ba97004498878ddc1f48835d9a997dea57056fe756891ffbd7e1ac68177'
        TEST_PLAN_KEY      = 'POEI2-710'
        TEST_KEYS          = 'POEI2-717'
    }

    stages {

        stage('Checkout') {
            steps {
                // Même pour un projet "local", Jenkins doit avoir le code
                checkout scm
            }
        }

        stage('Get Xray Token') {
            steps {
                bat '''
                curl -H "Content-Type: application/json" ^
                     -X POST https://xray.cloud.getxray.app/api/v2/authenticate ^
                     -d "{\\"client_id\\": \\"%XRAY_CLIENT_ID%\\", \\"client_secret\\": \\"%XRAY_CLIENT_SECRET%\\"}" ^
                     > token.txt
                '''
            }
        }

        stage('Export Features from Xray') {
            steps {
                bat '''
                set /p TOKEN=<token.txt

                curl -H "Authorization: Bearer %TOKEN%" ^
                     "https://xray.cloud.getxray.app/api/v2/export/cucumber?keys=%TEST_KEYS%" ^
                     -o features.zip
                '''

                powershell '''
                $dest = "src/test/resources/features"
                if (Test-Path $dest) { Remove-Item $dest -Recurse -Force }
                Expand-Archive -Path features.zip -DestinationPath $dest -Force
                '''
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Publish Results to Xray') {
            steps {
                bat '''
                set /p TOKEN=<token.txt

                curl -H "Authorization: Bearer %TOKEN%" ^
                     -F "file=@target/cucumber.json" ^
                     -F "info={\\"testPlanKey\\":\\"%TEST_PLAN_KEY%\\"}" ^
                     https://xray.cloud.getxray.app/api/v2/import/execution/cucumber
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/**', fingerprint: true
        }
        success {
            echo 'Pipeline terminé avec succès ✅'
        }
        failure {
            echo 'Pipeline en échec ❌'
        }
    }
}
