package org.fawkes.fawkesrc;

/**
 * Created by hello_000 on 12/31/2015.
 */
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
        if(get('r')>200 && get('g')>130 && get('g')<200 && get('b')<95) return 'y';
        if(get('r')>200 && get('b')<100) return 'r';
        if(get('r')<100 && get('b')>200) return 'b';
        else return 'o';
    }
}
