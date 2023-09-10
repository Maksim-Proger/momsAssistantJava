package PozMaxPav.com.model.helperClasses.classesForAssistant;

public class ChatMessage {
    private String message;
    private boolean userMessage;
    private boolean isEmptyLine;

    public ChatMessage(String message, boolean userMessage, boolean isEmptyLine) {
        this.message = message;
        this.userMessage = userMessage;
        this.isEmptyLine = isEmptyLine;
    }
    public String getMessage() {
        return message;
    }
    public boolean isUserMessage() {
        return userMessage;
    }

    public boolean isEmptyLine() {
        return isEmptyLine;
    }
}
