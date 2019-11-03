import java.util.ArrayList;
import java.util.Arrays;

public class ClassSchedule {

    private String day;
    private String hour;
    private String duration;
    private String classroom_id;
    private String subject_id;
    private ArrayList<String> valid_hour = new ArrayList<String>();
    private ArrayList<String> valid_day = new ArrayList<String>();

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    private String professor_id;
    private boolean error;

    public String getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(String professor_id) {
        this.professor_id = professor_id;
    }

    private int invalid_intervals[][] = {{915, 925}, {1225, 1315}, {1515, 1525}, {1825, 1830}, {2030, 2040}};

    public ClassSchedule() {
        String valid_hour[] = {"7h15", "8h15", "9h25", "10h25", "11h25", "13h15", "14h15", "15h25", "16h25",
                "17h25", "18h30", "19h30", "20h40", "21h40"};
        String valid_day[] =  {"2","3","4","5","s","d"};
        this.valid_day.addAll(Arrays.asList(valid_day));
        this.valid_hour.addAll(Arrays.asList(valid_hour));
    }

    public ClassSchedule(String professor_id) {
        this.professor_id = professor_id;
        this.error = false;
    }

    public ClassSchedule(String subject, String day, String hour, String duration, String classroom) {
        this.subject_id = subject;
        this.day = day;
        this.hour = hour;
        this.duration = duration;
        this.classroom_id = classroom;
        String valid_hour[] = {"7h15", "8h15", "9h25", "10h25", "11h25", "13h15", "14h15", "15h25", "16h25",
                "17h25", "18h30", "19h30", "20h40", "21h40"};
        this.valid_hour.addAll(Arrays.asList(valid_hour));
        this.error = false;
    }

    public String getHour() {
        return hour;
    }

    public String getClassroom_id() {
        return classroom_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return hour;
    }

    public String getDuration() {
        return duration;
    }

    public boolean verifyHour(String hour) {
        return this.valid_hour.contains(hour);
    }

    public boolean verifyDay(String day) {
        return this.valid_day.contains(day);
    }


    public boolean verifyDuration(String hour, String duration) {
        String[] array = hour.split("h");
        int beginning = Integer.parseInt(array[0]) * 100 + Integer.parseInt(array[1]);
        int ending = beginning + Integer.parseInt(duration) * 100;

        for (int[] i : this.invalid_intervals) {
            if (beginning <= i[0] && ending >= i[1]) return false;
        }

        return true;
    }

    public boolean verifyInteception(ArrayList<ClassSchedule> cs, String hour, String duration, String day, String id, String classroom_id, ArrayList<String> es) {
        String[] array = hour.split("h");
        int beginning = Integer.parseInt(array[0]) * 100 + Integer.parseInt(array[1]);
        int ending = beginning + Integer.parseInt(duration) * 100;

        for (ClassSchedule p : cs) {
            String a1 = p.getSubject_id();
            String a2 = p.getDay();
            String a3 = p.getTime();
            String a4 = p.getDuration();
            String a5 = p.getClassroom_id();

            String[] arrayP = a3.split("h");
            int beginningP = Integer.parseInt(arrayP[0]) * 100 + Integer.parseInt(arrayP[1]);
            int endingP = beginningP + Integer.parseInt(a4) * 100;

            if (day.equals(a2) && a5.equals(classroom_id)) {
                if (verifyHourInterception(beginning, ending, beginningP, endingP)) {
                    String exception = classroom_id + " está com mais de uma turma alocada no mesmo horário: " + a1 + "," + id;
                    es.add(exception);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyHourInterception(int b, int e, int bP, int eP) {
        if (bP < e && eP > b) {
            return true;
        }
        return false;
    }


    public boolean verifyProfessor(String professor_id, String subject_id, ArrayList<ClassSchedule> cs, ArrayList<String> es) {
        //Verificar se a disciplina possuí professor
        ClassSchedule pi = new ClassSchedule(professor_id);
        for (ClassSchedule s : cs) {
            if (s.getSubject_id().equals(subject_id) && s.isError()) {
                s.setError(false);
            }
        }

            for (ClassSchedule c:cs) {
                if(c.getSubject_id().equals(subject_id)){
                    //Disicplina escolhida já possui um professor
                    if(c.getProfessor_id()!=null){
                        if(!professor_id.equals(c.getProfessor_id())){
                            String exception = subject_id + " está sendo ministrada por mais de um professor: " + c.getProfessor_id()+ " " +professor_id ;
                            if (!es.contains(exception)) es.add(exception);
                        }
                    }
                    //Disciplina escolhida nao possui professor
                    else{
                        for (ClassSchedule c1 : cs) {
                                if (c1.getProfessor_id()!=null && c1.getProfessor_id().equals(professor_id) && !c.getSubject_id().equals(c1.getSubject_id())) {

                                    String[] array = c.getHour().split("h");
                                    int beginning = Integer.parseInt(array[0]) * 100 + Integer.parseInt(array[1]);
                                    int ending = beginning + Integer.parseInt(c.getDuration()) * 100;
                                    String[] arrayP = c1.getHour().split("h");
                                    int beginningP = Integer.parseInt(arrayP[0]) * 100 + Integer.parseInt(arrayP[1]);
                                    int endingP = beginningP + Integer.parseInt(c1.getDuration()) * 100;
                                    if (verifyHourInterception(beginning, ending, beginningP, endingP)) {
                                        String la = professor_id + " está ministrando duas turmas no mesmo horário: " + c1.getSubject_id() + "," + c.getSubject_id();

                                            if(!es.contains(la))es.add(la);

                                        for (ClassSchedule s : cs) {
                                            if (s.getSubject_id().equals(subject_id)) {
                                                s.setError(true);
                                            }
                                        }
                                    }
                                } else {
                                    for (ClassSchedule s : cs) {
                                        if (s.getSubject_id().equals(subject_id) && !s.isError()) {
                                            s.setProfessor_id(professor_id);
                                        }
                                    }
                                }
                        }
                    }
                }
            }


        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return this.professor_id.equals(((ClassSchedule) obj).professor_id);
    }

}


    
    



