import java.util.HashMap;
import java.io.*;

public class Anagrams {
    /**
     *
     */

    private static final String QUIT = "quit";

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("GBR", "United Kingdom");
        hashMap.put("IDN", "Indonesia");
        hashMap.put("IND", "India");

        String str ="";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input here:");
        // 读取字符
        while (!str.equals("q")){
            str = (String) br.readLine();
            if(hashMap.get(str) == null)
                System.out.println("Invalid input!");
            else 
                System.out.println(hashMap.get(str));
        }
        
    }
}