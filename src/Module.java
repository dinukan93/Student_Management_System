public class Module {
    private double moduleMark_1;
    private double moduleMark_2;
    private double moduleMark_3;


    public Module(double moduleMark_1, double moduleMark_2, double moduleMark_3) {
        this.moduleMark_1 = moduleMark_1;
        this.moduleMark_2 = moduleMark_2;
        this.moduleMark_3 = moduleMark_3;
    }


    public double getModuleMark_1() {
        return moduleMark_1;
    }

    public void setModuleMark_1(double moduleMark_1) {
        this.moduleMark_1 = moduleMark_1;
    }

    public double getModuleMark_2() {
        return moduleMark_2;
    }

    public void setModuleMark_2(double moduleMark_2) {
        this.moduleMark_2 = moduleMark_2;
    }

    public double getModuleMark_3() {
        return moduleMark_3;
    }

    public void setModuleMark_3(double moduleMark_3) {
        this.moduleMark_3 = moduleMark_3;
    }

    public double getTotal(){
        return moduleMark_1 + moduleMark_2 + moduleMark_3;
    }

    public double getAverage(){
        return (moduleMark_1+moduleMark_2+moduleMark_3)/3;
    }

    public String calculateGrade() {
        double avg=getAverage();
        if (avg >= 80) {
            return "Distinction";
        } else if (avg >= 70) {
            return "Merit";
        } else if (avg >= 40) {
            return "Pass";
        }
        return "Fail";
    }

    public String toString() {
        return moduleMark_1+","+moduleMark_2+","+moduleMark_3+"\n";//this line display in .getModule()
    }
}

