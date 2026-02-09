pipeline {
    agent any

       tools {
            maven 'Maven_3'
        }

    stages {

        stage('Checkout') {
            steps {
                // Si ton projet n'est PAS sur Git, on saute cette étape
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
