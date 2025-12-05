package app.util;


public class PasswordManager {

    static String mockPassword = null;

    /** A function used during unit test, to ask to don't retrieve password from the environment variable 
     * "ERDLINE_PASSWORD" than will not necessary be declared. But to retrieve it from the value passed in 
     * argument. The goal is of don't crash during unit test, with a message "ERDLINE_PASSWORD not declared".*/
    public static void mockPassword(String password){
        PasswordManager.mockPassword = password;
    }

    /** A function than check if the password is declared in the environment variable "ERDLINE_PASSWORD"
     * and else will display an error message, and exit. */
    public static void checkIfPasswordDeclared(){

        if(System.getenv("ERDLINE_PASSWORD") == null && mockPassword == null){

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

        if(mockPassword != null)
            return mockPassword;
        else
            return System.getenv("ERDLINE_PASSWORD");
    }
    
}
