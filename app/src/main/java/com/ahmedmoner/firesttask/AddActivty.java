package com.ahmedmoner.firesttask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddActivty extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    private StorageReference ProductImagesRef;
    private Uri ImageUri;
    private String downloadImageUrl;

    Button button;
    private ImageView InputProductImage;
    private ProgressDialog loadingBar;
    private static final int GalleryPick = 1;

    EditText et_name,   et_discribtion, et_pricr, et_oldPrice, et_rating, et_percentDiscount;
    String name, describtion,   percentDiscount;
    int price, oldPrice , rating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activty);

        button = findViewById(R.id.btn);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        et_name = findViewById(R.id.name_product);
        et_discribtion = findViewById(R.id.describtion_product);
         et_pricr = findViewById(R.id.price_product);
        et_oldPrice = findViewById(R.id.priceOld_product);
        et_rating = findViewById(R.id.rating_product);
        et_percentDiscount = findViewById(R.id.percentDiscount_product);

        InputProductImage = findViewById(R.id.iv_add_product2);
        loadingBar = new ProgressDialog(this);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images test");

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddActivty.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GalleryPick);

                } else {
                    OpenGallery();
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new user with a first and last name
                Toast.makeText(AddActivty.this, "جاري رفع المنتج", Toast.LENGTH_SHORT).show();
                StoreProductInformation();



            }
        });

    }
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    private void StoreProductInformation() {

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + "name" + ".jpg");


        Bitmap bitmap = ((BitmapDrawable) InputProductImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //todo مصفوفه بها بعض المتغيرات لقياس جوده الصوره
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = filePath.putBytes(data);
        // final UploadTask uploadTask = filePath.putFile(data);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddActivty.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddActivty.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddActivty.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }
    private void saveProductInfoToDatabase() {
        name = et_name.getText().toString();
        describtion = et_discribtion.getText().toString();

        price = Integer.parseInt(et_pricr.getText().toString());
        oldPrice = Integer.parseInt(et_oldPrice.getText().toString());
        rating = Integer.parseInt(et_rating.getText().toString());


        percentDiscount = et_percentDiscount.getText().toString();

        Map<String, Object> ViewAllProduct = new HashMap<>();


        ViewAllProduct.put("name", name);
        ViewAllProduct.put("description", describtion);
        ViewAllProduct.put("image", downloadImageUrl);

        ViewAllProduct.put("rating", rating);//int

        ViewAllProduct.put("price", price);//int
        ViewAllProduct.put("oldPrice", oldPrice);//int

        ViewAllProduct.put("percentDiscount", "23 % OFF");


// Add a new document with a generated ID
        db.collection("Products")
                .add(ViewAllProduct)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(AddActivty.this, "DocumentSnapshot", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        Toast.makeText(AddActivty.this, "e" + e, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == GalleryPick && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                OpenGallery();

            } else {
                Toast.makeText(this, "permission denied ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {


            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);

        }
    }

}