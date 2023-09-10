package PozMaxPav.com.all_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import PozMaxPav.com.R;
import PozMaxPav.com.model.Model;
import PozMaxPav.com.model.helperClasses.classesForAssistant.ChatAdapter;
import PozMaxPav.com.model.helperClasses.classesForAssistant.ChatMessage;

public class Assistant extends AppCompatActivity {

    private EditText editAssistant;
    private Button back_button_assistant;
    private ImageButton buttonAssistant;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ChatAdapter(this, chatMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addListenerOnButton();
    }

    private void addListenerOnButton() {
        editAssistant = findViewById(R.id.editAssistent);
        buttonAssistant = findViewById(R.id.buttonAssistent);
        back_button_assistant = findViewById(R.id.back_button_assistant);
        buttonAssistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        back_button_assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void sendMessage() {
        String messageText = editAssistant.getText().toString().trim();
        if (!messageText.isEmpty()) {
            // Создаем объект ChatMessage для нового сообщения пользователя
            ChatMessage userMessage = new ChatMessage(messageText, true,false);

            // Добавляем новое сообщение в список чата
            chatMessages.add(userMessage);

            // Сообщаем adapter о добавлении нового сообщения
            adapter.notifyDataSetChanged();

            // Обрабатываем сообщение от пользователя и даем на него ответ
            ChatMessage botMessage = generateBotResponse(messageText);

            // Добавляем пропуск строки
            ChatMessage emptyLine = new ChatMessage("", false, true);
            chatMessages.add(emptyLine);

            // Добавляем ответ бота в список чата
            chatMessages.add(botMessage);

            // Добавляем пропуск строки
            ChatMessage emptyLine2 = new ChatMessage("", false, true);
            chatMessages.add(emptyLine);

            // Сообщаем adapter о добавлении ответа ассистента
            adapter.notifyDataSetChanged();
        }
        editAssistant.setText("");
    }

    private ChatMessage generateBotResponse(String userMessage) {
        // Инициализируем экземпляр класса Model
        Model model = new Model();

        String assistantResponse = model.assistantMethod(getApplicationContext(),userMessage);
        return new ChatMessage(assistantResponse,false,false);
    }

}
