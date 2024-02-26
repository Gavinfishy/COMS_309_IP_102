# COMS_309_IP_102
This is the group project created in the class COMS 309. I worked on the backend with one other member.

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

## Integrate with your tools

- [ ] [Set up project integrations](https://git.las.iastate.edu/cs309/fall2023/ip_102/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing(SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

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
