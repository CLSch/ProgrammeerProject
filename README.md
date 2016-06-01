# Programmeer Project Proposal

This project will be a feedback app on which users can save the feedback they get on subjects throughout the years. 

##### Problem and solution:

Lots of feedback is still given on paper or saved on a computer somewhere to be never found again. Valuable feedback gets lost
by this and students will make the same mistakes again. It's hard to save all the feedback on a certain subject in the same place
and have a clear view on your progress because of this. This app will solve this problem by saving feedback in a subject map which is saved in 
a 'year map' as to make the feedback easy to find. The color of the feedback names in the app will indicate if the feedback was constructive (red) or
if it's a compliment (green) on what the student's done right. 

##### Datatypes and API:

For this app I will use the Dropbox API. With this API I will have users log in to the app so their feedback can be stored even if the user changes
from device. Dropbox can store photos (JPEG and PNG), Word doc files and PDF files which I want to use to store the feedback with on the app. Most electronic feedback will be stored in PDF and Word files. For all other files a screenshot can be taken. For paper feedback a photo can be made and saved. The user can also add his own 'memos' by text. The files all have an icon in the listview to quickly make clear what datatype the feedback is.

##### Sketches and seperate parts of app:

The app will have a log in screen that is connected to the dropbox API. The user will be directed to the dropbox subscribe page in their browser if they don't have an account. The account part is connected to the database. The first activity after the log in screen is a screen with the current schoolyear and finished schoolyears.

![Log in screen](doc/log_in_first_screen.jpg)

When clicked on a year the user is directed to the subjects the user has added. 

![User Interface](doc/user_interface.jpg)

Subjects and feedback can be deleted or have their names changed by doing a long click on the name. By doing this an alert dialog will pop up that has the option of deleting the files or changing the names.

The subject part of the database can be divided between a files upload part and a view files in database part. The files that can be uploaded are either a PDF, Doc, JPEG or PNG file or the user can make a picture and save a file directly that way. 

Saved feedbacks are connected to the part that makes viewing files possible because this will make sure the saved files can be accessed and viewed later on. The log in, file adding and file accessing part are the main components of the app (with the photo-making option being a sub category of the upload part).

##### Technical problems or limitations:

One problem that could arise is the fact that I had trouble with updating listviews after making changes in an other app before. This problem can be overcome by digging deeper in the source of the problem. Another problem could be the fact that I need either multiple databases or multiple tables. In an earlier app assignment I had some trouble with saving these tables/databases after the app closed. This problem can probably be overcome by looking into shared preferences. Other problems that may arise could be problems with the API. If the API is not able to do what I want I will try to see if there is a workaround.

##### Similar apps:

Most Feedback apps in the Google Play Store are about giving feedback on certain subjects. Some work with a like/dislike option, others with faces that indicate if you are happy or unhappy with the product. A feedback app (called Feedback) that comes a little closer to my idea is an app that saves all feedback from clients on their products through a form. This gets saved and is accessable again for the user. An other app I found is one outside of the Play Store called "KOOTSJ". This app makes it possible for employees and employers to give feedback to colleagues (either a compliment or constructive) which gets saved on the app. 

##### Minimum viable product:

The minimum viable product would be an app on which the user can log in through dropbox and on which you can add feedback and add feedback. You can change the name of the directories and/or files or delete them and that will be it. There will not be any icons, colors for if it was constructive feedback or a compliment nor will there be memo's that can be added by the user for feedback.