/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffiehellmanclient;

/**
 *
 * @author vinita
 */
import java.io.*;
import java.net.*;
public class Diffiehellmanclient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
        
        Socket socket=new Socket("localhost",9999);
        DataInputStream in=new DataInputStream(socket.getInputStream());
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        System.out.println("Client socket created!!");
        int p=23;
        int q=5;
        int privatekey =20;
        int publickey=(int)(Math.pow(q, privatekey)%p);
        out.write(p);
        out.write(q);
        out.write(publickey);
        
        int publicserverkey=in.read();
        
        System.out.println("Modulus "+p+" sent to server:");
        System.out.println("Base "+q+" sent to server:");
        System.out.println("Private key:"+privatekey);
        System.out.println("Public Client key "+publickey+" sent to server");

        System.out.println("Public server key "+publicserverkey+" received from server");
        
        int key=(int)(Math.pow(publicserverkey, privatekey)%p);
        System.out.println("KEY:"+key);
        socket.close();
        in.close();
        out.close();
        
        
        
        
        
    }
    
}
