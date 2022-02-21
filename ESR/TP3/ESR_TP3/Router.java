import java.io.IOException;

import Node.RouterData;

// Nó responsável por redirecionar o tráfego na rede overlay
public class Router {
    private static RouterData rt;
    public static void main(String[] args) {
        try {
			rt = new RouterData(args);
            rt.start();
            
		} catch (IOException e) {
			e.printStackTrace();
		}   
    }
}
