@echo off
setlocal

set JAR=clinica-citas-0.1.0.jar

if not exist "%JAR%" (
    echo Error: No se encontro el archivo %JAR%
    echo Asegurate que este archivo este en la misma carpeta.
    pause
    exit /b 1
)

echo Iniciando Clinica Citas...
java -Dfile.encoding=UTF-8 -jar "%JAR%"