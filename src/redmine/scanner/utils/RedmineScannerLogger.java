package redmine.scanner.utils;

/**
 * Logger class for Redmine Scanner.
 * 
 * @author Anil Kurian
 *
 */
public class RedmineScannerLogger {

	public static final RedmineScannerLogger instance = new RedmineScannerLogger();
	
	public static RedmineScannerLogger getInstance() {
		return instance;
	}
	
	public void log(String msg) {
		System.out.println("MSG: "+msg);
	}
	
	public void err(String message) {
		System.out.println("ERR: "+message);
	}
}
