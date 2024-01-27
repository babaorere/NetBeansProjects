
# Conocer la version de git
git version

# Configurar git
git config --global user.name "nombre"
git config --global user.email "email"

# Mostrar la configuracion
git config --list

# Iniciar git en el directorio de trabajo
git init

# Crear archivo de exclusiones para git
.gitignore

# Ver el estado de los archivos
git status

# Agregar un archivo al eguimiento de git, staging area
git add nombre.archivo

# Agregar archivos al eguimiento de git, staging area
git add -A

# dejar de rastrear un archivo
git rm --cached nombre-archivo

# historial de commits
git log

# traer un repositorio externo, para poder trabajarlo
git clone  url.del.repositorio

# Conocer los cambios de local vs repositorio
git diff

# subir el codigo al repositorio remoto
git push origin master

# traer la ultima actualizacion del repositorio remoto
git pull origin master

# Crear una nueva rama
git branch nombre.rama

# Cambiar a una rama
git checkout nombre.rama

# Crea y cambiar a una rama
git checkout -b nombre.rama

# Ver todas las ramas locales
git branch

# Ver todas las ramas locales y remotas
git branch -a

# actualizar la rama en el repositorio remoto
git push -u origin nombre.rama

# Unir el codigo de la rama a master
git checkout master
git pull origin master
git branch --merged
git merge nombre.ramasgit push origin master
git pull origin master

# Eliminar la rama, en el repositorio remoto
git push origin --delete nombre.ramas

# Eliminar la rama del repositorio locales
git brach -d nombre.rama

# regresar el estado de un archivo, al que esta en el repositorio
git checkout nombre.archivo

# Modificar el ultimo commit
git commit --amend

# Ver los archivos modificados en los commit 
git log --stat

# Copiar el commit de una rama a otra
git log 
git checkout nombre.rama 
git cherry-pick hash.commit 
git log
git checkout master
[git reset hash.commit] [git reset --soft hash.commit ] [git reset --hard hash.commit ]

# Actualizar la info de los tracking local, con lo remotos
[git fetch origin] [git fetch nombre.repositotio.remoto] 

# Actualizar los cambios desde el repositorio
git merge origin/master

# git pull hace un git fetch y un git merge




















