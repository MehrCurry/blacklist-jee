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

The service is running at:
`http://localhost:8080/blacklist-jee7/resources`

! BUG: All requests without accept header return 500.

* Resources
 * /blacklist [GET,PUT]
 * /blacklist/{blacklistName} [GET,POST,DELETE]
 * /blacklist/{blacklistName}/{blacklistEntry} [GET,DELETE]

### /blacklist
REST wrapper for the BlacklistService. Provides methods for finding, creating and modifying blacklists.
#### Methods
##### GET
Returns a list of URIs for given blacklists.
###### Example Request
```
curl --header 'Accept: application/json' "http://localhost:8080/blacklist-jee7/resources/blacklist"
```
###### available response representations:
  * 200 OK - application/json (depends on Accept header)
   * `[]` (no blacklists)
   * `["http://localhost:8080/blacklist-jee7/resources/blacklist/Gamma","http://localhost:8080/blacklist-jee7/resources/blacklist/Delta"]`
   
##### PUT
Adds a new blacklist to the service.
###### Example Requests
* Blacklist with entries
```
curl -X PUT -d '{"name":"ARGH","listedElements":["ARG","nooo","345"]}' --header 'Content-Type: application/json' --header 'Accept: application/json' "http://localhost:8080/blacklist-jee7/resources/blacklist"
```
* Blacklist without entries
```
curl -X PUT -d '{"name":"ARGH2"}' --header 'Content-Type: application/json' --header 'Accept: application/json' "http://localhost:8080/blacklist-jee7/resources/blacklist"
```
* Blacklist with malformed entries
```
curl -X PUT -d '{"name":"ARGH","listedElements":["ARG$%&","no_..,#oo","3&nbsp;45"]}' --header 'Content-Type: application/json' --header 'Accept: application/json' "http://localhost:8080/blacklist-jee7/resources/blacklist"
```
###### available response representations:
* 201 (Created)
```
Status Code: 201 Created
Content-Length: 0
Date: Wed, 15 Oct 2014 22:10:19 GMT
Location: http://localhost:8080/blacklist-jee7/resources/blacklist/TEST
Server: Apache-Coyote/1.1
```
* 400 (Bad Request) on malformed JSON
```
Status Code: 400 Bad Request
Connection: close
Content-Length: 268
Content-Type: text/plain
Date: Wed, 15 Oct 2014 22:12:44 GMT
Server: Apache-Coyote/1.1
```
* 400 (Bad Request) on malformed blacklist entities
```
Status Code: 400 Bad Request
Connection: close
Content-Length: 0
Date: Wed, 15 Oct 2014 22:13:54 GMT
Server: Apache-Coyote/1.1
validation-problem: The given blacklist entry/entries [ARG$%&, ##&##, 3&nbsp;45] is/are not alphanumeric
```

### /blacklist/{blacklistName}
Accessing a blacklist directly. Provides methods for browsing entries, deleting blacklists and adding entries.
#### Methods
##### GET, POST, DELETE
TODO ...

### /blacklist/{blacklistName}/{blacklistEntry}
Accessing a blacklist entry. Provides methods for deleting blacklist entries or check if they exist.
#### Methods
##### GET, DELETE
TODO ...
