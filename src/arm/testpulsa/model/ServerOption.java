package arm.testpulsa.model;

public class ServerOption {
	public int id = 0;
	public String name = "";
	public String server = "";
	
	public ServerOption (int _id, String _name, String _server) {
		id = _id;
		name = _name;
		server = _server;
	}
	
	public String toString() {
		return (name);
	}

}
