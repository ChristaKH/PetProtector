package edu.miracostacollege.cs134.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView petImageView;

    public static final int RESULT_LOAD_IMAGE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect petImage view to the layout
        petImageView = findViewById(R.id.petImageView);

        // setImageUri on the petImageView
        petImageView.setImageURI(getUriResource(this, R.drawable.none));
    }


    // When image is clicked
    public void selectPetImage(View v){
        // 1) Make a list (empty) of permissions
        // 2) As user grants them, add each permission to the list
        List<String> permList = new ArrayList<String>();
        int permRequestCode = 100;
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int hasWriteExternalPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadExternalPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        // Toast toast = Toast.makeText(this,"Works", Toast.LENGTH_SHORT);
        //toast.show();
        // Check to see if camera permission is denied
        // If denied, add it to List of permissions requested
        if(hasCameraPerm == PackageManager.PERMISSION_DENIED){
            permList.add(Manifest.permission.CAMERA);
        }

        if(hasWriteExternalPerm == PackageManager.PERMISSION_DENIED){
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(hasReadExternalPerm == PackageManager.PERMISSION_DENIED){
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        // Now that we've built the list, let's ask the user
        if(permList.size() > 0){
            // Convert the List into an array
            String [] perms = new String[permList.size()];
            permList.toArray(perms);

            // Make request to the user
            ActivityCompat.requestPermissions(this, perms, permRequestCode);
        }

        //After requesting permissions, find out which ones the user granted
        // Check to see if ALL permissions were granted
        if(hasCameraPerm == PackageManager.PERMISSION_GRANTED &&
            hasReadExternalPerm == PackageManager.PERMISSION_GRANTED &&
            hasWriteExternalPerm == PackageManager.PERMISSION_GRANTED){

            // Open the Gallery!
            // Use implicit intent
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, permRequestCode);
        } else{
            // Toast informing user that you need permissions
        }

        // Override onActivityResult to find out what user picked
        // Use ctl + o



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_LOAD_IMAGE){
            Uri uri = data.getData();
            petImageView.setImageURI(uri);
        }
    }

    private static Uri getUriResource(Context context, int id){
        Resources res = context.getResources(); // Gives us access to the resource folder
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"   // Grab package name
                 + res.getResourcePackageName(id) +  "/"  // and we want to send in the resource id
                 + res.getResourceTypeName(id) + "/"
                 + res.getResourceEntryName(id);

        // android.resource:// is the scheme
        // android.resource://edu.miracostacollege.cs134.petprotector/drawable/none
        return Uri.parse(uri);
    }
}
