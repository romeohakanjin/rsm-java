1/Rajouter la datasource au fichier standalone-full, en modifiant l'ip pour qu'elle corresponde à celle de votre pc

<datasource jndi-name="java:jboss/datasources/rsmProject" pool-name="rsmProject" enabled="true" use-java-context="true">
  <connection-url>jdbc:mysql://192.168.150.1:3306/apocalypse</connection-url>
  <driver>mysql</driver>
    <security>
      <user-name>interro</user-name>
      <password>interro</password>
    </security>
</datasource>

2/Développement

Chaque personne travaille uniquement sur sa branche pour ne pas créer de conflit entre les fichiers
La branche master doit uniquement contenir les fonctionnalités finalisés (approuvé par tous, et tester)
Aucun fichier d'environnement, "personnel" (généré par eclipse) ne doit être commit.
Ne pas travailler en même temps sur un même fichier pour faciliter le merge de la branche dev à la branche master.

3/Convention
Les noms de fichiers, variables et méthodes doivent être :
- explicite
- en anglais
- en camel : premier mot de la variable en miniscule, chaque première lettre des autres mots en majuscule
	EX: nomVariableCamel

4/Guide de récupèration du projet

Pré-requis : Avoir installé git
A- Créez un répertoire sur votre pc
B- Clique-droit dans votre nouveau répertoire, Cliquez sur "Git Bash Here"
C- Saisissez les commandes suivants :
	"git init"
	"git remote add origin https://github.com/romeohakanjin/m1-java.git"
	"git pull origin master"
	"git fetch"
D- Pour commencer à travailler sur votre branche : "git checkout [Nom de la branche]"
