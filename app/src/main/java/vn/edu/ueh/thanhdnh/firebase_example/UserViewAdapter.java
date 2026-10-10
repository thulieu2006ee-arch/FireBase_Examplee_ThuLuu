package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {
  private List<Article> articleList;

  public UserViewAdapter(List<Article> articleList) {
    this.articleList = articleList;
  }

  public void update(List<Article> newList) {
    this.articleList = newList;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list, parent, false);
    return new UserViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    Article article = articleList.get(position);
    holder.txtTitle.setText(article.getTitle());
    holder.txtContent.setText(article.getContent());

    // Sử dụng Glide tải ảnh từ URL
    Glide.with(holder.itemView.getContext())
            .load(article.getImage_url())
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imgArticle);
  }

  @Override
  public int getItemCount() {
    return articleList != null ? articleList.size() : 0;
  }
}