@echo off
echo ===========================================
echo   Employee Information Management System   
echo ===========================================

echo.
echo [1/3] Creating bin and lib directories if they don't exist...
if not exist "bin" mkdir bin
if not exist "lib" mkdir lib

echo.
echo [2/3] Compiling Java files...
javac -d bin src/com/eims/*.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo Compilation Failed! Please check for syntax errors.
    pause
    exit /b
)

echo.
echo [3/3] Starting Application...
echo NOTE: Ensure you have placed your mysql-connector-j-*.jar inside the 'lib' folder!
echo If you get a ClassNotFoundException, update the jar name in this script.
echo.

:: You may need to change the jar file name below if you downloaded a different version
java -cp "bin;lib/*" com.eims.EmployeeManagementGUI

pause
