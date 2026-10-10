package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
  private EditText etTitle, etContent, etImageUrl;
  private Button btnSave, btnViewList;
  private FirebaseFirestore db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    etTitle = findViewById(R.id.etTitle);
    etContent = findViewById(R.id.etContent);
    etImageUrl = findViewById(R.id.etImageUrl);
    btnSave = findViewById(R.id.btnSave);
    btnViewList = findViewById(R.id.btnViewList);

    // Khởi tạo Firestore theo mục 7 trong file hướng dẫn
    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();

    // Gán sẵn link ảnh mẫu tiện test trên máy ảo
    etImageUrl.setText("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRj1BHCbnijU-dUK-t6anlCtlOct0SVob-EepPCwwUweA&s");

    // 1. Thêm dữ liệu theo Slide 90: db.collection(...).add(...).addOnSuccessListener(...).addOnFailureListener(...)
    btnSave.setOnClickListener(v -> {
      String title = etTitle.getText().toString().trim();
      String content = etContent.getText().toString().trim();
      String imageUrl = etImageUrl.getText().toString().trim();

      if (title.isEmpty() || content.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập đầy đủ Title và Content", Toast.LENGTH_SHORT).show();
        return;
      }

      Article article = new Article("", title, content, imageUrl);

      db.collection("articles").add(article)
              .addOnSuccessListener(documentReference -> {
                // Cập nhật lại id của document vừa tạo vào Article
                documentReference.update("id", documentReference.getId());
                Toast.makeText(MainActivity.this, "Thêm Article thành công!", Toast.LENGTH_SHORT).show();
                etTitle.setText("");
                etContent.setText("");
              })
              .addOnFailureListener(e -> {
                Toast.makeText(MainActivity.this, "Lỗi thêm dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
              });
    });

    // 2. Chuyển sang màn hình xem danh sách
    btnViewList.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
      startActivity(intent);
    });
  }
}