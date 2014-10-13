#!/bin/bash

# install java 8 (auto accepting oracle java 8 license)
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo add-apt-repository ppa:webupd8team/java &&
sudo apt-get update &&
sudo apt-get install -y docker.io &&
sudo apt-get install -y curl &&
sudo apt-get install -y git &&
sudo apt-get install -y oracle-java8-installer &&
sudo apt-get install -y maven &&

# link docker's binary to docker
sudo ln -sf /usr/bin/docker.io /usr/local/bin/docker &&
sudo sed -i '$acomplete -F _docker docker' /etc/bash_completion.d/docker.io