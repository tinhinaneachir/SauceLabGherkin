pipeline {
    agent any

    tools {
        maven 'Maven_3'
    }

    triggers {
        cron('30 11 * * 1')
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Projet local - pas de checkout Git'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Execution des tests Cucumber via Maven...'
                bat 'chcp 65001'
                bat 'mvn test -Dtags=@POEI2-717'
            }
        }

        stage('Publish Results to Xray') {
            steps {
                echo 'Publication des résultats vers Xray...'

                // Authentification Xray
                bat '''
                curl -H "Content-Type: application/json" ^
                     -X POST https://xray.cloud.getxray.app/api/v2/authenticate ^
                     -d "{\\"client_id\\": \\"%XRAY_CLIENT_ID%\\", \\"client_secret\\": \\"%XRAY_CLIENT_SECRET%\\"}" > token.txt
                '''

                // Envoi du fichier JSON
                bat '''
                set /p TOKEN=<token.txt
                curl -H "Content-Type: application/json" ^
                     -H "Authorization: Bearer %TOKEN%" ^
                     -X POST https://xray.cloud.getxray.app/api/v2/import/execution/cucumber ^
                     --data-binary @target/cucumber.json
                '''
            }
        }
    }

    post {
        success {
            echo 'Tests exécutés avec succès 🎉'
        }

        failure {
            echo 'Des tests ont échoué ❌'
        }
    }
}
