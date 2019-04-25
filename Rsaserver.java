/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaserver;

/**
 *
 * @author vinita
 */
import java.io.*;
import java.math.BigInteger;
import java.net.*;
public class Rsaserver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
        // TODO code application logic here
        ServerSocket server=new ServerSocket(9998);
        Socket socket=server.accept();
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        DataInputStream in=new DataInputStream(socket.getInputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter p:");
        int p=Integer.parseInt(br.readLine());
        System.out.println();
        System.out.print("Enter q:");
        int q=Integer.parseInt(br.readLine());
        System.out.println();
        int n=p*q;
        int phin=(p-1)*(q-1);
        int e[]=new int[100];
        int gcd;
        int j=0;
        for(int i=2;i<phin;i++){
            gcd=calgcd(i,phin);
            if(gcd==1){
                e[j]=i;
                j++;
            }
        }
        System.out.println("Choose public key from below :");
        for(int i=0;i<j;i++){
            System.out.print(e[i]+" ");
        }
        System.out.println();
        int E=Integer.parseInt(br.readLine());
        
        
        out.write(p);
        out.write(q);
        out.write(E);
        
        double d=0.0;
        int i=1;
        while(true){
            d=(double)((phin*i)+1)/E;
            if(d==(int)d){
                break;
            }
            i++;
        }
        System.out.println("PUBLIC KEY PAIR:("+E+","+n+")");
       System.out.println("PRIVATE KEY PAIR:("+d+","+n+")");
        
        int cipher=in.read();
        System.out.println("Cipher text:"+cipher+" received from client");
        BigInteger b1=BigInteger.valueOf(cipher);
        BigInteger b2=BigInteger.valueOf((int)d);
        BigInteger b3=BigInteger.valueOf((int)n);
        
        BigInteger plaintext=b1.modPow(b2, b3);
        
        System.out.println("plaintext:"+plaintext);
        
              
        
    }
    
    public static int calgcd(int a,int b){
        int temp;
        while(true){
            temp=a%b;
            if(temp==0){
                return b;
            }
            a=b;
            b=temp;
            
        }
    }
    
}
