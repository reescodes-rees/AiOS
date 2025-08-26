package com.aios;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.messageTextView.setText(message.getText());

        if (message.getSender() == Message.Sender.USER) {
            holder.messageLayout.setGravity(Gravity.END);
            holder.messageTextView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.message_bubble_user));
        } else {
            holder.messageLayout.setGravity(Gravity.START);
            holder.messageTextView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.message_bubble_ai));
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout messageLayout;
        TextView messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageLayout = itemView.findViewById(R.id.messageLayout);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }
}
