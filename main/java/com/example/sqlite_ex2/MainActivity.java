
package com.example.sqlite_ex2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adapter.ProductAdapter;
import com.example.model.Product;
import com.example.sqlite_ex2.databinding.ActivityMainBinding;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    database db;
    ProductAdapter adapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prepareBD();
        loadData();
    }

    private void loadData() {
        adapter = new ProductAdapter(MainActivity.this,R.layout.itemlist,getDataFromDb());
        binding.lvProduct.setAdapter(adapter);
    }

    private List<Product> getDataFromDb() {
        products = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + database.TBL_NAME);
        while (cursor.moveToNext()){
            products.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2)));
        }
        cursor.close();
        return products;
    }

    private void prepareBD() {
        db = new database(this);
        db.createSampleData();
    }
    public  void openEditDialog(Product p){

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_edit);
        EditText editName = dialog.findViewById(R.id.editName);
        editName.setText(p.getProductName());

        EditText editPrice = dialog.findViewById(R.id.editPrice);
        editPrice.setText(String.valueOf(p.getProductPrice()));

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                double price = Double.parseDouble(editPrice.getText().toString());

                //db.execSql("UPDATE "+ database.TBL_NAME + " SET "+ database.COL_NAME + " ='" +name+"'," + database.COL_PRICE+ "=" + price + " Where" + database.COL_CODE + "=" + p.getProductCode());
                db.execSql("UPDATE " + database.TBL_NAME + " SET " + database.COL_NAME + "='" + name + "'," + database.COL_PRICE + "=" + price + " WHERE " + database.COL_CODE + "=" + p.getProductCode());
                loadData();
                dialog.dismiss();
            }
        });
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnAdd){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog);

            EditText edtName = dialog.findViewById(R.id.editName);
            EditText edtPrice = dialog.findViewById(R.id.editPrice);

            Button btnSave = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //insert data
                    String name = edtName.getText().toString();
                    double price = Double.parseDouble(edtPrice.getText().toString());

                    db.execSql("INSERT INTO "+ database.TBL_NAME + " values(null,'" + name +"',"+price+")");
                    loadData();
                    dialog.dismiss();
                }
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void openDeleteConfirmDialog(Product p){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xac nhan xoa");

        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("ban co chac muon xoa san pham khong");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.execSql("Delete from "+ database.TBL_NAME + " Where " + database.COL_CODE + " = " + p.getProductCode());
                loadData();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

}