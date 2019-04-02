package edu.miracostacollege.cs134.petprotector;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView petImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect petImage view to the layout
        petImageView = findViewById(R.id.petImageView);

        // setImageUri on the petImageView
        petImageView.setImageURI(getUriResource(this, R.drawable.none));
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
