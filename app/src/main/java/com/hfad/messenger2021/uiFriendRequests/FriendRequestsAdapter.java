package com.hfad.messenger2021.uiFriendRequests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.messenger2021.Objects.FriendRequestClick;
import com.hfad.messenger2021.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class FriendRequestsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Integer> userIdList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    List<Integer> relationId = new ArrayList<>();
    final int EMPTY_VIEW = 0;

    private final PublishSubject<String> clickPublisher = PublishSubject.create();

    //Publish subject with custom object named RequestResponse is used to transfer data
    //(boolean isAccepted, int relationId) using single rxJava subject onClick
    private final PublishSubject<FriendRequestClick> requestClickPublisher = PublishSubject.create();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_empty, parent, false);
            return new emptyFriendRequestsViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
            return new friendRequestViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof friendRequestViewHolder){
            ((friendRequestViewHolder) holder).request_name.setText(nameList.get(position));

            //On accept click listener
            ((friendRequestViewHolder) holder).request_accept.setOnClickListener(view -> {
                FriendRequestClick response = new FriendRequestClick(true, relationId.get(position));
                requestClickPublisher.onNext(response);

                //Remove request no matter if the request has been accepted or not
                removeItemAtPosition(position);
            });
            //On decline click listener
            ((friendRequestViewHolder) holder).request_decline.setOnClickListener(view -> {
                FriendRequestClick response = new FriendRequestClick(false, relationId.get(position));
                requestClickPublisher.onNext(response);

                //Remove request no matter if the request has been accepted or not
                removeItemAtPosition(position);
            });
        }
        else{
            //This click opens new fragment
            ((emptyFriendRequestsViewHolder) holder).emptyButton.setOnClickListener(view -> {
                clickPublisher.onNext("Click");
            });
        }
    }

    @Override
    public int getItemCount() {
        if (userIdList.size() == 0){
            return 1;
        }
        else{
            return userIdList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (userIdList.size() == 0){
            return EMPTY_VIEW;
        }
        else{
            return 1;
        }
    }

    private void removeItemAtPosition(int position){
        userIdList.remove(position);
        nameList.remove(position);
        relationId.remove(position);
        notifyItemRemoved(position);
    }

    public PublishSubject<String> getClick() {
        return clickPublisher;
    }

    public PublishSubject<FriendRequestClick> getRequestClickPublisher() {
        return requestClickPublisher;
    }

    public static class emptyFriendRequestsViewHolder extends RecyclerView.ViewHolder{
        public final TextView emptyTextView;
        public final ImageView emptyImageView;
        public final Button emptyButton;

        public emptyFriendRequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            emptyImageView = itemView.findViewById(R.id.empty_friend_requests_image);
            emptyTextView = itemView.findViewById(R.id.empty_friend_requests_text);
            emptyButton = itemView.findViewById(R.id.empty_friend_requests_button);
        }
    }

    public static class friendRequestViewHolder extends  RecyclerView.ViewHolder{
        public final TextView request_name;
        public final ImageView request_image;
        public final ImageView request_accept;
        public final ImageView request_decline;

        public friendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            request_name = itemView.findViewById(R.id.friend_request_name);
            request_image = itemView.findViewById(R.id.friend_request_image);
            request_accept = itemView.findViewById(R.id.friend_request_accept);
            request_decline  = itemView.findViewById(R.id.friend_request_decline);
        }
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public void setRelationId(List<Integer> relationId) {
        this.relationId = relationId;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }

}
