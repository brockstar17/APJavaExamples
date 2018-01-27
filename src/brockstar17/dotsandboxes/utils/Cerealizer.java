package brockstar17.dotsandboxes.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Cerealizer
{

	public void sendSerialized(ObjectOutputStream oos) throws IOException;

	public void deserialize(ObjectInputStream ois) throws IOException, ClassNotFoundException;

}