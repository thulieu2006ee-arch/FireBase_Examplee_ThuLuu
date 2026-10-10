package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
  TextView txtTitle, txtContent;
  ImageView imgArticle;

  public UserViewHolder(@NonNull View itemView) {
    super(itemView);
    txtTitle = itemView.findViewById(R.id.txt_title);
    txtContent = itemView.findViewById(R.id.txt_content);
    imgArticle = itemView.findViewById(R.id.img_article);
  }
}