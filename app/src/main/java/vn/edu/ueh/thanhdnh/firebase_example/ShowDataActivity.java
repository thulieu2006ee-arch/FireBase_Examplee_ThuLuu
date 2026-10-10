package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowDataActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserViewAdapter adapter;
    private List<Article> articleList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserViewAdapter(articleList);
        recyclerView.setAdapter(adapter);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // Thực thi phương thức GET theo Slide 91 (không cập nhật thường xuyên)
        db.collection("articles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    articleList.clear();
                    for (QueryDocumentSnapshot q : task.getResult()) {
                        Map<String, Object> data = q.getData();
                        String id = q.getId();
                        String title = (String) data.get("title");
                        String content = (String) data.get("content");
                        String imageUrl = (String) data.get("image_url");

                        Article article = new Article(id, title, content, imageUrl);
                        articleList.add(article);
                    }
                    adapter.update(articleList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ShowDataActivity.this, "Lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}