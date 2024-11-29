package com.example.seedbuy.view;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.seedbuy.R;
import com.example.seedbuy.viewmodel.ProductViewModel;

import java.io.File;

public class InventoryFragment extends Fragment {
    private Button btnAddProduct;
    private Button btnAddProductConfirm;
    private EditText edtProductName, edtProductPrice, edtProductQuantity;
    private Spinner spinnerCategory;
    private ImageView imgProduct;
    private File imageFile;
    private ProductViewModel productViewModel;

    // Constant for image picker intent request
    private static final int IMAGE_PICKER_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        // Initialize the ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        btnAddProduct = view.findViewById(R.id.btn_add_product);
        btnAddProduct.setOnClickListener(v -> showAddProductDialog());

        return view;
    }

    private void showAddProductDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_add_product);
        dialog.setCancelable(true);

        edtProductName = dialog.findViewById(R.id.edt_product_name);
        edtProductPrice = dialog.findViewById(R.id.edt_product_price);
        edtProductQuantity = dialog.findViewById(R.id.edt_product_quantity);
        spinnerCategory = dialog.findViewById(R.id.spinner_category);
        btnAddProductConfirm = dialog.findViewById(R.id.btn_add_product_confirm);
        imgProduct = dialog.findViewById(R.id.img_product);

        // Set up spinner for categories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.product_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Set up the image selection button
        Button btnSelectImage = dialog.findViewById(R.id.btn_select_image);
        btnSelectImage.setOnClickListener(v -> openImagePicker());

        // Confirm product addition
        btnAddProductConfirm.setOnClickListener(v -> {
            String productName = edtProductName.getText().toString().trim();
            String productPrice = edtProductPrice.getText().toString().trim();
            String productQuantity = edtProductQuantity.getText().toString().trim();
            String productCategory = spinnerCategory.getSelectedItem().toString();

            if (productName.isEmpty() || productPrice.isEmpty() || productQuantity.isEmpty() || productCategory.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Trigger ViewModel method to add product
                productViewModel.addProduct(productName, productPrice, productQuantity, productCategory, imageFile);

                productViewModel.getProductLiveData().observe(getViewLifecycleOwner(), product -> {
                    if (product != null) {
                        Toast.makeText(requireContext(), "Product added successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(requireContext(), "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    // Open the image picker
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the result is from the image picker
        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Uri imageUri = data.getData();

            try {
                // Use Glide to load the image URI into the ImageView
                Glide.with(this)
                        .load(imageUri)
                        .into(imgProduct);  // Display the image

                // Optionally, save the image file path if you need to upload it later
                imageFile = new File(getRealPathFromURI(imageUri));  // If you want to upload the image later

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error selecting image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Convert URI to file path (For API 29 and above, this might not work)
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        android.database.Cursor cursor = requireContext().getContentResolver().query(contentUri, proj, null, null, null);

        // For Android 10 and above, we don't use the file path directly
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }

        // Fallback to using a ContentResolver for API 29 and above
        if ("content".equalsIgnoreCase(contentUri.getScheme())) {
            return contentUri.getPath();
        }

        return null;
    }
}
