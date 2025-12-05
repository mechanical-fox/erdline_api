package app.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;



public class Util {

    /** Generate a random and unique name.*/
    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        String result = uuid.toString().replaceAll("-","");
        result = result.substring(0,16);
        return result;
    }


    /** Return the same string, with added at each line the number of space specified*/
    public static String addIndent(String str, int indent){
        if(str == null)
            return null;

        String result = "";
        String[] parts = str.split("\n");
        String indentString = "";

        for(int i = 0;i < indent;i++)
            indentString += " ";

        for(String part: parts)
            result += indentString + part + "\n";

        if(parts.length > 0 && str.charAt(str.length() - 1) != '\n')
            result = result.substring(0, result.length() - 1);

        return result;
    }


    /** This function read the content of a file, and return it. This function read all the file without limit
     * of size, and so will have problem to proceed large files (500Mo or more).*/
    public static String readAll(String filename) throws IOException{

        try(FileInputStream file_in = new FileInputStream(filename)){
            int length = file_in.available();
            byte[] buffer = new byte[length];
            int readed = file_in.read(buffer);

            if(readed != length)
                throw new IOException("Error I/O during reading the file " + filename);

            return new String(buffer);
        }
        
    }


    /** This function return the same text, but in html. Space and enter are converted, and all html characters like
    *  "<" are escaped to be displayed correctly, and for security purposes. */
    public static String toHtmlEscaped(String text){

        if(text == null)
            return null;
        else{
            String answer = text.replaceAll("&", "&amp;");

            answer = answer.replaceAll("<", "&lt;");
            answer = answer.replaceAll(">", "&gt;");
            answer = answer.replaceAll("\"", "&quot;");
            answer = answer.replaceAll("'", "&#39;");
            answer = answer.replaceAll(" ", "&nbsp;");
            answer = answer.replaceAll("\n", "<br/>");
            return answer;      
        }
    }

}
