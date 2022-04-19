# Requirements

- Docker and Docker Compose
- Java (11)

# Build

To build the project you can use the `gradlew` command line (or an IDE with `gradle` support) to run the `build` task.

Example for command line:
```shell
./gradlew clean build
```
<small>Use `gradlew.bat` if you are in windows.</small>

Upon completion, a file named `ds-jgroups-demo-1.0-SNAPSHOT.jar` should be created inside the `{project}/build/libs` directory.

# Start the Example

In the `docker` directory, you wil find a `docker-compose.yml` file which declares two services:

- The **Gossip Router** - needed for the members to connect to the cluster when it traverses multiple networks _(similar to a gateway)_ 
- The **Peer Node** - uses the above generated `.jar` file to spawn multiple copies of each member _(see `deploy.replicas` parameter)_

To run the example, simply issue:
```shell
cd docker
docker-compose up
```