node('windows_agent'){
    stage("step1. git checkout branch"){
        bat """
            cd c:\\Github\\zhangzu
            dir
            git fetch
            git checkout feature/#58_add_backup.bat
            git pull
        """
    }
    stage("step2. zhangzu_backup"){
        bat """
            cd c:\\Github\\zhangzu\\tools
            zhangzu_backup.bat
        """
    }
}