
public class MessageControl {
	
	private int messageCode;
	private String messageText;
	
	public MessageControl () {
		
	}
	
	public MessageControl(int messageCode, String messageText) {
		super();
		this.messageCode = messageCode;
		this.messageText = messageText;
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

	@Override
	public String toString() {
		return "MessageControl [messageCode=" + messageCode + ", messageText=" + messageText + "]";
	}
	
}
