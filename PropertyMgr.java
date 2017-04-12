import java.util.Properties;

public class PropertyMgr {
	static Properties properties = new Properties();
	static {
		try {
			properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank properties"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static String getProp(String key){
		return properties.getProperty(key);
	}
}
