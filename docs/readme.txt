The tests and documentation is not complete and it needs more time.

1 - Create DOCKER
change to project directory and follow the below instructions respectively.

a. build the project using the following command
    ./mvnw package && java -jar target/arabian-0.0.1.jar
b. build the docker
    sudo docker build -t target/arabian-0.0.1 .
c. save docker image for using in other machines
./mvnw package && java -jar target/arabian-0.0.1.jar