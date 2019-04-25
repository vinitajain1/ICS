/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desf;

/**
 *
 * @author vinita
 */
public class Desf {

    /**
     * @param args the command line arguments
     */
    
    static int IP[]={2,6,3,1,4,8,5,7};
    static int IPINV[]={4,1,3,5,7,2,8,6};
    static int plaintext[]={1,0,0,1,0,0,1,1};
    static int EP[]={4,1,2,3,2,3,4,1};
    static int s0[][]={{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,2}};
    static int s1[][]={{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}};
    static int ciphertext[]=new int[8];
    static int pbox[]={2,4,3,1};
    static int key1[]={1,1,0,0,1,1,0,1};
    static int key2[]={1,1,0,0,0,1,1,0};
    static int keyx[]={0,1,0,1,1,1,0,1,0,0};
    public static void main(String[] args) {
        Desf d=new Desf();
        System.out.print("plaintext:");
        d.print(plaintext);
        ciphertext=d.encrypt(plaintext);
        System.out.print("ciphertext:");
        d.print(ciphertext);
        
        System.out.println("DECRYPTION:");
        System.out.print("ciphertext:");
        d.print(ciphertext);
        plaintext=d.decrypt(ciphertext);
        System.out.print("plaintext:");
        d.print(plaintext);
        
        d.genkey(keyx);
        
    }
    
    public int[] encrypt(int plain[]){
        
        int postip[]=new int[8];
        int left[]=new int[4];
        int right[]=new int[4];
        int postfk[]=new int[4];
        int temp[]=new int[4];
        int postfk2[]=new int[8];
        int postipinv[]=new int[8];
        //step1:initial permutation
        for(int i=0;i<8;i++){
            postip[i]=plain[IP[i]-1];
        }
        System.out.print("POST INITIAL PERMUTATION:");
        print(postip);
        
        //step2:
        for(int i=0;i<4;i++){
            left[i]=postip[i];
            right[i]=postip[i+4];
        }
        
        System.out.print("LEFT:");
        print(left);
        System.out.print("RIGHT:");
        print(right);
        
        postfk=fk(right,key1);
        left=xor(postfk,left);
        
        for(int i=0;i<4;i++){
            temp[i]=left[i];
            left[i]=right[i];
            right[i]=temp[i];
        }
        System.out.println("AFTER SWAPPING");
        System.out.print("LEFT:");
        print(left);
        System.out.print("RIGHT:");
        print(right);
        
        postfk=fk(right,key2);
        left=xor(postfk,left);
        
        for(int i=0;i<4;i++){
            postfk2[i]=left[i];
            postfk2[i+4]=right[i];
        }
        
        System.out.print("POST FK2:");
        
        print(postfk2);
        
        for(int i=0;i<8;i++){
            postipinv[i]=postfk2[IPINV[i]-1];
        }
        
        
           
        return postipinv;
    }
    
    public int[] decrypt(int plain[]){
        
        int postip[]=new int[8];
        int left[]=new int[4];
        int right[]=new int[4];
        int postfk[]=new int[4];
        int temp[]=new int[4];
        int postfk2[]=new int[8];
        int postipinv[]=new int[8];
        //step1:initial permutation
        for(int i=0;i<8;i++){
            postip[i]=plain[IP[i]-1];
        }
        System.out.print("POST INITIAL PERMUTATION:");
        print(postip);
        
        //step2:
        for(int i=0;i<4;i++){
            left[i]=postip[i];
            right[i]=postip[i+4];
        }
        
        System.out.print("LEFT:");
        print(left);
        System.out.print("RIGHT:");
        print(right);
        
        postfk=fk(right,key2);
        left=xor(postfk,left);
        
        for(int i=0;i<4;i++){
            temp[i]=left[i];
            left[i]=right[i];
            right[i]=temp[i];
        }
        System.out.println("AFTER SWAPPING");
        System.out.print("LEFT:");
        print(left);
        System.out.print("RIGHT:");
        print(right);
        
        postfk=fk(right,key1);
        left=xor(postfk,left);
        
        for(int i=0;i<4;i++){
            postfk2[i]=left[i];
            postfk2[i+4]=right[i];
        }
        
        System.out.print("POST FK2:");
        
        print(postfk2);
        
        for(int i=0;i<8;i++){
            postipinv[i]=postfk2[IPINV[i]-1];
        }
        
        
           
        return postipinv;
    }
    
    
    
    public int[] fk(int r[],int key[]){
        
        int postep[]=new int[8];
        int postaddkey[]=new int[8];
        int postsbox[]=new int[4];
        int postpbox[]=new int[4];
        for(int i=0;i<4;i++){
            postep[i]=r[EP[i]-1];
            postep[i+4]=r[EP[i+4]-1];
        }
        System.out.print("POST EXPANSION PERMUTATION:");
        print(postep);
        postaddkey=xor(postep,key);
        System.out.print("POST KEY ADDITION:");
        print(postaddkey);
        int r1,c1,r2,c2;
        r1=postaddkey[0]*2+postaddkey[3];
        c1=postaddkey[1]*2+postaddkey[2];
        r2=postaddkey[4]*2+postaddkey[7];
        c2=postaddkey[5]*2+postaddkey[6];
        
        System.out.println("r1:"+r1+" c1:"+c1+" r2:"+r2+" c2:"+c2);
        
        String x=Integer.toBinaryString(s0[r1][c1]);
        String y=Integer.toBinaryString(s1[r2][c2]);
        if(x.length()==1){
            x="0"+x;
        }
        if(y.length()==1){
            y="0"+y;
        }
        String z=x+y;
        for(int i=0;i<4;i++){
            postsbox[i]=Character.getNumericValue(z.charAt(i));
        }
        
        System.out.print("POST SBOX:");
        print(postsbox);
        
        for(int i=0;i<4;i++){
            postpbox[i]=postsbox[pbox[i]-1];
        }
        System.out.print("POST PBOX:");
        print(postpbox);
        
        
        
        
        return postpbox;
    }
    
    public int[] xor(int x[],int[]y){
        int temp[]=new int[x.length];
        for(int i=0;i<x.length;i++){
            temp[i]=x[i]^y[i];
        }
        return temp;
    }
    
    public void genkey(int a[]){
        int left[]=new int[5];
        int right[]=new int[5];
        int p10[]={3,5,2,7,4,10,1,9,8,6};
        int p8[]={6,3,7,4,8,5,10,9};
        int postp10[]=new int[10];
        int postshift[]=new int[10];
        int key1[]=new int[8];
        int key2[]=new int[8];
        
        for(int i=0;i<10;i++){
            postp10[i]=a[p10[i]-1];
        }
        System.out.print("POST P10:");
        print(postp10);
        
        for(int i=0;i<5;i++){
            left[i]=postp10[i];
            right[i]=postp10[i+5];
        }
        System.out.print("LEFT:");
        print(left);
        System.out.print("RIGHT:");
        print(right);
        
        for(int i=0;i<4;i++){
            postshift[i]=left[i+1];
            postshift[i+5]=right[i+1];
        }
        postshift[4]=left[0];
        postshift[9]=right[0];
        
        System.out.print("POSTSHIFT:");
        print(postshift);
        
        for(int i=0;i<8;i++){
            key1[i]=postshift[p8[i]-1];
        }
        
        System.out.print("POSTP8:");
        print(key1);
        
        for(int i=0;i<5;i++){
            left[i]=postshift[i];
            right[i]=postshift[i+5];
        }
        
        System.out.print("LEFT:");
        print(left);
        System.out.print("RIGHT:");
        print(right);
        
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                postshift[j]=left[j+1];
                postshift[j+5]=right[j+1];
            }
            postshift[4]=left[0];
            postshift[9]=right[0];
            for(int k=0;k<5;k++){
            left[k]=postshift[k];
            right[k]=postshift[k+5];
        }
        }
        for(int i=0;i<8;i++){
            key2[i]=postshift[p8[i]-1];
        }
        
        System.out.print("key1:");
        print(key1);
        System.out.print("key2:");
        print(key2);
    }
    
    public static void print(int []a){
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    
}
