blacklist-jee
=============

You will need Java8 to compile and run.

Check if your application is running with

http://localhost:8080/blacklist-jee7/resources/health

## Developing in local Ubuntu VM with git, java8, docker and maven installed

Prerequisite: VirtualBox and Vagrant installed.

Initialize and connect to the VM from this project's root directory:

    $ vagrant up
    # grab yourself a coffee
    $ vagrant ssh

If you need to install more tools in the VM please add them to ```vm-bootstrap.sh```.