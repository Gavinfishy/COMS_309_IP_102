package coms309;


public class JSONExample {
    private boolean bool;
    private int[] array;
    private String string;
    private double doubleNum;
    private int number;

    public JSONExample(boolean bool, int[] array, String string, double doubleNum, int number) {
        this.bool = bool;
        this.array = array;
        this.string = string;
        this.doubleNum = doubleNum;
        this.number = number;
    }

    public double getDoubleNum() {
        return doubleNum;
    }

    public int getNumber() {
        return number;
    }

    public int[] getArray() {
        return array;
    }

    public String getString() {
        return string;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public void setDoubleNum(double doubleNum) {
        this.doubleNum = doubleNum;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setString(String string) {
        this.string = string;
    }
}
