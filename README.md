App to fetch NycSchools
=======================

The project fetches a limited (upto 50) list of NYC Schools and displays them in a feed. The users
can drill down to view scores of a particular schools by clicking on any row.

Feed view aka list view displays just the `dbn` and `school name` but detail view displays reading,
writing and arithmetic scores for a given school.

### Dependencies

The implementation has been written in Kotlin, and is implemented using MVVM model. It uses the following libraries

1. Jetpack Compose toolkit to render the UI, and handle the navigation from one screen to another
2. ViewModel and StateFlow to manage and persist the state across the activity lifecycle
3. Coroutines for structured concurrency
4. Retrofit to handle networking operations
5. MockK to enable mocks in tests
6. Robolectric library to support Android libraries in unit tests
7. Hilt to manage dependency injection

### Set up

The implementation uses Socrata data to power the screen, which requires an app token to be passed
as a header parameter value. The users can register for an app token using the [instructions on this site](https://dev.socrata.com/foundry/data.cityofnewyork.us/s3k6-pzi2).

Since the app token is a sensitive information is a sensitive information, it must be secured. The implementation 
uses system environment variable to provide the app with the SOCRATA app token. The implementation uses `SOCRATA_API_KEY` as the variable with which the app token is associated.

This should be set up before the app is launched or the system will fail

### Potential improvements
* Support offline first paradigm. This can be set up using local sqlite DB powered by Room as the source of truth. The network calls will simply augment the initial calls.
* Handle loss of network connectivity so that we can adjust accordingly
* Show list-detail flow for large screen form factors, and expanded foldables
* Support pagination to show more content as the user scrolls down the feed