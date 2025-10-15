package com.plgdhd;

enum Parts {
    HEAD(0),
    TORSO(1),
    HAND(2),
    FEET(3);

    private int code;

    Parts(int code){
        this.code=  code;
    }

    public int getCode(){
        return this.code;
    }

    public static Parts getNameByCode(int code){
        for(Parts part : Parts.values()){
            if(part.getCode() == code){
                return part;
            }
        }
        return FEET;
    }
}
