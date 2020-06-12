package com.example.a222latest;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;
    private FirebaseAuth mAuth;

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView senderMessageText, receiverMessageText, senderTimeText, receiverTimeText;

        public MessageViewHolder(@NonNull View v) {
            super(v);
            senderMessageText = v.findViewById(R.id.senderMessageText);
            receiverMessageText = v.findViewById(R.id.receiverMessageText);
            senderTimeText = v.findViewById(R.id.senderMessageTime);
            receiverTimeText = v.findViewById(R.id.reciverMessageTime);
        }

    }

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_layout, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String currentUserMail = mAuth.getCurrentUser().getEmail();
        currentUserMail = MessagingActivity.emailToId(currentUserMail);
        Message message = messageList.get(position);
        // Don't optimize here to set invisible inside if.
        // It causes a bug (messages disappear when scrolling).
        holder.receiverMessageText.setVisibility(View.INVISIBLE);
        holder.senderMessageText.setVisibility(View.INVISIBLE);
        holder.senderTimeText.setText(message.getDate() + "  " + message.getTime());
        holder.receiverTimeText.setText(message.getDate() + "  " + message.getTime());
        holder.senderTimeText.setVisibility(View.INVISIBLE);
        holder.receiverTimeText.setVisibility(View.INVISIBLE);

        if (currentUserMail.equals(message.getSenderId())) {
            holder.senderMessageText.setVisibility(View.VISIBLE);
            holder.senderTimeText.setVisibility(View.VISIBLE);
            holder.senderMessageText.setBackgroundResource(R.drawable.message_sender);
            holder.senderMessageText.setText(message.getText());
        } else {
            holder.receiverTimeText.setVisibility(View.VISIBLE);
            holder.receiverMessageText.setVisibility(View.VISIBLE);
            holder.receiverMessageText.setBackgroundResource(R.drawable.message_receiver);
            String text = message.getSenderName() + "\n" + message.getText();
            SpannableString spanString = new SpannableString(text);
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, text.indexOf('\n'), 0);
            spanString.setSpan(new UnderlineSpan(), 0, text.indexOf('\n'), 0);
            holder.receiverMessageText.setText(spanString);
        }


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}
