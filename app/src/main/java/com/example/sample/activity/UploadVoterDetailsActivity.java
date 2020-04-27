package com.example.sample.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sample.BuildConfig;
import com.example.sample.modals.DistrictData;
import com.example.sample.modals.MandalData;
import com.example.sample.modals.StateData;
import com.example.sample.modals.VoterData;
import com.example.sample.utilities.FileCompressor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.example.sample.R;
import com.google.android.material.textfield.TextInputEditText;

public class UploadVoterDetailsActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.etVoterId)
    TextInputEditText etVoterId;
    @BindView(R.id.etVoterName)
    TextInputEditText etVoterName;
    @BindView(R.id.etDob)
    TextInputEditText etDob;
    @BindView(R.id.etDrNo)
    TextInputEditText etDrNo;
    @BindView(R.id.etLane)
    TextInputEditText etLane;
    @BindView(R.id.etStreet)
    TextInputEditText etStreet;
    @BindView(R.id.etPlace)
    TextInputEditText etPlace;
    @BindView(R.id.spinStateName)
    Spinner spinStateName;
    @BindView(R.id.spinDistName)
    Spinner spinDistName;
    @BindView(R.id.spinMandalName)
    Spinner spinMandalName;
    @BindView(R.id.etLandmark)
    TextInputEditText etLandmark;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etMobile)
    TextInputEditText etMobile;
    @BindView(R.id.radioGender)
    RadioGroup radioGender;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    RadioButton radioButton;
    DatabaseReference voterRef,myref,databaseReference,databaseReference1;
    ArrayList<String> stateNameList,districtNameList,mandalNameList;
    ProgressDialog progressDialog,regProgress;
    StorageReference storageReference;
    Uri selectedImage;
    String voterId,voterName,voterGender,voterDoB,voterState,voterDistrict,voterMandal,voterDrno,voterLane,voterStreet,voterPlace,voterLandmark,voterEmail,voterMobile,photoUrl;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_voter_details);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Upload Voter Details");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mCompressor = new FileCompressor(this);

        storageReference = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        regProgress=new ProgressDialog(this);

        voterRef = FirebaseDatabase.getInstance().getReference().child("Voter_Details");


        stateNameList=new ArrayList<String>();
        districtNameList=new ArrayList<String>();
        mandalNameList=new ArrayList<String>();

        myref = FirebaseDatabase.getInstance().getReference("State_Details");
        databaseReference=FirebaseDatabase.getInstance().getReference("District_Details");
        databaseReference1=FirebaseDatabase.getInstance().getReference("Mandal_Details");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String stateName = Objects.requireNonNull(dataSnapshot1.getValue(StateData.class)).getState();
                        stateNameList.add(stateName);
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UploadVoterDetailsActivity.this,R.layout.support_simple_spinner_dropdown_item, stateNameList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinStateName.setAdapter(arrayAdapter);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(UploadVoterDetailsActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinStateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedState = spinStateName.getSelectedItem().toString();

                //Retrieving District Names based on State Selected
                Query query1 = databaseReference.orderByChild("state").equalTo(selectedState);
                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            districtNameList.clear();
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                String districtName = Objects.requireNonNull(dataSnapshot1.getValue(DistrictData.class)).getDistrictname();
                                districtNameList.add(districtName);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UploadVoterDetailsActivity.this,R.layout.support_simple_spinner_dropdown_item, districtNameList);
                            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinDistName.setAdapter(arrayAdapter);

                        } else {
                            districtNameList.clear();
                            progressDialog.dismiss();
                            Toast.makeText(UploadVoterDetailsActivity.this, "No data Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Retrieving Mandal Name as per District Name
                spinDistName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mandalNameList.clear();
                        String selectedDistrict = spinDistName.getSelectedItem().toString();

                        Query query = databaseReference1.orderByChild("district").equalTo(selectedDistrict);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                                        String mandalName = Objects.requireNonNull(dataSnapshot1.getValue(MandalData.class)).getMandal();
                                        mandalNameList.add(mandalName);
                                    }
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UploadVoterDetailsActivity.this,R.layout.support_simple_spinner_dropdown_item, mandalNameList);
                                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                    spinMandalName.setAdapter(arrayAdapter);
                                    progressDialog.dismiss();
                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(UploadVoterDetailsActivity.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImage==null) {
                    Toast.makeText(UploadVoterDetailsActivity.this, "Please select Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    String imgId = voterRef.push().getKey();
                    int selctedId = radioGender.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selctedId);

                    voterId = Objects.requireNonNull(etVoterId.getText()).toString().trim();
                    voterName = Objects.requireNonNull(etVoterName.getText()).toString().trim();
                    voterGender = radioButton.getText().toString();
                    voterDoB = Objects.requireNonNull(etDob.getText()).toString().trim();
                    voterState = spinStateName.getSelectedItem().toString();
                    voterDistrict = spinDistName.getSelectedItem().toString();
                    voterMandal = spinMandalName.getSelectedItem().toString();
                    voterDrno = Objects.requireNonNull(etDrNo.getText()).toString().trim();
                    voterLane = Objects.requireNonNull(etLane.getText()).toString().trim();
                    voterStreet = Objects.requireNonNull(etStreet.getText()).toString().trim();
                    voterPlace = Objects.requireNonNull(etPlace.getText()).toString().trim();
                    voterLandmark = Objects.requireNonNull(etLandmark.getText()).toString().trim();
                    voterEmail = Objects.requireNonNull(etEmail.getText()).toString().trim();
                    voterMobile = Objects.requireNonNull(etMobile.getText()).toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (voterId.isEmpty()) {
                        etVoterId.setError("Please enter Voter ID");
                    } else if(voterName.isEmpty()) {
                        etVoterName.setError("Please enter Voter Name");
                    } else if(voterGender.isEmpty()) {
                        Toast.makeText(UploadVoterDetailsActivity.this, "Please choose Gender", Toast.LENGTH_SHORT).show();
                    } else if(voterDoB.isEmpty()) {
                        etDob.setError("Please enter DOB");
                    } else if(voterDrno.isEmpty()) {
                        etDrNo.setError("Please enter Door No");
                    }  else if(voterLane.isEmpty()) {
                        etLane.setError("Please enter Lane ");
                    } else if(voterStreet.isEmpty()) {
                        etStreet.setError("Please enter Street");
                    } else if(voterPlace.isEmpty()) {
                        etPlace.setError("Please enter Place");
                    } else if(voterLandmark.isEmpty()) {
                        etLandmark.setError("Please enter Landmark");
                    } else if(voterEmail.isEmpty()) {
                        etEmail.setError("Please enter Email ID");
                    } else if(emailPattern.matches(voterEmail)) {
                         etEmail.setError("Please enter Valid Email ID");
                    }
                    else if(voterMobile.length()!=10) {
                        etMobile.setError("Please enter Valid Mobile Number");
                    }
                    else {

                        StorageReference ref = storageReference.child("Images/" + imgId);
                        ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful()) ;
                                Uri downloadUrl = uriTask.getResult();
                                VoterData voterData = new VoterData(downloadUrl.toString(), voterId, voterName, voterGender, voterDoB, voterState, voterDistrict, voterMandal, voterDrno, voterLane, voterStreet, voterPlace, voterLandmark, voterEmail, voterMobile,"No");
                                voterRef.child(imgId).setValue(voterData);
                                Toast.makeText(UploadVoterDetailsActivity.this, "Voter Registration Successful", Toast.LENGTH_SHORT).show();
                                regProgress.dismiss();

                                Intent intent = new Intent(UploadVoterDetailsActivity.this, RegistrationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                regProgress.dismiss();
                                Toast.makeText(UploadVoterDetailsActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                regProgress.show();
                                regProgress.setCanceledOnTouchOutside(false);
                                regProgress.setCancelable(false);

                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                regProgress.setMessage(
                                        "Uploaded "
                                                + (int) progress + "%");
                            }
                        });
                    }
                }

            }
        });










    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.image, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image:
                selectImage();
                break;
            case R.id.btnSubmit:
                break;
        }
    }

    /**
     * Alert dialog for capture or select from galley
     */
    private void selectImage() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Library",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadVoterDetailsActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Capture image from camera
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                   // photo = (Bitmap) data.getExtras().get("data");
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    selectedImage = data.getData();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(UploadVoterDetailsActivity.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                .circleCrop()
                                .placeholder(R.drawable.ic_add_a_photo_black_24dp))
                        .into(image);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                selectedImage = data.getData();
                Log.d("TAG", "onActivityResult: "+selectedImage);
                mPhotoFile = new File(getRealPathFromUri(selectedImage));
                Log.d("TAG", "onActivityResult: "+mPhotoFile);
                try {
                    Bitmap bitmap  = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                /*Glide.with(UploadVoterDetailsActivity.this)
                        .load(mPhotoFile)
                        .apply(new RequestOptions().centerCrop()
                                .circleCrop()
                                .placeholder(R.drawable.ic_add_a_photo_black_24dp))
                        .into(image);*/
            }
        }
    }

    /**
     * Requesting multiple permissions (storage and camera) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(
                        error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT)
                                .show())
                .onSameThread()
                .check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage(
                "This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Create file with current timestamp name
     *
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    /**
     * Get real file path from URI
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
