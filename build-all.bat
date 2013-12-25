@echo off

echo Build All...
echo:
echo -------------------------------------------------
echo -------------------------------------------------
echo -- Building Project
echo -------------------------------------------------
echo -------------------------------------------------
echo:
call mvn clean source:jar install
if not "%ERRORLEVEL%" == "0" exit /b

echo:
echo All Ok!
pause
