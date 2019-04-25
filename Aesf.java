/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesf;

import java.math.BigInteger;

/**
 *
 * @author vinita
 */
public class Aesf {

    /**
     * @param args the command line arguments
     */
    static int plaintext[]={1,1,0,1,0,1,1,1,0,0,1,0,1,0,0,0};
    static int ciphertext[]=new int[16];
    static int sbox[][]={{9,4,10,11},{13,1,8,5},{6,2,0,3},{12,14,15,7}};
    static int invsbox[][]={{10,5,9,11},{1,7,8,15},{6,0,2,3},{12,4,13,14}};
    static int key0[]={0,1,0,0,1,0,1,0,1,1,1,1,0,1,0,1};
    static int key1[]={1,1,0,1,1,1,0,1,0,0,1,0,1,0,0,0};
    static int key2[]={1,0,0,0,0,1,1,1,1,0,1,0,1,1,1,1};
    public static void main(String[] args) {
        // TODO code application logic here
        Aesf a=new Aesf();
        System.out.print("Plaintext:");
        a.print(plaintext);
        
        ciphertext=a.encrypt(plaintext);
        System.out.print("Ciphertext:");
        a.print(ciphertext);
    }
    public int[] encrypt(int text[]){
        int postaddround[]=new int[16];
        int postnibblesub[]=new int[16];
        int postshift[]=new int[16];
        int postmixcolumn[]=new int[16];
        postaddround=xor(text,key0);
        
        System.out.print("POST ADD ROUND KEY:");
        print(postaddround);
        //ROUND1:
        System.out.println("ROUND1:");
        //step1: NIBBLE SUBSTITUTION
        postnibblesub=nibblesub(postaddround);
        System.out.print("POST NIBBLE SUBSTITUTION:");
        print(postnibblesub);
        
        //step2:SHIFT ROW:
        postshift=shiftrow(postnibblesub);
        System.out.print("POST SHIFT ROW:");
        print(postshift);
        
        //step3:MIX COLUMN:
        postmixcolumn=mixcolumn(postshift);
        System.out.print("POST MIX COLUMN:");
        print(postmixcolumn);
        
        postaddround=xor(postmixcolumn,key1);
        System.out.print("POST ADD ROUND KEY:");
        print(postaddround);
        
        //ROUND2:
        
        System.out.println("ROUND2:");
        //step1: NIBBLE SUBSTITUTION
        postnibblesub=nibblesub(postaddround);
        System.out.print("POST NIBBLE SUBSTITUTION:");
        print(postnibblesub);
        
        //step2:SHIFT ROW:
        postshift=shiftrow(postnibblesub);
        System.out.print("POST SHIFT ROW:");
        print(postshift);
        
        postaddround=xor(postshift,key2);
        
        System.out.print("POST ADD ROUND KEY:");
        print(postaddround);
        
        
        return postaddround;
    }
    public int[] nibblesub(int var[]){
        int temp[]=new int[16];
        int t[]=new int[4];
        for(int i=0;i<4;i++){
            
            for(int j=0;j<4;j++){
             
                t[j]=var[j+i*(2*2)];
                
            }
                int r1=t[0]*2+t[1];
                int c1=t[2]*2+t[3];
                String x=decimaltobinary(sbox[r1][c1]);
                for(int k=0;k<4;k++){
                    temp[k+i*(2*2)]=Character.getNumericValue(x.charAt(k));
                }
        }

        return temp;
    }
    
    public int[] shiftrow(int var[]){
        int temp[]=new int[16];
        
        for(int i=0;i<4;i++){
            temp[i]=var[i];
            temp[i+4]=var[i+12];
            temp[i+8]=var[i+8];
            temp[i+12]=var[i+4];
        }
        
        return temp;
        
    }
    
    public int[] mixcolumn(int var[]){
        int a1[]=new int[4];
        int a2[]=new int[4];
        int a3[]=new int[4];
        int a4[]=new int[4];
        int s00[],s10[],s01[],s11[];
        for(int i=0;i<4;i++){
            a1[i]=var[i];
            a2[i]=var[i+12];
            a3[i]=var[i+8];
            a4[i]=var[i+4];
        }
        s00=xor(mult(a1,1),mult(a2,4));
        s10=xor(mult(a3,1),mult(a4,4));
        s01=xor(mult(a1,4),mult(a2,1));
        s11=xor(mult(a3,4),mult(a4,1));
        
        for(int i=0;i<4;i++){
            var[i]=s00[i];
            var[i+4]=s01[i];
            var[i+8]=s10[i];
            var[i+12]=s11[i];
        }
        
        return var;
        
        
    }
    
    public int[] xor(String a,String b){
        int temp[]=new int[4];
        a=hexcoverttobinary(a);
        b=hexcoverttobinary(b);
        for(int i=0;i<4;i++){
            temp[i]=Character.getNumericValue(a.charAt(i))^Character.getNumericValue(b.charAt(i));
        }
        return temp;
    }
    
    public String hexcoverttobinary(String a){
        String x=new BigInteger(a,16).toString(2);
        if(x.length()==1)
            x="000"+x;
        if(x.length()==2)
            x="00"+x;
        if(x.length()==3)
            x="0"+x;
        return x;
    }
    
    public String mult(int a[],int num){
        int h=a[0]*8+a[1]*4+a[2]*2+a[3];
        int val=num*h;
        if(num!=9){
            if(val>=64)
                val^=76;
            if(val>=32)
                val^=38;
            if(val>=16)
                val^=19;
            
            
                        
        }
        //System.out.println(Integer.toHexString(val));
        return Integer.toHexString(val);
    }
    
    
    public String decimaltobinary(int a){
        String x=Integer.toBinaryString(a);
        if(x.length()==1)
            x="000"+x;
        if(x.length()==2)
            x="00"+x;
        if(x.length()==3)
            x="0"+x;
        
        return x;
             
    } 
    
    public int[] xor(int a[],int b[]){
        int temp[]=new int[a.length];
        for(int i=0;i<a.length;i++){
            temp[i]=a[i]^b[i];
        }
        return temp;
    }
    public void print(int a[]){
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    
}
