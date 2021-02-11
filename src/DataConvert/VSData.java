package DataConvert;

public class VSData {
    private String sourse;
    private String target;
    private int weight ;
    private static String original_therm;

    VSData(String s, String t, int w){
        this.sourse = s;
        this.target = t;
        this.weight = w;
    }

    public String getSourse() {
        return this.sourse;
    }
    public String getTarget() {
        return this.target;
    }
    public int  getWeight() {
        return this.weight;
    }

    public static void setOriginal_therm(String original_therm) {
        VSData.original_therm = original_therm;
    }

    public static String getOriginal_therm() {
        return original_therm;
    }

    @Override
    public String toString(){
        return this.getSourse() + "|" + this.getTarget() + "|" + this.getWeight()+ "|" + VSData.original_therm;
    }


}

