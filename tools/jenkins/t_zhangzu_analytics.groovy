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
        powershell '''
            cd c:\\Github\\zhangzu\\tools
            $OutputEncoding = [System.Console]::OutputEncoding = [System.Console]::InputEncoding = [System.Text.Encoding]::UTF8
            $PSDefaultParameterValues['*:Encoding'] = 'utf8'
            python zhangzu_CSVtoGCS.py
        '''
    }
}