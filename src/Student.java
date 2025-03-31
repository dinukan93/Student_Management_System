public class Student {
    private String studentId;
    private String studentName;
    private Module module;

    public Student(String studentId, String studentName,Module module) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.module = module;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public Module getModule() {
        return module;}
    public void setModule(Module module) {
        this.module = module;
    }
    public String toString() {
        return studentId+","+studentName+","+module;
    }
}
