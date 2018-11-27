package fall2018.csc207_project.MineSweeper;

import fall2018.csc207_project.R;

public class Tile {
    private int num = 0;
    private boolean fliped = false;
    private boolean labeled = false;

    Tile(){}

    Tile(int n){this.num = n;}

    public void setNum(int num) {
        this.num = num;
    }

    public void setLabeled(boolean labeled) {
        this.labeled = labeled;
    }

    public void setFliped(boolean fliped) {
        this.fliped = fliped;
    }

    public int getNum() {
        return num;
    }

    public boolean isLabeled() {
        return labeled;
    }

    public boolean isFliped() {
        return fliped;
    }

    public int getTileImage(){
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
            else {ret = R.drawable.bomb_exploded;} // num = 10


        }
        return ret;
    }
}

