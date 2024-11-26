package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    private OnUserClickListener onUserClickListener;

    public UserAdapter(Context context, List<User> userList, OnUserClickListener onUserClickListener) {
        this.context = context;
        this.userList = userList;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main2, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.firstName.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
        holder.hobby.setText(user.getHobby());
        // Set user image if available
        if (user.getImageUri() != null) {
            holder.imageView.setImageURI(user.getImageUri());
        } else {
            holder.imageView.setImageResource(R.drawable.cat); // Default image for all users
        }

        // Handle delete button
        holder.deleteButton.setOnClickListener(v -> {
            userList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, userList.size());
        });

        // Handle edit button
        holder.editButton.setOnClickListener(v -> {
            onUserClickListener.onUserEdit(position, user);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, hobby;
        ImageView imageView;
        Button editButton, deleteButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            hobby = itemView.findViewById(R.id.hobby);
            imageView = itemView.findViewById(R.id.imageView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
    public interface OnUserClickListener {
        void onUserEdit(int position, User user);
    }
}
