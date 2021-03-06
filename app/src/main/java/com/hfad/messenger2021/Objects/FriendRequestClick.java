package com.hfad.messenger2021.Objects;


//Object used to pass data from friendRequestsAdapter to friendRequestFragment
public class FriendRequestClick {
    private boolean isAccepted;
    private int relationId;

    public FriendRequestClick(boolean isAccepted, int relationId) {
        this.isAccepted = isAccepted;
        this.relationId = relationId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }
}
