# RandomAlbumGenerator
## Summary
Using Spotify SDK and Spotify Web API this Android app allows a logged in Spotify user to select a genre of music and will be given a
random album of the same genre as a selection to listen to. In addition a user can use their personal spotify playlists and have a random
album chosen based on the artists within the selected playlist. The user is also greeted with a curated album of the day that has been 
selected by the developer. All random albums currently have a redirect URL to spotify in order to be played and cannot be played within 
the app.


## What Was Used to Make This App
Android Studio- All app development was done in Android Studio\
Spotify Android SDK- Used to login user to their spotify account and get an authentication token to access the web api\
Spotify Web API- Used to generate the random album\
PhpMyAdmin- Used to make calls to the database to retrieve the curated album of the day\
MySql- Database that stores all the curated albums\
Wampserver- Local server used to test features
