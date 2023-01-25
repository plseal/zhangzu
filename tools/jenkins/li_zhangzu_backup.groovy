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
    stage("step2. zhangzu_backup"){
        bat """
            cd c:\\Github\\zhangzu\\tools
            li_zhangzu_backup.bat
        """
    }
}