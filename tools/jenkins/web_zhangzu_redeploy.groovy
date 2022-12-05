node{
    stage("step1. git checkout branch"){
        bat """
            cd c:\\Github\\zhangzu
            dir
            git fetch
            git checkout master
            git pull
        """
    }
    stage("step2. stop SpringBoot"){
        bat """
            cd c:\\Github\\zhangzu
            SET /P VALUE_FROM_FILE= < app.pid
            taskkill /pid %VALUE_FROM_FILE% /f
        """
    }
    stage("step3. prepare Prod properties"){
        bat """
            cd C:\\GitHub\\zhangzu\\src\\main\\resources
            del application.properties
            copy application.properties_prod application.properties
        """
    }

    stage("step4. SpringBoot run"){
        bat """
            cd C:\\GitHub\\zhangzu
            mvn spring-boot:run
        """
    }

}
