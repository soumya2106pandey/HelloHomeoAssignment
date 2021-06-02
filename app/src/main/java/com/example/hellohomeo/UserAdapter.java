package com.example.hellohomeo;

import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hellohomeo.database.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final ArrayList<User> users = new ArrayList();
    public UserAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users_list_2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User currentUser = users.get(position);
        holder.getName().setText(currentUser.getName());
        holder.getAgency().setText(currentUser.getAgency());
        holder.getWiki_link().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = currentUser.getLink();
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(holder.getWiki_link().getContext(), Uri.parse(url));
            }
        });
        
        if(users.get(position).getStatus().equals("active")){
            holder.getActiveCircle().setImageResource(R.drawable.circle_green);
        }
        else{
            holder.getActiveCircle().setImageResource(R.drawable.circle_blue);
        }
        holder.getStatus().setText(currentUser.getStatus());
        Glide.with(holder.getProfileImage().getContext()).load(users.get(position).getImage()).into(holder.getProfileImage());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    void updateUsers(ArrayList<User> updatedUsers){
        users.clear();
        users.addAll(updatedUsers);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name, agency, wiki_link, status;
        private final ImageView profileImage, activeCircle;

        public ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.user_name);
            agency = view.findViewById(R.id.user_agency);
            wiki_link = view.findViewById(R.id.user_wiki_link);
            wiki_link.setPaintFlags(wiki_link.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
            status = view.findViewById(R.id.user_status);
            profileImage = view.findViewById(R.id.user_profile_imageView);
            activeCircle = view.findViewById(R.id.active_circle);
        }

        public TextView getName() {
            return name;
        }

        public TextView getAgency() {
            return agency;
        }

        public TextView getWiki_link() {
            return wiki_link;
        }

        public TextView getStatus() {
            return status;
        }

        public ImageView getProfileImage() {
            return profileImage;
        }

        public ImageView getActiveCircle() {
            return activeCircle;
        }
    }
}

