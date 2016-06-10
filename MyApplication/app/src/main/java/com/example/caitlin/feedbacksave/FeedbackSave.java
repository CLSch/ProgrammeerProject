package com.example.caitlin.feedbacksave;

import android.app.ActionBar;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.authentication.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.concurrent.Executor;

/**
 * Created by Caitlin on 06-06-16.
 */
public class FeedbackSave extends android.app.Application {
    StorageReference storageRef;
    StorageReference imagesRef;
    StorageReference photoRef;
    private Uri mDownloadUrl = null;
    //DatabaseReference rootRef;
    //StorageReference rootRef;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //rootRef = FirebaseStorage.getInstance().getReference();
        storageRef = storage.getReferenceFromUrl("gs://project-1258991994024708208.appspot.com");

        //rootRef = FirebaseDatabase.getInstance().getReference();
    }

    public void createRef() {
// Create a reference to "mountains.jpg"
        String fileName = ""; //= get filename
        // filename als het jpg is?? hoe kijken voor png/jpg? wat als een naam al een extentie heeft???
        String childName = fileName + ".jpg";
        photoRef = storageRef.child(childName);

// Create a reference to 'images/mountains.jpg'
        String imagesName = "images/" + childName;
        imagesRef = storageRef.child(imagesName);

    }

    public void uploadPhoto() {
        Uri file = Uri.fromFile(new File(imagesRef.getPath()));
        StorageReference uploadRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = uploadRef.putFile(file);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                 double progress = 100.0 * (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                 System.out.println("Upload is " + progress + "% done");
             }
         });

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    /////////


    

    // [START upload_from_uri]
    private void uploadFromUri(Uri fileUri) {
        Log.d("uploadFromUri:src:", fileUri.toString());

        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = mStorageRef.child("photos")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage

        Log.d("uploadFromUri:dst:", photoRef.getPath());
        photoRef.putFile(fileUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded

                        // Get the public download URL
                        mDownloadUrl = taskSnapshot.getMetadata().getDownloadUrl();]
                    }
                })
                .addOnFailureListener((Executor) this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w("uploadFromUri:onFailure", exception);

                        mDownloadUrl = null;

                        Toast.makeText(FeedbackSave.this, "Error: upload failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }





    //////////

    public void getMetadata() {

// Get reference to the file
        StorageReference metaRef = storageRef.child("images/forest.jpg");

        // alternative way to get data
        //StorageMetadata storageMetadata = forestRef.getMetadata().getResult();

        metaRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                // Metadata now contains the metadata for 'images/forest.jpg'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }

    // DEZE HEB IK MISSCHIEN NIET NODIG????
    public void updateMetadata() {
// Get reference to the file
        StorageReference updateRef = storageRef.child("images/forest.jpg");

// Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .setCustomMetadata("name", "whatever the user inputted!!!!!")
                .build();

// Update metadata properties
        updateRef.updateMetadata(metadata)
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        // Updated metadata is in storageMetadata
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                    }
                });

// Alterntively update the metadata with
        //StorageMetadata updatedMetadata = forestRef.updateMetadata(metadata).getResult();
    }

    public void deletePhoto() {
// Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("images/desert.jpg");

// Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
}
