node('windows_agent'){
    stage("step1. git checkout branch"){
        bat """
            cd c:\\Github\\zhangzu
            dir
            git fetch
            git checkout feature/#60_csvtogcs
            git pull
        """
    }
    stage("step2. make csv from mysql"){
        bat """
            cd c:\\Github\\zhangzu\\tools
            zhangzu_toCSV.bat
        """
    }
    stage("step3. upload csv to gcs"){
        bat """
            cd c:\\Github\\zhangzu\\tools
            zhangzu_CSVtoGCS.bat
        """
    }
}