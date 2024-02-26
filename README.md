# COMS_309_IP_102
This is the group project created in the class COMS 309. I worked on the backend with one other member. (F2023)

The idea of the project was to make a workout application for teams. Coaches can make a "team" which athletes could join. Coaches could assign workouts to individual team members or assign to the entire team.
There are also gymrats which track their own workouts.

This was a great project to teach me how to use GIT, understand how front end and backend work together, and how to plan and work with a team.


# IP_102

```
cd existing_repo
git remote add origin https://git.las.iastate.edu/cs309/fall2023/ip_102.git
git branch -M main
git push -uf origin main
```

***

# Backend info
[Spring initializer](https://start.spring.io/) can be used for creating the pom.xml file (dependencies). After the project is created dependencies can be
added later by adding whatever dependecies you need then copying and pasting the dependency in from the explore button.

## Helpful videos
- [Backend Overview](https://youtu.be/XBu54nfzxAQ?si=TtNY30Tg9MwfTH6z)
- [Spring Boot tutorial](https://youtu.be/-mwpoE0x0JQ?si=691FD1NJo9ARYJLE)
- [Spring Data JPA](https://www.youtube.com/watch?v=8SGI_XS5OPw)

## Dependencies already installed
- Spring Web
- H2 Database
- Spring Data JPA
- MySQL Driver

## Connecting and using DB
### SSH to VM
open command line

ssh your-netid@coms-309-008.class.las.iastate.edu

### Running jar
make sure you are SSH into the VM

cd /tmp

java -jar backendApplication-1.0.0.jar

## [Adding jar to VM](https://docs.google.com/presentation/d/1AWb6BqmjAQheL6HF1gH1GXu8qopMDntk/edit#slide=id.p7)

### Access MySQL on VM
mysql -u <user> -p

#### Root Login
user: root

password: 966263656e7aec8b


## Authors and acknowledgment
Frontend:

Eric Hedgren
Gavin Fisher

Backend:

Franck Biyoghe Bi Ndoutoume
Max Roher
