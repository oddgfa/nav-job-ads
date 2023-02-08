# Job Ads

## Build and run tests

`./mvnw clean install`

## Run the server

`./mvnw spring-boot:run`

## Usage

Start the application with `./mvnw spring-boot:run`.

To get the count of Kotlin vs Java ads, visit `http://localhost:8080/kotlinVsJava`.

## How it works

The application will fetch the job listings from NAV, group them by week and count if the ad contains either Kotlin or Java.

## Limitations

The endpoint to fetch the list of job ads does not paginate the whole query, so to get the job listing for half a year, some changes to the query has to be made.

Error handling is also lacking at this point. Currently, we ignore any errors we get when fetching the job ads and silently continue.
