pipeline {
    agent any

    tools {
        maven 'Maven_3'
    }

    triggers {
        cron('30 11 * * 1')
    }

    parameters {
    string(name: 'SELENIUM_BROWSER', defaultValue: 'CHROME')

    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Projet local - pas de checkout Git'
            }
        }

        stage('Build & Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                bat "mvn test -Dtags=@POEI2-717"
                echo 'Execution des tests Cucumber via Maven...'
                bat 'chcp 65001'
                bat 'mvn test -Dtags=@POEI2-717'
                }

            }
        }

        stage('Check JSON') {
            steps {
                echo "Liste du dossier target :"
                bat 'dir target'
            }
        }


        stage('Publish Results to Xray') {
            steps {
                echo 'Publication des résultats vers Xray...'

                // Authentification Xray
                bat '''
            curl -H "Content-Type: application/json" ^
                 -X POST https://xray.cloud.getxray.app/api/v2/authenticate ^
                 -d "{\\"client_id\\": \\"19B21B3A87A84FB187E8ED0C929260EA\\", \\"client_secret\\": \\"8f8d1ba97004498878ddc1f48835d9a997dea57056fe756891ffbd7e1ac68177\\"}" > token.txt
            '''

                // Envoi du fichier JSON
         bat """
                curl -H "Content-Type: application/json" ^
                     -H "Authorization: Bearer %XRAY_TOKEN%" ^
                     -X POST https://xray.cloud.getxray.app/api/v2/import/execution/cucumber ^
                     --data-binary @target\\cucumber.json ^
                     -F testPlanKey=POEI2-710
                """

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
