```
  ____       _ _   _     _       _______   _                                                _           _   _                 
 |  _ \     (_| | (_)   | |     |__   __| | |                                              (_)         | | (_)                
 | |_) |_ __ _| |_ _ ___| |__      | | ___| | ___  ___ ___  _ __ ___  _ __ ___  _   _ _ __  _  ___ __ _| |_ _  ___  _ __  ___ 
 |  _ <| '__| | __| / __| '_ \     | |/ _ | |/ _ \/ __/ _ \| '_ ` _ \| '_ ` _ \| | | | '_ \| |/ __/ _` | __| |/ _ \| '_ \/ __|
 | |_) | |  | | |_| \__ | | | |    | |  __| |  __| (_| (_) | | | | | | | | | | | |_| | | | | | (_| (_| | |_| | (_) | | | \__ \
 |____/|_|  |_|\__|_|___|_| |_|    |_|\___|_|\___|\___\____|_______|_|_| |_| |_|\__,_|_| |_|_|\___\__,_|\__|_|\___/|_| |_|___/ 



                                                         ____ _______ 
                                                        |  _ |__   __|
                                                        | |_) | | |   
                                                        |  _ <  | |   
                                                        | |_) | | |   
                                                        |____/  |_| 


```

# Fair Billing Application

## Overview
This Java application provides a summary of each user's total session time to have fair billing.

## Features
- Parses log file with user session start and end times
- Handles incomplete sessions (missing start or end times)
- Calculates and outputs session count and total session duration for each user

## Requirements
- Java 8 or higher
- Maven (for building and managing dependencies)

## Building the Project
To build the project, run:
mvn clean install

## Running the Application
After building, run the application with:
java -jar target/fair-billing-1.0-SNAPSHOT.jar path/to/logfile.txt

## Input Format
The log file should contain lines in the following format:
`
HH:MM:SS USERNAME Start|End
`

Example:
```
14:02:03 ALICE99 Start
14:02:34 ALICE99 End
14:02:58 ALICE99 Start
14:03:33 ALICE99 Start
14:03:35 ALICE99 End
14:04:05 ALICE99 End
```

## Output Format
The application prints session details for each user:
USERNAME SESSION_COUNT TOTAL_DURATION_IN_SECONDS

Example:
`
ALICE99 4 240
`

## Testing
Run unit tests with:
mvn test
