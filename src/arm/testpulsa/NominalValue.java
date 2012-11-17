package arm.testpulsa;

public class NominalValue {
	public int id = 0;
	public String name = "";
	public int value = 0;
	
	public NominalValue (int _id, String _name, int _value) {
		id = _id;
		name = _name;
		value = _value;
	}
	
	public String toString() {
		return (name);
	}
}