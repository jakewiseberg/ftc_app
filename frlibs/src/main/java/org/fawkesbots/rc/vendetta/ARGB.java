package org.fawkesbots.rc.vendetta;

public class ARGB {
    private int a;
    private int r;
    private int g;
    private int b;
    public ARGB(int c){
        b = (c)&0xFF;
        g = (c>>8)&0xFF;
        r = (c>>16)&0xFF;
        a = (c>>24)&0xFF;
    }

    public static int classify(char c){
        int col=0;
        switch(c){
            case 'r': col=1; break;
            case 'b': col=-1; break;
            case 'o': col=0; break;
            default: col=0; break;
        }
        return col; //returns 1 for red, -1 for blue
    }

    public ARGB set(int c){
        b = (c)&0xFF;
        g = (c>>8)&0xFF;
        r = (c>>16)&0xFF;
        a = (c>>24)&0xFF;
        return this;
    }

    public int get(char color){
        switch(color){
            case 'a': return a;
            case 'r': return r;
            case 'g': return g;
            case 'b': return b;
            default: return 404;
        }
    }

    @Override
    public String toString(){
        return "("+r+", "+g+", "+b+", "+a+")";
    }

    public char getColor(){
        if(get('r')>=200 && get('b')<=100 && get('g')>120 && get('g')<200) return 'y' ;
        if(get('r')>200 && get('b')<100) return 'r';
        if(get('r')<100 && get('b')>200) return 'b';
        else return 'o';
    }
}