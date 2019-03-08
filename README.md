# Instrucciones para conectar proyecto Android Studio al respositorio en Github

1. Sincroniza tu proyecto de Android Studio.
Desde GitBash situarse en la carpeta del proyecto y lanzar los siguientes comandos:
$ rm -Rf .git/
(este primer comando es esencial que estéis dentro de la carpeta del repositorio lo podéis comprobar con el comando "pwd")
$ git init
$ git add .
$ git commit -am "initial commit"
$ git remote add origin https://github.com/2DAMUE/pfcjun19-tuequipo.git
$ git pull origin master --allow-unrelated-histories
(aquí se abre el editor vi y tenéis que teclear tecla ESC y luego :wq con tecla Enter al final
$ git push origin master (editado) 

2. La coordinación del proyecto se realiza en el siguiente grupo de slack, únete ahora:
https://join.slack.com/t/pfcdic18/shared_invite/enQtNDM5MzM1MTUxNTI2LWE3YjBiYTkzODI5OTQxNTZjZjQzNDAwZjc1MjYyODBlZmMzZmVjZDBkODQ0MDdjZWEyYTdmNWUzNTc2ZTRmYjI
3. Dentro de la carpeta "docs" sustituirás la versión actual por tu versión de las plantillas de Anteproyecto y Memoria


