import java.net.InetAddress;

public class User {
	
	private String userName;
	
	private InetAddress userIP;
	
	private int userPort;
	
	public User() {
	}
	
	public User(String userName, InetAddress userIP, int userPort) {
		this.userName = userName;
		this.userIP = userIP;
		this.userPort = userPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public InetAddress getUserIP() {
		return userIP;
	}

	public void setUserIP(InetAddress userIP) {
		this.userIP = userIP;
	}

	public int getUserPort() {
		return userPort;
	}

	public void setUserPort(int userPort) {
		this.userPort = userPort;
	}

	
	

}
