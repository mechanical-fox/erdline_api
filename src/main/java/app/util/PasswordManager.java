package app.util;


public class PasswordManager {

    /** A function than check if the password is declared in the environment variable "ERDLINE_PASSWORD"
     * and else will display an error message, and exit. */
    public static void checkIfPasswordDeclared(){

        if(System.getenv("ERDLINE_PASSWORD") == null){

            String msg = "\n\nTo function the application need the following variable :\n\n";
            msg += "    ERDLINE_PASSWORD\n";
            msg += "        A password used to prevent unauthorized persons to access admin features\n\n";

            System.err.println(msg);
            System.exit(-1);
        }   
    }


    /** Return the password to use by the API to protect admin features */
    public static String getErdlinePassword(){
        PasswordManager.checkIfPasswordDeclared();

        return System.getenv("ERDLINE_PASSWORD");
    }
    
}
