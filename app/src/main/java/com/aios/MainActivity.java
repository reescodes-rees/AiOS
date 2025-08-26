package com.aios;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editText;
    private Button sendButton;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private AiService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        aiService = new AiService();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editText.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    // Add user message
                    Message userMessage = new Message(userInput, Message.Sender.USER);
                    messageList.add(userMessage);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);

                    // Get AI response
                    Message aiMessage = aiService.getResponse(userInput);
                    messageList.add(aiMessage);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);

                    // Scroll to the bottom
                    recyclerView.scrollToPosition(messageList.size() - 1);
                    editText.setText("");
                }
            }
        });
    }
}
