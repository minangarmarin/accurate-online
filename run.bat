@echo off
"cmd" /K "cd C:\Arief\Hijra\Automation\DBO\Freelance\Web\minang\accurate-online && .\gradlew clean test && .\gradlew runAfterTest"
timeout /t 5 > nul