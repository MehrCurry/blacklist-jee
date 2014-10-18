blacklist-jee
=============

[![Build Status](https://travis-ci.org/MehrCurry/blacklist-jee.svg)](https://travis-ci.org/MehrCurry/blacklist-jee)

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

## Building and running Docker container

``` bash
cd Docker
docker build --rm -t blacklist .
# grab yourself another coffee (only for the first run)
docker run -d -p 8080:8080 -p 4848:4848 blacklist
```

## Using the service

ITs all (... well most of it)  in the [Wiki] (https://github.com/MehrCurry/blacklist-jee/wiki/Using-the-service)

## Using glassfish with DB
Start the database before starting the ApplicationServer:

	<somedirectory>\glassfish4\bin>asadmin.bat start-database