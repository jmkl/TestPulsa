package arm.testpulsa;

public class OperatorOption {
	public int id = 0;
	public String name = "";
	public String kode = "";
	
	public OperatorOption (int _id, String _name, String _kode) {
		id = _id;
		name = _name;
		kode = _kode;
	}
	
	public String toString() {
		return (name);
	}

}
