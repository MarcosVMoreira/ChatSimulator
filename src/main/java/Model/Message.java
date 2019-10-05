package Model;
import java.net.InetAddress;

public class Message {
	
	private int messageCode;
	
	private String messageText;
	
	private String messageSource;
	
	private String messageRecipient;
	
	public Message() {
	}

	public Message(int messageCode, String messageText, String messageSource, String messageRecipient) {
		super();
		this.messageCode = messageCode;
		this.messageText = messageText;
		this.messageSource = messageSource;
		this.messageRecipient = messageRecipient;
	}

	public int getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessageRecipient() {
		return messageRecipient;
	}

	public void setMessageRecipient(String messageRecipient) {
		this.messageRecipient = messageRecipient;
	}

	@Override
	public String toString() {
		return "Message [messageCode=" + messageCode + ", messageText=" + messageText + ", messageSource="
				+ messageSource + ", messageRecipient=" + messageRecipient + "]";
	}
	
}
