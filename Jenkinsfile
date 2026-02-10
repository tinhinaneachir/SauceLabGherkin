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
                bat '''
                curl -H "Content-Type: application/json" ^
                -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnQiOiJiNmNhZGQwNS1lMzQxLTNmMTctYjU1Zi00OTM0MTI4MWQ4MmEiLCJhY2NvdW50SWQiOiI2MzVhNDAwNWM5N2Y1NDczYWY3MDcwYWYiLCJpc1hlYSI6ZmFsc2UsImlhdCI6MTc3MDYzODU0NywiZXhwIjoxNzcwNzI0OTQ3LCJhdWQiOiIxOUIyMUIzQTg3QTg0RkIxODdFOEVEMEM5MjkyNjBFQSIsImlzcyI6ImNvbS54cGFuZGl0LnBsdWdpbnMueHJheSIsInN1YiI6IjE5QjIxQjNBODdBODRGQjE4N0U4RUQwQzkyOTI2MEVBIn0.1wmr0pKsQ1NWQDVrUrB38pSfj_UN7bARB2Hj1C5v0mc" ^
                -X POST https://xray.cloud.getxray.app/api/v2/import/execution/cucumber ^
                --data-binary @target\\cucumber.json
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
