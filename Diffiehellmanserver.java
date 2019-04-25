/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffiehellmanserver;

import java.io.*;
import java.net.*;
public class Diffiehellmanserver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
        // TODO code application logic here
        ServerSocket server=new ServerSocket(9999);
        System.out.println("Waiting for client!");
        Socket socket=server.accept();
        DataInputStream in =new DataInputStream(socket.getInputStream());
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        //out.writeUTF("Welcome to Diffie Hellman!");
        System.out.println("Connected to:localhost portno:9999");
        int p=in.read();
        int q=in.read();
        int publicclientkey=in.read();
        int privatekey=3;
        int publicserverkey=(int)(Math.pow(q,privatekey)%p);
        out.write(publicserverkey);
        
        System.out.println("Modulus "+p+" received from client");
        System.out.println("Base "+q+" received from client");
        System.out.println("Public server key "+publicserverkey+" sent to client");
        System.out.println("public client key "+publicclientkey+" received from client");
        int key=(int)(Math.pow(publicclientkey,privatekey)%p);
        System.out.println("Key:"+key);
        socket.close();
        in.close();
        out.close();
    }
    
}
