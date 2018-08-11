# FollowerFinder

A github follower finding sharing service created with [Spring Boot](https://spring.io/projects/spring-boot)
The main logic can be found in the file git-follow-tree/src/main/java/com/follower/application/controller/UserController.java

## Setup/Execution
Make sure you have java jdk installed.
This project uses Maven to handle dependencies. 
https://maven.apache.org/download.cgi
(installation instructions https://maven.apache.org/install.html )
(On windows, this may also involve adding a new environment variable on your system with the name JAVA_HOME, and a value that points to the installation of the java jdk. This will generally be similar to the following: C:\Program Files\Java\jdk1.8.0_171\ 
Per the provided instructions from apache, you will also have to add the [maven installation directory]\bin\ directory to your Path environment variable.
)

Navigate to the base directory of the project and open a command prompt there. Be sure you open this prompt after your modifications to the environment variables. 
Now simply enter "mvn clean install"
and then "mvn package"

Note that to use this service you require the ID of a user. The user ID is distinct from the username. To obtain the ID of a given username, you can navigate to

https://api.github.com/users/your_github_user_name
The second entry should list the ID of a given username. For example
https://api.github.com/users/emscook
gives 
login	"emscook"
id	39387338
...

Now, you can navigate to the newly created "target" directory within the project folder, and run the mydrive-0.0.1-SNAPSHOT.jar .
Allow the application internet access, and now the endpoint is accessible. It is running on port 8080.
The endpoint can be queried through curl with the following command, simply replace the userid with the userid you'd like to query. 

curl -X GET "http://localhost:8080/users/39387338" -H  "accept: */*"

Alternatively, you can navigate to http://localhost:8080/swagger-ui.html to use the swagger ui to send the queries through a gui. Click on the user-controller entry, go into the only git endpoint, and hit "try it out" to open the options to enter the userid and send the request.

Give the command around ten seconds to complete. You will receive a JSON Array of the desired userids encountered in traversing the followers to a depth of five.

## Testing
This can be tested by running the query on users where the followers are small in number or known ahead of time, and comparing the ids against expected ids to be encountered.

## Issues
If you run into any problems running the application or setting it up, you can email me at emseduau@gmail.com
