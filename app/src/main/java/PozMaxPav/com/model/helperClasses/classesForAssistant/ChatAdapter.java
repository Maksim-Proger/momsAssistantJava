package PozMaxPav.com.model.helperClasses.classesForAssistant;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import PozMaxPav.com.R;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int USER_MESSAGE = 1;
    private static final int ASSISTANT_MESSAGE = 2;
    private static final int EMPTY_LINE_MESSAGE = 3;
    private final List<ChatMessage> chatMessages;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = chatMessages.get(position);
        if (message.isUserMessage()) {
            return USER_MESSAGE;
        } else if (message.isEmptyLine()) {
            return EMPTY_LINE_MESSAGE;
        } else {
            return ASSISTANT_MESSAGE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == USER_MESSAGE) {
            View userMessageView = inflater.inflate(R.layout.user_message, parent, false);
            return new UserMessageViewHolder(userMessageView);
        } else if (viewType == EMPTY_LINE_MESSAGE) {
            View emptyLineView = inflater.inflate(R.layout.empty_line, parent, false);
            return new EmptyLineViewHolder(emptyLineView);
        } else if (viewType == ASSISTANT_MESSAGE) {
            View assistantMessageView = inflater.inflate(R.layout.assistant_message, parent, false);
            return new AssistantMessageViewHolder(assistantMessageView);
        }
        return null; // Возвращайте null, если viewType неизвестен
    }

    public static class EmptyLineViewHolder extends RecyclerView.ViewHolder {
        public TextView emptyLine;
        public EmptyLineViewHolder(@NonNull View itemView) {
            super(itemView);
            emptyLine = itemView.findViewById(R.id.emptyLine);
        }
    }

    public static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView userMessageText;
        public UserMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            userMessageText = itemView.findViewById(R.id.userMessageText);
        }
    }

    public static class AssistantMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView assistantMessageText;
        public AssistantMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            assistantMessageText = itemView.findViewById(R.id.assistantMessageText);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

        if (holder instanceof UserMessageViewHolder) {
            UserMessageViewHolder userMessageViewHolder = (UserMessageViewHolder) holder;
            userMessageViewHolder.userMessageText.setText(chatMessage.getMessage());
        } else if (holder instanceof  AssistantMessageViewHolder) {
            AssistantMessageViewHolder assistantMessageViewHolder = (AssistantMessageViewHolder) holder;
            assistantMessageViewHolder.assistantMessageText.setText(chatMessage.getMessage());
        } else if (holder instanceof EmptyLineViewHolder) {
            // Убираем видимость пустой строки
            EmptyLineViewHolder emptyLineViewHolder = (EmptyLineViewHolder) holder;
            emptyLineViewHolder.emptyLine.setText(chatMessage.getMessage());
            emptyLineViewHolder.emptyLine.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }
}
