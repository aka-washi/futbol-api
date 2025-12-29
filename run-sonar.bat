@echo off
REM Run SonarQube analysis with Java 21 while keeping the rest of the build on Java 11

echo Running tests and generating coverage report...
call gradlew.bat clean test jacocoTestReport
if %ERRORLEVEL% NEQ 0 (
    echo Tests failed!
    exit /b %ERRORLEVEL%
)

echo.
echo Running SonarQube analysis...
set SAVED_JAVA_HOME=%JAVA_HOME%
set JAVA_HOME=C:\Program Files\Amazon Corretto\jdk21.0.9_10
set PATH=%JAVA_HOME%\bin;%PATH%

call gradlew.bat sonar --no-daemon

set RESULT=%ERRORLEVEL%
set JAVA_HOME=%SAVED_JAVA_HOME%

if %RESULT% EQU 0 (
    echo.
    echo SonarQube analysis completed successfully!
    echo View results at: http://localhost:9010
) else (
    echo.
    echo SonarQube analysis failed!
)

exit /b %RESULT%
