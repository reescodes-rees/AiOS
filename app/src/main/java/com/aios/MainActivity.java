package com.aios;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int MESSAGE_ID_SYSTEM_MONITOR = 1;
    private static final long SYSTEM_MONITOR_INTERVAL_MS = 20000; // 20 seconds

    private RecyclerView recyclerView;
    private EditText editText;
    private Button sendButton;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private AiService aiService;
    private AiOSCore aiOSCore;
    private SystemMonitor systemMonitor;
    private Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Standard UI setup
        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);

        // Core AI and Service initialization
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        // Setup Handler to receive messages from background services
        // This must be initialized before AiOSCore, which needs it.
        uiHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull android.os.Message msg) {
                if (msg.what == MESSAGE_ID_SYSTEM_MONITOR) {
                    Message systemMessage = (Message) msg.obj;
                    messageList.add(systemMessage);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerView.scrollToPosition(messageList.size() - 1);
                }
            }
        };

        aiOSCore = new AiOSCore(uiHandler);
        aiService = new AiService(aiOSCore);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        // Setup and start the System Monitor
        systemMonitor = new SystemMonitor(aiOSCore, SYSTEM_MONITOR_INTERVAL_MS);

        // Setup user input handling
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
                    // Don't add the immediate response here for async commands like fetch
                    if (aiMessage != null) {
                        messageList.add(aiMessage);
                        messageAdapter.notifyItemInserted(messageList.size() - 1);
                    }

                    // Scroll to the bottom
                    recyclerView.scrollToPosition(messageList.size() - 1);
                    editText.setText("");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        systemMonitor.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        systemMonitor.stop();
    }
}
