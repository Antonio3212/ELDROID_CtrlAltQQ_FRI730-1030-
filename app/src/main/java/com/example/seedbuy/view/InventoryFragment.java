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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seedbuy.R;
import com.example.seedbuy.model.Product;
import com.example.seedbuy.viewmodel.InventoryViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {
    private Button btnAddProduct;
    private EditText edtProductName, edtProductPrice, edtProductQuantity;
    private Spinner spinnerCategory;
    private ImageView imgProduct;
    private File imageFile;
    private InventoryViewModel inventoryViewModel;
    private RecyclerView recyclerView;
    private InventoryProductAdapter inventoryProductAdapter;

    private static final int IMAGE_PICKER_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        // Initialize the ViewModel
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerview_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize the adapter with an empty list and set the click listener
        inventoryProductAdapter = new InventoryProductAdapter(new ArrayList<>(), requireContext(), product -> {
            // Open product details activity on item click
            Intent intent = new Intent(requireContext(), InventoryProductDetailsActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        recyclerView.setAdapter(inventoryProductAdapter);

        // Observe the products LiveData to update RecyclerView
        inventoryViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            if (products != null && !products.isEmpty()) {
                inventoryProductAdapter.updateProductList(products);
            }
        });

        // Observe error message
        inventoryViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });

        // Button to show dialog to add product
        btnAddProduct = view.findViewById(R.id.btn_add_product);
        btnAddProduct.setOnClickListener(v -> showAddProductDialog());

        // Fetch the current list of products
        inventoryViewModel.fetchProducts();

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
        imgProduct = dialog.findViewById(R.id.img_product);
        Button btnSelectImage = dialog.findViewById(R.id.btn_select_image);
        Button btnAddProductConfirm = dialog.findViewById(R.id.btn_add_product_confirm);

        // Set up category spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.product_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Handle image selection
        btnSelectImage.setOnClickListener(v -> openImagePicker());

        // Handle add product action
        btnAddProductConfirm.setOnClickListener(v -> {
            String name = edtProductName.getText().toString();
            String price = edtProductPrice.getText().toString();
            String quantity = edtProductQuantity.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            // Call ViewModel to add product
            if (imageFile != null) {
                inventoryViewModel.addProduct(name, price, quantity, category, imageFile);
                dialog.dismiss(); // Close the dialog after submitting
            } else {
                Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show();
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

        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Uri imageUri = data.getData();

            try {
                // Use Glide to load the image URI into the ImageView
                Glide.with(this)
                        .load(imageUri)
                        .into(imgProduct);

                // Save the image file for later use (upload)
                imageFile = new File(getRealPathFromURI(imageUri));

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error selecting image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Convert URI to file path (for API 29 and above, we can handle this with a ContentResolver)
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        android.database.Cursor cursor = requireContext().getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }

        if ("content".equalsIgnoreCase(contentUri.getScheme())) {
            return contentUri.getPath();
        }

        return null;
    }
}
