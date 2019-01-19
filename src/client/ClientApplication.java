/**
 * 
 */
package client;

import utilities.Config;

/**
 * @author Brandon
 *
 */
public class ClientApplication {
	public static void main(String[] argv) {
		new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	}
}
