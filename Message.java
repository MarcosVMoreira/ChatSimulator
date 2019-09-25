import java.net.InetAddress;

public class Message {
	
	private int messageCode;
	
	private String messageText;
	
	private InetAddress messageSource;
	
	private InetAddress messageRecipient;
	
	public Message() {
	}

	public Message(int messageCode, String messageText, InetAddress messageSource, InetAddress messageRecipient) {
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

	public InetAddress getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(InetAddress messageSource) {
		this.messageSource = messageSource;
	}

	public InetAddress getMessageRecipient() {
		return messageRecipient;
	}

	public void setMessageRecipient(InetAddress messageRecipient) {
		this.messageRecipient = messageRecipient;
	}
	
	

}
