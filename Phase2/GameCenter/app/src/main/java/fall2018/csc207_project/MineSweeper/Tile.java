package fall2018.csc207_project.MineSweeper;

import java.io.Serializable;

import fall2018.csc207_project.R;


class Tile implements Serializable {

    private static final long serialVersionUID = 24575L;

    private int num;
    private boolean fliped = false;
    private boolean labeled = false;
    private boolean boom = false;

    Tile(int num){this.num = num;}

    Tile(Tile tile) {
        this.num = tile.getNum();
        this.fliped = tile.getFliped();
        this.labeled = tile.getLabeled();
        this.boom = tile.getBoom();
    }

    public boolean getFliped() {
        return fliped;
    }

    public boolean getLabeled() {
        return labeled;
    }

    public boolean getBoom() {
        return boom;
    }

    void setNum(int num) {
        this.num = num;
    }

    void setLabeled(boolean labeled) {
        this.labeled = labeled;
    }

    void setFliped() {
        this.fliped = true;
    }

    void setBoom() {this.boom = true;}

    int getNum() {
        return num;
    }

    boolean isLabeled() {
        return labeled;
    }

    boolean isFliped() {
        return fliped;
    }

    int getTileImage(){
        int ret;
        if(!isFliped()){
            if(isLabeled()){
                ret = R.drawable.flag;}
            else{
                ret = R.drawable.button;}}
        else{
            if(num == 0){ret = R.drawable.number_0;}
            else if(num == 1){ret = R.drawable.number_1;}
            else if(num == 2){ret = R.drawable.number_2;}
            else if(num == 3){ret = R.drawable.number_3;}
            else if(num ==4){ret = R.drawable.number_4;}
            else if(num == 5){ret = R.drawable.number_5;}
            else if(num == 6){ret = R.drawable.number_6;}
            else if(num == 7){ret = R.drawable.number_7;}
            else if(num == 8){ret = R.drawable.number_8;}
            else {
                // num >= 10
                if (boom) {
                    ret = R.drawable.bomb_exploded;
                } else {
                    ret = R.drawable.bomb_normal;
                }
            }
        }
        return ret;
    }
}
