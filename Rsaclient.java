/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaclient;

/**
 *
 * @author vinita
 */
import java.io.*;
import java.math.BigInteger;
import java.net.*;
public class Rsaclient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
        Socket socket=new Socket("localhost",9998);
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        DataInputStream in=new DataInputStream(socket.getInputStream());
        int p=in.read();
        int q=in.read();
        int e=in.read();
        System.out.print("Enter plaintext:");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int plain=Integer.parseInt(br.readLine());
        int n=p*q;
        int cipher=(int)(Math.pow(plain, e)%n);
        System.out.println("Ciphertext:"+cipher);
        out.write(cipher);
        
                
    }
    
}
