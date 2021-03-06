This application saves feedback that users got on schoolwork over the years. For this app a dropbox account is necessary. The user can add schoolyears in which they can save subjects. In the subjects they can add feedback. The feedback will be saved in the form of an image. The user can save it by uploading an image from their gallery or by making a picture on the spot. Whenever an image gets saved, the image is uploaded to dropbox. When the user clicks on the feedback the image gets downloaded from dropbox.

##Technical Design

####User Interfact  
The user interface is as follows. Upon opening the app a login year screen for dropbox will pop-up or - when the user was previously logged in and din't log off - the user will go straight to the first activity. After authetication the first activity is a screen in which users see all their schoolyears. They can add schoolyears whenever they want. Upon clicking on a year the user gets directed to the second activity in which all subjects they've added for that year are saved. Users can add and delete subjects and also change the name of subjects. This all works through alert dialogs. When a user clicks on a subject they go to an actiivty in which all feedback for that subject is saved. They can add feedback by either uploading a picture from the gallery or making a picture. The name of this feedback gets shown in the activity and when clicked, the images get downloaded and shown in a different activity. At the upper left of the screen is a log out button which is accessable through every activity. When logged out, the user needs to log in to enter the app again. 

####UML  

######YearsActivity  
The UML is structured the following way. Upon launching the app a dropbox session gets initialized. If there's no token saved in the shared preferences the user needs to log in and a new session gets created with a new token. If there's already a token the user is still logged in and the log in screen doesn't get prompted. After a session is initialized the user-id of the logged in dropbox account is retrieved through the AccountAsyncTask. This user-id is linked to the database. After getting the user-id the appropiate fields are retrieved from the database. When adding a year a new item is made in the database, all yearfields linked to the user-id get saved in a list and the adapter gets updated with the new list. Upon clicking on a list item a new activity gets called and the year that is clicked gets passed to the new activity. 

######AllSubjectsActivity  
A list gets filled with all subjects belonging to the passed year and user-id if the database isn't empty and the listview gets updated. The user can add a subject by clicking on the add button which will prompt an alert dialog. If the user long clicks on a subject, a different alert dialog is shown with which they can delete subjects or change the name of subjects. When the user clicks on a subject the third activity opens and the clicked subject gets passed to it.

######CurrentSubjectActivity  
Like the previous activities the listview gets updated with items - in this case feedbackitems - from the database if it isn't empty. In this activity the user has two options of adding items. If they click on the gallery button a new activity gets opened where they can enter a name. After getting the name the gallery app gets opened. This is a different activity because originally the user would be able to add tags and type of feedback to the photo. A onactivityresult function listens for a result from the gallery and makes a file of the photo with the path that gets passed to this function. After making the file, the file gets passed to the UploadPhotoAsyncTask The UploadPhotoAsyncTask upload the photo to dropbox and return the path at dropbox to a function in the activity. In this function the path gets saved in the database for the right photoitem. After this the currentsubject activity gets called again to show the added feedback.
The user can also make a picture by clicking on the camera button. This will first prompt an alert dialog in which the user needs to pass a name for the feedback. After adding the name the camera intent gets called and opens the camera app on the phone. After the picture is taken the picture gets once again uploaded to dropbox and added to the database. Upon clicking on a feedback item the PhotoFeedbackActivity gets called and the drobox path of the clicked feedback item gets passed.

######PhotoFeedbackActivity  
Upon opening this activity the DownloadFileAsyncTask gets called and the photo of the given path gets downloaded. After downloading a bitmap gets created with the downloaded photo and passed back to the PhotoFeedbackActivity. In the PhotoFeedbackActivity the bitmap is saved to a variable and a function gets called to set the bitmap to the imageview. 

######SuperActivity  
All of the activities extend the SuperActivity in which a menuitem is created for logging out. Because every activity extends this activity the log out button is shown and clickable in every activity.

######DropBoxApiManager
This singleton contains an instance of the DropBoxAPI with which function calls can be made to dropbox. Most of the authentication gets handled here and the token gets saved through shared preferences, so a user doesn't have to log in each and every time they open the app.

######UserIdSingleton
The UserIdSingleton contains the user-id linked to the signed in dropbox account which is linked to the database. With the user-id the right fields can be retrieved from the database for the current user.

##Challenges

I had a lot of challenges while making this app. 

#####Firebase
In the second week I started working with the Firebase API but I coulnd't get it to work. At first I read up on the database. Google's documentation is quite extensive so it took me a lot of time to read through it all. I was however confident it would all work out especially because there was so much documentation provided by google. On wednesday I realized that I needed to work with the Firebase storage and not the database. On thursday the authentication via Firebase worked (all be it buggy) and I also realized that the storage function from Firebase had only been available for a few weeks. On friday I was able to upload pictures to the firebase storage. After trying to make downloading possible the entire weekend I set a ultimatum to have downloading working on monday or I would switch to dropbox. Due to the lack of documentation I still had no clue how to download my images from the firebase storage so I decided to switch.

#####Dropbox
On Tuesday I was able to log in via dropbox and create a session. On wednesday I was able to upload pictures to dropbox after finding a very useful tutorial. On thursday however I spend the entire day trying to find a way to download the pictures from dropbox. None of the TA's were able to help me either. On friday I decided to dicuss my current state of the app with the coordinator as I was nowhere near of having a beta version. Together we were able to make the downloading of images possible. During the weekend I realized I had an other problem. I stored the years - subjects and feedback items from the user - in a SQLite database. However the minute a different user logged into my app, the old database would still be there. Upon clicking on the feedbackitems that were stored in the database, my app would try to download pictures from the current users's dropbox and crash because the files weren't there. 

The last week I spend building my database and creating my apps functionality. On wednesday I was able to pair my database with a unique user-id that I could retrieve from the logged in dropbox accounts. This way I have a personal SQLite database per user. In the evening I however found that my app still crashed on a few points. My app was prone to crashing while downloading the image because my app would be out of memory and upon changing the orientation repeatedly the window from my alert dialog would lose it's link and crash. I was able to repair those bugs. I also had trouble with my own mobile phone. When signing up with a different account the user gets send to a login page in a browser. My phone crashed about half of those times, because it's old and it is on the brink of death. My phone is prone to crashing with different apps too as apps are getting bigger and bigger and my phone is three years old. If the internet app wasn't crashing my phone also didn't redirect the user back after logging in via the browser. 

On Thursday I decided to test my app on a phone from the minor and the redirecting to the internet browser and back to the app seemed to work fine. However after testing my app on that phone I found out there was a bug for opening the camera app on newer devices than my own phone. After taking a picture the camera intent is supposed to send back 'data' to the onactivityresult function. In the data should be the URI of the path of the picture saved but my app could not find this URI and crashed. On the internet I read that this is a common bug in android phones. However none of the solutions I found seemed to work on the new device so the bug is still in there. 

One other crash that sometimes happened was that the token would suddenly be null upon retrieving. However when checking what my DropBoxAPIManager returned, the token was not null and the error seemed out of place. It seems to be fixed by not adding extras to the take picture intent and the open gallery intent but these two things seem unrelated. Id the error would show up again I would probably catch it with an exception but since it seems to be fixed now I didn't do that.

##Decisions

The decision to change from API was a good one and should have been done sooner. I changed my plan from using only dropbox as database to using an SQLite too. I did this because working with the Dropbox API was not easy and would probably take up to much time. I would however have to save a lot of information in shared preferences like the image paths if I don't use a database on the side. Saving a database to dropbox and loading it when the app gets started and uploading when you close the app would be a nice solution. This way the user has no problem using the app on a different device. I tried this but I had not enough time to implement it. I would also add a lot more ways to upload feedback like adding feedback by hand and uploading word/pdf files. One other function I would add would be searching and creating tags for feedback files. This way you can sort feedback in multiple ways. Right now feedback is only sorted by subject. 





