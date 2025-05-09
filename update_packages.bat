@echo off
setlocal enabledelayedexpansion

set "OLD_PACKAGE=com.onework.boot.task.collection"
set "NEW_PACKAGE=com.onework.boot.module.scraper"

for /r "onework-module-scraper" %%f in (*.java) do (
    powershell -Command "(Get-Content '%%f') -replace '%OLD_PACKAGE%', '%NEW_PACKAGE%' | Set-Content '%%f'"
)

for /r "onework-module-scraper" %%f in (*.xml) do (
    powershell -Command "(Get-Content '%%f') -replace '%OLD_PACKAGE%', '%NEW_PACKAGE%' | Set-Content '%%f'"
)

echo Package names updated successfully! 