package edu.escuelaing.arep;

import spark.Request;
import spark.Response;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */

public class App {



    /**
     * This main method uses SparkWeb static methods and lambda functions to
     * create a simple Hello World web app. It maps the lambda function to the
     * /hello relative URL.
     */
    public static void main(String[] args) {

        port(getPort());
        get("/inputdata", (req, res) -> inputDataPage(req, res));
        get("/results", (req, res) -> resultsPage(req, res));
    }

    /**
     * Metodo por el cual se realizael ingreso de los datos
     * @param req request
     * @param res results
     * @return pageContent contenido de pagina
     */
    private static String inputDataPage(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Cuadrado de un número</h2>"
                + "<form action=\"/results\">"
                + "Ingrese un número <br>"
                + "<br>"
                + " <input type=\"text\" name='numbers'>"
                + " <br>"
                + "<br>"
                + "<input type=\"submit\" value=\"Submit\">"
                + "<input type=\"reset\" value=\"Reset\">"
                + "</form>"
                + "</body>"
                + "</html>";
        return pageContent;
    }


    /**
     * Metodo para crear la pagina que muestra el resultado de la operacion
     * @param req pagina request
     * @param res pagina res
     * @return result resultado de operacion
     */
    private static String resultsPage(Request req, Response res) {
        int digit= Integer.parseInt(String.valueOf(req));
        String result="";
        try {
            URL url = new URL(   "https://gxm4pigg58.execute-api.us-east-1.amazonaws.com/Beta" + "?value=" + digit);
            String temp;
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            while ((temp = reader.readLine()) != null) {
                result = result + temp;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;

    }
    /**
     * This method reads the default port as specified by the PORT variable in
     * the environment.
     *
     * Heroku provides the port automatically so you need this to run the
     * project on Heroku.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
